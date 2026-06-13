package com.nhs.automation.framework.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

public class BasePage {

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void click(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
        } catch (StaleElementReferenceException e) {
            wait.until(ExpectedConditions.refreshed(
                    ExpectedConditions.elementToBeClickable(locator)
            )).click();
        }
    }

    public void type(By locator, String value) {
        WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        el.clear();
        el.sendKeys(value);
    }

    public String getText(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getText();
    }

    public WebElement find(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public boolean isDisplayed(By locator) {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(locator)
            ).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void waitForPageLoad() {
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    public void scrollToElement(By locator) {
        WebElement el = find(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", el);
    }

    public List<WebElement> findAll(By locator){
        return driver.findElements(locator);
    }
}