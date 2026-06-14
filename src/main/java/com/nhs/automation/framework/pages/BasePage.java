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

    public WebElement find(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
    }

    public List<WebElement> findAll(By locator){
        return driver.findElements(locator);
    }

    public void click(By locator) {

            wait.until(ExpectedConditions.elementToBeClickable(locator)).click();

    }

    public void type(By locator, String value) {
        WebElement el = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        );
        el.clear();
        el.sendKeys(value);
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
    public boolean isElementVisible(By locator) {
        try {
            return driver.findElement(locator).isDisplayed();
        } catch (Exception e) {
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
    protected void waitForUrlContains(String value) {

        wait.until(
                ExpectedConditions.urlContains(value));
    }
    public void waitForResultsToLoad(By locator) {

        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));

        wait.until(driver -> {

            List<WebElement> elements =
                    driver.findElements(locator);

            return !elements.isEmpty();
        });
    }

    public String getText(By locator) {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator)
        ).getText();
    }

    public void scrollToElement(By locator) {
        WebElement el = find(locator);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", el);
    }

}