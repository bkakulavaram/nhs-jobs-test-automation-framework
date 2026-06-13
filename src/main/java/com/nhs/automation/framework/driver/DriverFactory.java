package com.nhs.automation.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void initDriver(String browser) {

        WebDriver webDriver;

        switch(browser.trim().toLowerCase()) {

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = new FirefoxDriver();
                break;

            case "chrome":
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unsupported browser: " + browser
                );
        }

        driver.set(webDriver);
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}