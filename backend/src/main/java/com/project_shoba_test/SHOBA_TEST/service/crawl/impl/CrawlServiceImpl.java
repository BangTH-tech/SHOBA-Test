package com.project_shoba_test.SHOBA_TEST.service.crawl.impl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;
import com.project_shoba_test.SHOBA_TEST.model.entity.Product;
import com.project_shoba_test.SHOBA_TEST.service.crawl.CrawlService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CrawlServiceImpl implements CrawlService {

    @Override
    public ProductDetailResponse crawlProduct(String link) {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\acer\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0); // 0 = chặn popup
        prefs.put("profile.default_content_setting_values.popups", 0);
        prefs.put("profile.default_content_setting_values.notifications", 2); // chặn thông báo

        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--start-maximized");

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
            log.info(pageSource);
            Product product = new Product();
            log.info(extractJsonByKey("offerId", pageSource));
            log.info(extractJsonByKey("offerTitle", pageSource));
            log.info(extractAllFullPathImageUris(pageSource));
            log.info(extractJsonByKey("featureAttributes", pageSource));
            log.info(extractJsonByKey("skuProps", pageSource));
            log.info(extractJsonByKey("skuMap", pageSource));
            log.info(extractJsonByKey("imageList", pageSource));
            // product.setId(Long.parseLong(extractJsonByKey("offerId", pageSource)));
            // product.setTitle(extractJsonByKey("offerTitle", pageSource));
            // product.setImageUrls(extractAllFullPathImageUris(pageSource).toArray(new
            // String[0]));
            // product.setFeatureInfos(
            // objectMapper.readValue(extractJsonByKey("featureAttributes", pageSource), new
            // TypeReference<>() {
            // }));
            // product.setColorInfos(
            // objectMapper.readValue(extractJsonByKey("skuProps", pageSource), new
            // TypeReference<>() {
            // }));
            // product.setProductPriceInfos(
            // objectMapper.readValue(extractJsonByKey("skuMap", pageSource), new
            // TypeReference<>() {
            // }));

            log.info(product);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
        }

        return null;
    }

    public String extractSaledCount(String text) {
        Pattern pattern = Pattern.compile("\"dataJson\"\\s*:\\s*\\{[^}]*?\"saledCount\"\\s*:\\s*(\\d+)");
        Matcher matcher = pattern.matcher(text);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    public List<String> extractAllFullPathImageUris(String text) {
        List<String> uris = new ArrayList<>();

        // Regex tìm giá trị fullPathImageURI: "some_url"
        Pattern pattern = Pattern.compile("\"fullPathImageURI\"\\s*:\\s*\"(.*?)\"");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            uris.add(matcher.group(1)); // Lấy phần URL
        }

        return uris;
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

    private static JsonNode findKeyRecursively(JsonNode node, String key) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                } else {
                    JsonNode found = findKeyRecursively(entry.getValue(), key);
                    if (found != null)
                        return found;
                }
            }
        } else if (node.isArray()) {
            for (JsonNode item : node) {
                JsonNode found = findKeyRecursively(item, key);
                if (found != null)
                    return found;
            }
        }
        return null;
    }

}
