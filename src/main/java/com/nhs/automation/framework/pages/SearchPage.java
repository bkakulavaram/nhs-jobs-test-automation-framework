package com.nhs.automation.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;


public class SearchPage extends BasePage {


    public SearchPage(WebDriver driver) {
        super(driver);

    }

    private By whatField = By.id("keyword");
    private By whereField = By.id("location");
    private By distanceDropdown = By.id("distance");
    private By searchButton = By.id("search");
    private By clearButton = By.id("clearFilters");
    private By searchOptionsLink=By.id("searchOptionsBtn");
    private By jobReference=By.id("jobReference");
    private By employer=By.id("employer");
    private By payRangeDropdown=By.id("payRange");

    public void enterWhat(String value) {
        type(whatField, value);
    }

    public void enterWhere(String value) {
        type(whereField,value);
    }


    public void enterJobReference(String value){
        type(jobReference,value);
    }

    public void enterEmployer(String value){
        type(employer,value);
    }

    public void selectDistance(String value) {

        new Select(find(distanceDropdown))
                .selectByVisibleText(value);
    }
    public void selectPayRange(String value) {

        new Select(find(payRangeDropdown))
                .selectByVisibleText(value);
    }

    public void clickSearch() {

        click(searchButton);

        waitForPageLoad();

        wait.until(driver -> {

            String url = driver.getCurrentUrl();

            return url.contains("/results")
                    || url.contains("location-not-found")
                    || url.contains("too-many-locations");
        });
    }
    public void clickSearchOptionsLink(){

        click(searchOptionsLink);


    }

    public void clearFilters() {

        click(clearButton);

        waitForPageLoad();
    }

    public void waitForAdvancedFilters() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(By.id("jobReference")),
                ExpectedConditions.visibilityOfElementLocated(By.id("employer")),
                ExpectedConditions.visibilityOfElementLocated(By.id("payRange"))
        ));
    }

    public boolean advancedFiltersDisplayed() {

        waitForAdvancedFilters();
        return (isDisplayed(jobReference)
                && isDisplayed(employer)
                && isDisplayed(payRangeDropdown));
    }

    public boolean advancedFiltersHidden() {
        return !isElementVisible(jobReference)
                || !isElementVisible(employer)
                || !isElementVisible(payRangeDropdown);
    }

    public ResultsPage searchWithBasicCriteria(String what, String where, String distance) {
        enterWhat(what);
        enterWhere(where);
        if (distance != null) {
            selectDistance(distance);

        }
        clickSearch();

        return new ResultsPage(driver);
    }


    public ResultsPage searchWithAdvancedCriteria(String what, String where, String distance, String jobReference, String employer, String payRange) {

        clickSearchOptionsLink();
        waitForAdvancedFilters();
            enterWhat(what);
            enterWhere(where);

            if (distance != null) {
                selectDistance(distance);
            }

        if (jobReference != null) {

            enterJobReference(jobReference);

        }

        if (employer!=null){

            enterEmployer(employer);
        }


            if (payRange != null) {
                selectPayRange(payRange);
            }

            clickSearch();

            return new ResultsPage(driver);
        }


        public void clickSearchWithoutEnteringAnyData() {

        click(searchButton);

    }

    public void searchForAJobUsingInvalidLocation(String location) {

        type(whereField,location);

        click(searchButton);

    }

    public boolean isSearchPageReset() {

        boolean keywordEmpty =
                find(whatField)
                        .getAttribute("value")
                        .isEmpty();

        boolean locationEmpty =
                find(whereField)
                        .getAttribute("value")
                        .isEmpty();

        String distance =
                new Select(find(distanceDropdown))
                        .getFirstSelectedOption()
                        .getText();


        boolean distanceReset =
                distance.equalsIgnoreCase("All locations")
                        || distance.equalsIgnoreCase("+5 Miles");

        return keywordEmpty && locationEmpty && distanceReset;
    }
}