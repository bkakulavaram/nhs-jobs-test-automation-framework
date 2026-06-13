package com.nhs.automation.test.stepdefinitions;

import com.nhs.automation.framework.config.ConfigReader;
import com.nhs.automation.framework.driver.DriverFactory;
import com.nhs.automation.framework.models.ResultRule;
import com.nhs.automation.framework.pages.ResultsPage;
import com.nhs.automation.framework.pages.SearchPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import java.util.Map;

public class SearchSteps {

    private SearchPage searchPage;
    private ResultsPage resultsPage;
    private WebDriver driver;

    private String lastKeyword;
    private String lastLocation;
    private String lastDistance;
    private String lastEmployer;
    private String lastJobReference;
    private String lastPayRange;



    @Given("I am on the NHS Jobs search page")
    public void iAmOnTheNHSJobsSearchPage() {

        driver = DriverFactory.getDriver();

        searchPage = new SearchPage(driver);
        resultsPage = new ResultsPage(driver);

        driver.get(ConfigReader.getBaseUrl());
    }



    @When("I search using {string}, {string} and {string}")
    public void iSearchUsingAnd(String what, String where, String distance) {

        this.lastKeyword = what;
        this.lastLocation = where;
        this.lastDistance = distance;

        resultsPage = searchPage.searchWithBasicCriteria(what, where, distance);
    }

    @Then("relevant job results should be displayed")
    public void relevantJobResultsShouldBeDisplayed() {

        ResultRule rule = new ResultRule()
                .setKeyword(lastKeyword)
                .setMaxDistance(parseDistance(lastDistance));

        Assert.assertTrue(
                resultsPage.validateResults(rule),
                "No search results matched the criteria"
        );
    }



    @Then("results should be sorted by newest date posted first")
    public void resultsShouldBeSortedByNewestDatePostedFirst() {

        Assert.assertTrue(
                resultsPage.areResultsSortedByNewestDate(),
                "Results are not sorted by newest date"
        );
    }

    @When("I search using advanced criteria")
    public void iSearchUsingAdvancedCriteria(DataTable table) throws InterruptedException {


        Map<String, String> data = table.asMap(String.class, String.class);

        this.lastKeyword = data.get("job");
        this.lastLocation = data.get("location");
        this.lastDistance = data.get("distance");
        this.lastEmployer = data.get("employer");
        this.lastPayRange = data.get("payRange");
        //lastJobReference = data.get("jobReference");

        resultsPage = searchPage.searchWithAdvancedCriteria(
                lastKeyword,
                lastLocation,
                lastDistance,
                null,
                lastEmployer,
                lastPayRange
        );
    }

    @Then("advanced search results should match my criteria")
    public void advancedSearchResultsShouldMatchMyCriteria() {

        ResultRule rule = new ResultRule()
                .setKeyword(lastKeyword)
                .setMaxDistance(parseDistance(lastDistance))
                .setEmployer(lastEmployer)
                .setPayRange(lastPayRange)
                .setJobReference(null);
        System.out.println(rule);
        Assert.assertTrue(
                resultsPage.validateResults(rule),
                "Advanced search results do not match search criteria"
        );
    }



    @When("I have opened more search options")
    public void iOpenMoreSearchOptions() {
        searchPage.clickSearchOptionsLink();
    }

    @Then("I should see more search filters")
    public void iShouldSeeJobReferenceEmployerAndPayRangeFields() {
        Assert.assertTrue(searchPage.advancedFiltersDisplayed());
    }

    @When("I click on fewer search options")
    public void iClickOnFewerSearchOptions() {
        searchPage.clickSearchOptionsLink();
    }

    @Then("advanced search fields should be hidden")
    public void advancedSearchFieldsShouldBeHidden() {
        Assert.assertTrue(searchPage.advancedFiltersHidden());
    }



    @When("I click on clear filters")
    public void iClickOnClearFilters() {
        searchPage.clearFilters();
    }

    @Then("all fields should be reset to default state")
    public void allFieldsShouldBeResetToDefaultState() {
Assert.assertTrue(searchPage.isSearchPageReset());
    }



    @When("I search for a job using invalid location {string}")
    public void iSearchForAJobUsingInvalidLocation(String location) throws InterruptedException {
        searchPage.searchForAJobUsingInvalidLocation(location);
    }

    @Then("I should be redirected to location_not_found or too_many_locations page")
    public void iShouldBeRedirectedToPage() {

        String url = driver.getCurrentUrl();

        Assert.assertTrue(
                url.equals(ConfigReader.getLocationNotFoundUrl())
                        || url.equals(ConfigReader.getTooManyLocationsUrl()),
                "Unexpected redirect URL: " + url
        );
    }



    @When("I click search without entering any data")
    public void iClickSearchWithoutEnteringAnyData() {
        searchPage.clickSearchWithoutEnteringAnyData();
    }

    @Then("I should see all the jobs on NHS")
    public void iShouldSeeAllTheJobsOnNHS() {
        Assert.assertTrue(resultsPage.areResultsDisplayed());
    }


    private Double parseDistance(String distance) {
        if (distance == null) return null;

        try {
            return Double.parseDouble(distance.replace("+", "").replace("Miles", "").trim());
        } catch (Exception e) {
            return null;
        }
    }

    @Given("I enter search criteria {string}, {string} and {string}")
    public void iEnterSearchCriteriaAnd(String what, String where, String distance) {
        searchPage.enterWhat(what);
        searchPage.enterWhere(where);
        searchPage.selectDistance(distance);
    }


}





