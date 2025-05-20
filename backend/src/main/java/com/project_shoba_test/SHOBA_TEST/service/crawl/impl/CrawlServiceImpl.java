package com.project_shoba_test.SHOBA_TEST.service.crawl.impl;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

import com.project_shoba_test.SHOBA_TEST.model.dto.response.product.ProductDetailResponse;
import com.project_shoba_test.SHOBA_TEST.service.crawl.CrawlService;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CrawlServiceImpl implements CrawlService {

    @Override
    public ProductDetailResponse crawlProduct(String link) {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\acer\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless=new");
        // options.addArguments("--disable-gpu");
        options.addArguments("--start-maximized");
        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get(link);
            // (tùy chỉnh)

            log.info(driver.getPageSource());

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            driver.quit();
        }
        return null;
    }

}
