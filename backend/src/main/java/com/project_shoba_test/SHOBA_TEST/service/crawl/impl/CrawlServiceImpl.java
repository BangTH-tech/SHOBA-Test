package com.project_shoba_test.SHOBA_TEST.service.crawl.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_shoba_test.SHOBA_TEST.model.dto.request.product.ColorCrawl;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.ColorInfo;
import com.project_shoba_test.SHOBA_TEST.model.entity.ModelInfo;
import com.project_shoba_test.SHOBA_TEST.model.entity.Product;
import com.project_shoba_test.SHOBA_TEST.model.entity.SizeInfo;
import com.project_shoba_test.SHOBA_TEST.service.crawl.CrawlService;
import com.project_shoba_test.SHOBA_TEST.service.redis.RedisService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CrawlServiceImpl implements CrawlService {

    private final RedisService redisService;

    @Override
    public Product crawlProduct(String link) {
        Product product = new Product();
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\acer\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        // Giả lập thiết bị di động, ví dụ iPhone X
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 812);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_6 like Mac OS X) " +
                "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.2 Mobile/15E148 Safari/604.1");

        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        WebDriver driver = new ChromeDriver(options);

        try {
            // Bước 1: Vào trang gốc (để thiết lập domain)
            driver.get(link);

            // Bước 2: Đọc cookie từ file JSON (hoặc hardcode)
            String json = Files.readString(Paths.get("D:\\SHOBA\\SHOBAGit\\SHOBA-Test\\backend\\src\\cookie.txt"));
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<Map<String, Object>> cookies = objectMapper.readValue(json, new TypeReference<>() {
            });

            // Bước 3: Thêm từng cookie
            for (Map<String, Object> c : cookies) {
                Cookie seleniumCookie = new Cookie.Builder((String) c.get("name"), (String) c.get("value"))
                        .domain((String) c.get("domain"))
                        .path((String) c.getOrDefault("path", "/"))
                        .isSecure((Boolean) c.getOrDefault("secure", false))
                        .isHttpOnly((Boolean) c.getOrDefault("httpOnly", false))
                        .build();
                driver.manage().addCookie(seleniumCookie);
            }

            // Bước 4: Truy cập trang cần crawl sau khi đã có cookie
            driver.get(link);

            String pageSource = driver.getPageSource();
            // log.info(extractJsonByKey("offerId", pageSource));
            // log.info(extractSaledCount(pageSource));
            // log.info(extractUnitWeight(pageSource));
            // log.info(extractJsonBetweenKeys(pageSource, "skuWeight", "newOdFlag"));
            // log.info(extractJsonByKey("offerTitle", pageSource));
            // log.info(extractAllFullPathImageUris(pageSource));
            // log.info(extractJsonArrayByKey(pageSource, "skuRangePrices"));
            // log.info(extractJsonArrayByKey(pageSource, "propsList"));
            // log.info(extractJsonArrayByKey(pageSource, "skuProps"));
            // log.info(extractJsonBetweenKeys(pageSource, "skuInfoMap", "skuPriceScale"));

            String id = extractJsonByKey("offerId", pageSource);
            product.setId(Long.parseLong(id.substring(1, id.length() - 1)));

            String canBookedAmount = extractCanBookedAmount(pageSource);
            product.setCanBookedAmount(
                    canBookedAmount != null ? Long.parseLong(canBookedAmount) : 0L);

            String title = extractJsonByKey("offerTitle", pageSource);
            product.setTitle(title.substring(1,
                    title.length() - 1));

            product.setSaledCount(Long.parseLong(extractSaledCount(pageSource)));
            product.setUnitWeight(Double.parseDouble(extractUnitWeight(pageSource)));

            String jsonPrice = extractJsonArrayByKey(pageSource, "skuRangePrices");
            product.setPriceInfos(
                    jsonPrice != null ? objectMapper.readValue(jsonPrice,
                            new TypeReference<>() {
                            }) : null);

            product.setImageUrls(extractAllFullPathImageUris(pageSource));

            String jsonFeature = extractJsonArrayByKey(pageSource, "propsList");
            product.setFeatureInfos(
                    jsonFeature != null ? objectMapper.readValue(jsonFeature,
                            new TypeReference<>() {
                            }) : null);

            String jsonColor = extractJsonArrayByKey(pageSource, "skuProps");
            if (jsonColor != null) {
                ColorCrawl[] colorCrawls = objectMapper.readValue(
                        jsonColor,
                        new TypeReference<>() {
                        });

                for (int i = 0; i < colorCrawls.length; i++) {
                    if (colorCrawls[i].getProp().equals("颜色")) {
                        product.setColorInfos(
                                objectMapper.convertValue(
                                        colorCrawls[i].getValue(),
                                        new TypeReference<List<ColorInfo>>() {
                                        }));
                    } else {
                        product.setSizeInfos(
                                objectMapper.convertValue(
                                        colorCrawls[i].getValue(),
                                        new TypeReference<List<SizeInfo>>() {
                                        }));
                    }
                }
            }
            product.setModelInfos(parseModelInfo(extractJsonBetweenKeys(pageSource, "skuInfoMap", "skuPriceScale"),
                    extractJsonBetweenKeys(pageSource, "skuWeight", "newOdFlag")));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
        }

        return product;
    }

    public String extractCanBookedAmount(String text) {
        Pattern pattern = Pattern.compile("\"carOrderParam\"\\s*:\\s*\\{[^}]*?\"canBookedAmount\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public String extractSaledCount(String text) {
        Pattern pattern = Pattern.compile("\"globalData\"\\s*:\\s*\\{[^}]*?\"saledCount\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public String extractUnitWeight(String text) {
        Pattern pattern = Pattern.compile("\"data\"\\s*:\\s*\\{[^}]*?\"unitWeight\"\\s*:\\s*(-?\\d*\\.?\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public Set<String> extractAllFullPathImageUris(String text) {
        Set<String> uris = new HashSet();

        // Regex tìm giá trị fullPathImageURI: "some_url"
        Pattern pattern = Pattern.compile("\"fullPathImageURI\"\\s*:\\s*\"(.*?)\"");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            uris.add(matcher.group(1)); // Lấy phần URL
        }

        return uris;
    }

    private String extractResult(String json) {
        Pattern pattern = Pattern.compile("\\(result,\\s*(\\{.*?\\})\\)", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(json);

        if (matcher.find()) {

            return matcher.group(1);
        } else {
            return null;
        }
    }

    public String extractJsonByKey(String key, String text) {
        String regex = "\"" + Pattern.quote(key)
                + "\"\\s*:\\s*(\".*?\"|\\{.*?\\}|\\[.*?\\]|\\d+(\\.\\d+)?|true|false|null)";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1).trim(); // Trả về giá trị dạng chuỗi, object, array, số, boolean
        }
        return null;
    }

    public String extractJsonBetweenKeys(String jsonText, String startKey, String endKey) {
        // Tìm vị trí start của startKey:
        String startPattern = "\"" + startKey + "\"";
        int startIndex = jsonText.indexOf(startPattern);
        if (startIndex == -1)
            return null;

        // Tìm vị trí start của endKey, nhưng chỉ tìm sau startKey
        String endPattern = "\"" + endKey + "\"";
        int endIndex = jsonText.indexOf(endPattern, startIndex);
        if (endIndex == -1) {
            // Nếu không tìm thấy endKey thì lấy tới hết jsonText
            endIndex = jsonText.length();
        }

        // Từ startIndex, tìm dấu ':' kế tiếp
        int colonIndex = jsonText.indexOf(":", startIndex);
        if (colonIndex == -1 || colonIndex > endIndex)
            return null;

        // Bỏ qua dấu ':' và khoảng trắng
        int valueStartIndex = colonIndex + 1;
        while (valueStartIndex < jsonText.length() &&
                Character.isWhitespace(jsonText.charAt(valueStartIndex))) {
            valueStartIndex++;
        }

        // Bây giờ ta cần tìm vị trí kết thúc hợp lệ cho giá trị của startKey trước
        // endIndex
        // Giá trị có thể là object {...} hoặc array [...] hoặc giá trị đơn giản
        // Mình xử lý cơ bản với object hoặc array

        char firstChar = jsonText.charAt(valueStartIndex);
        int valueEndIndex = -1;

        if (firstChar == '{' || firstChar == '[') {
            // Tìm dấu đóng tương ứng, cân bằng ngoặc
            char openChar = firstChar;
            char closeChar = (openChar == '{') ? '}' : ']';

            int balance = 1;
            int i = valueStartIndex + 1;
            while (i < endIndex && balance > 0) {
                char c = jsonText.charAt(i);
                if (c == openChar)
                    balance++;
                else if (c == closeChar)
                    balance--;
                i++;
            }
            if (balance == 0) {
                valueEndIndex = i; // vị trí sau dấu đóng ngoặc cuối cùng
            } else {
                // không đóng ngoặc đủ -> lỗi
                return null;
            }
        } else {
            // Giá trị đơn giản (string, number, boolean, null)
            // Lấy đến dấu phẩy hoặc } hoặc ] trước endIndex
            int commaIndex = jsonText.indexOf(',', valueStartIndex);
            int braceIndex = jsonText.indexOf('}', valueStartIndex);
            int bracketIndex = jsonText.indexOf(']', valueStartIndex);

            // Tìm min trong các vị trí tìm được nhưng không vượt quá endIndex
            int minIndex = jsonText.length();
            if (commaIndex != -1 && commaIndex < minIndex && commaIndex < endIndex)
                minIndex = commaIndex;
            if (braceIndex != -1 && braceIndex < minIndex && braceIndex < endIndex)
                minIndex = braceIndex;
            if (bracketIndex != -1 && bracketIndex < minIndex && bracketIndex < endIndex)
                minIndex = bracketIndex;

            if (minIndex == jsonText.length()) {
                valueEndIndex = endIndex;
            } else {
                valueEndIndex = minIndex;
            }
        }

        // Trả về substring chứa giá trị key startKey
        String result = jsonText.substring(valueStartIndex, valueEndIndex).trim();

        // Có thể có dấu phẩy cuối cùng, ta cắt bỏ nếu có
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1).trim();
        }

        return result;
    }

    public String extractJsonArrayByKey(String text, String key) {
        try {
            // Find the index of the key
            ObjectMapper objectMapper = new ObjectMapper();
            int keyIndex = text.indexOf("\"" + key + "\"");
            if (keyIndex == -1) {
                log.info(key + " " + text.contains(key));
                return null;
            }
            // Find the start of the array (after key and colon)
            int arrayStart = text.indexOf('[', keyIndex);
            if (arrayStart == -1)
                return null;

            // Traverse to find the matching closing bracket
            int bracketCount = 1;
            int i = arrayStart + 1;
            while (i < text.length() && bracketCount > 0) {
                if (text.charAt(i) == '[')
                    bracketCount++;
                else if (text.charAt(i) == ']')
                    bracketCount--;
                i++;
            }

            if (bracketCount != 0)
                return null; // invalid JSON array

            String jsonArrayString = text.substring(arrayStart, i);
            // Optional: validate it's a proper array
            JsonNode node = objectMapper.readTree(jsonArrayString);
            if (node.isArray())
                return jsonArrayString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void testResponse(String link) {
        Product product = new Product();
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\acer\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();

        // Giả lập thiết bị di động, ví dụ iPhone X
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);
        deviceMetrics.put("height", 812);
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_6 like Mac OS X) " +
                "AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.1.2 Mobile/15E148 Safari/604.1");

        options.setExperimentalOption("mobileEmulation", mobileEmulation);

        WebDriver driver = new ChromeDriver(options);

        try {
            // Bước 1: Vào trang gốc (để thiết lập domain)
            driver.get(link);

            // Bước 2: Đọc cookie từ file JSON (hoặc hardcode)
            String json = Files.readString(Paths.get("D:\\SHOBA\\SHOBAGit\\SHOBA-Test\\backend\\src\\cookie.txt"));
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            List<Map<String, Object>> cookies = objectMapper.readValue(json, new TypeReference<>() {
            });

            // Bước 3: Thêm từng cookie
            for (Map<String, Object> c : cookies) {
                Cookie seleniumCookie = new Cookie.Builder((String) c.get("name"), (String) c.get("value"))
                        .domain((String) c.get("domain"))
                        .path((String) c.getOrDefault("path", "/"))
                        .isSecure((Boolean) c.getOrDefault("secure", false))
                        .isHttpOnly((Boolean) c.getOrDefault("httpOnly", false))
                        .build();
                driver.manage().addCookie(seleniumCookie);
            }

            // Bước 4: Truy cập trang cần crawl sau khi đã có cookie
            driver.get(link);

            String pageSource = driver.getPageSource();
            Files.writeString(Paths.get("D:\\SHOBA\\SHOBAGit\\SHOBA-Test\\backend\\src\\source.txt"), pageSource,
                    StandardCharsets.UTF_8);

            log.info(extractJsonByKey("offerId", pageSource));
            log.info(extractSaledCount(pageSource));
            log.info(extractUnitWeight(pageSource));
            log.info(extractJsonBetweenKeys(pageSource, "skuWeight", "newOdFlag"));
            log.info(extractJsonByKey("offerTitle", pageSource));
            log.info(extractAllFullPathImageUris(pageSource));
            log.info(extractJsonArrayByKey(pageSource, "skuRangePrices"));
            log.info(extractJsonArrayByKey(pageSource, "propsList"));
            log.info(extractJsonArrayByKey(pageSource, "skuProps"));
            log.info(extractJsonBetweenKeys(pageSource, "skuInfoMap", "skuPriceScale"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

    private List<ModelInfo> parseModelInfo(String jsonModel, String jsonWeight)
            throws JsonMappingException, JsonProcessingException {
        try {
            ObjectMapper mapper = new ObjectMapper();

            JsonNode rootNode = mapper.readTree(jsonModel);

            List<ModelInfo> result = new ArrayList<>();
            Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
            Iterator<Map.Entry<String, JsonNode>> weightFields = mapper.readTree(jsonWeight).fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String modelName = entry.getKey();
                JsonNode node = entry.getValue();

                ModelInfo model = new ModelInfo();
                model.setModelName(modelName);
                model.setCanBookCount(node.get("canBookCount").asLong());
                model.setPrice(node.get("price") != null ? node.get("price").asDouble() : 0);
                model.setDiscountPrice(node.get("discountPrice") != null ? node.get("discountPrice").asDouble() : 0);
                model.setSkuId(node.get("skuId").asLong());
                if (weightFields.hasNext()) {
                    Map.Entry<String, JsonNode> weightEntry = weightFields.next();
                    if (weightEntry.getKey().equals(String.valueOf(model.getSkuId()))) {
                        model.setWeight(weightEntry.getValue().asDouble());
                    }
                } else {
                    model.setWeight(0); // không có trong JSON, gán mặc định
                }

                result.add(model);
            }
            return result;
        } catch (JsonMappingException e) {
            log.error("Error parsing JSON: " + e.getMessage());
            throw e;
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: " + e.getMessage());
            throw e;
        }

    }

}
