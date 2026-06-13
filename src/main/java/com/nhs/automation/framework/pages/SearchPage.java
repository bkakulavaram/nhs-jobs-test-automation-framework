package com.nhs.automation.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
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

    public void selectDistance(String value) {
        new Select(driver.findElement(distanceDropdown)).selectByVisibleText(value);
    }

    public void clickSearch() {
        click(searchButton);
    }

    public void clearFilters() {
        click(clearButton);
    }

    public void clickSearchOptionsLink(){
        click(searchOptionsLink);
    }

    public void enterJobReference(String value){
        type(jobReference,value);
    }

    public void enterEmployer(String value){
        type(employer,value);
    }

    public void selectPayRange(String value) {
        new Select(driver.findElement(payRangeDropdown)).selectByVisibleText(value);
    }

    public ResultsPage searchWithBasicCriteria(String what, String where, String distance) {
        enterWhat(what);
        enterWhere(where);
        if (distance != null) {
            selectDistance(distance);

        }
        click(searchButton);
        return new ResultsPage(driver);
    }

    public ResultsPage searchWithAdvancedCriteria(String what, String where, String distance,String jobReference,String employer,String payRange) {

        clickSearchOptionsLink();
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


    public boolean advancedFiltersDisplayed() {


        return (isDisplayed(jobReference) && isDisplayed(employer) && isDisplayed(payRangeDropdown));
    }

    public boolean advancedFiltersHidden() {
        System.out.println(isDisplayed(jobReference) && isDisplayed(employer) && isDisplayed(payRangeDropdown));
        return (!(isDisplayed(jobReference) && isDisplayed(employer) && isDisplayed(payRangeDropdown)));
    }


    public void clickSearchWithoutEnteringAnyData() {
        System.out.println(driver.getCurrentUrl());
        click(searchButton);
        System.out.println(driver.getCurrentUrl());
    }

    public void searchForAJobUsingInvalidLocation(String location) {
        System.out.println(driver.getCurrentUrl());

        type(whereField,location);

        click(searchButton);

        System.out.println(driver.getCurrentUrl());
    }

    public String getSelectedJobReference() {
        return driver.findElement(jobReference)
                .getAttribute("value");
    }

    public String getSelectedEmployer() {
        return driver.findElement(employer)
                .getAttribute("value");
    }

    public String getSelectedPayRange() {
        return new Select(driver.findElement(payRangeDropdown))
                .getFirstSelectedOption()
                .getText();
    }
    public boolean isSearchPageReset() {

        boolean keywordEmpty = driver.findElement(whatField).getAttribute("value").isEmpty();
        boolean locationEmpty = driver.findElement(whereField).getAttribute("value").isEmpty();

        String distance = new Select(driver.findElement(distanceDropdown))
                .getFirstSelectedOption()
                .getText();


        boolean distanceReset =
                distance.equalsIgnoreCase("All locations")
                        || distance.equalsIgnoreCase("+5 Miles");

        return keywordEmpty && locationEmpty && distanceReset;
    }
}