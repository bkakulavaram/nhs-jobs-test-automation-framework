package com.nhs.automation.framework.pages;

import com.nhs.automation.framework.models.ResultRule;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultsPage extends BasePage
{

    private By resultsList = By.className("search-result");
    private By jobTitle = By.cssSelector("a");
    private By datePosted = By.cssSelector("li[data-test='search-result-publicationDate'] strong");
    private By distanceField = By.id("distance");
    private By whatField = By.id("keyword");
    private By sortDropdown = By.id("sort");
    private By noResultsMessage = By.id("no-result-title");

    public ResultsPage(WebDriver driver) {
        super(driver);
    }

    public boolean validateResults(ResultRule rule) {

        List<WebElement> results = findAll(resultsList);
        if (results.isEmpty())
        {
            return isNoResultsStateValid();
        }
        int limit = Math.min(5, results.size());
        int passedScore=0;
        int failedScore=0;
        for (int i = 0; i < limit; i++) {
            WebElement result = results.get(i);
            String title = result.findElement(jobTitle).getText().toLowerCase();
            boolean keywordMatch = rule.getKeyword() == null
                    || title.contains(rule.getKeyword().toLowerCase()) ||
                    result.getText().toLowerCase().contains(rule.getKeyword().toLowerCase());
            boolean distanceMatch =true;

            if (driver.findElement(distanceField).isEnabled()){
                distanceMatch = rule.getMaxDistance() == null ||
                        validateDistance(result, rule.getMaxDistance());
            }
            boolean employerMatch = rule.getEmployer() == null ||
                    employerMatches(rule.getEmployer().toLowerCase(),result);
            boolean jobRefMatch = rule.getJobReference() == null ||
                    result.getText().toLowerCase().contains(rule.getJobReference().toLowerCase());
            boolean payMatch = rule.getPayRange() == null ||
                    validatePayRange(result,rule.getPayRange());

            if (!(keywordMatch  && distanceMatch && employerMatch && jobRefMatch && payMatch))
            {
                logFailure(result, keywordMatch, distanceMatch,payMatch,employerMatch);
                failedScore++;
            }
        }
        passedScore=limit-failedScore;
        return passedScore >= 0.6 * limit;
    }



    private boolean validateDistance(WebElement result, double maxDistance) {

        String distanceText = result.findElement( By.cssSelector("li[data-test='search-result-distance']") ).getText().trim();
        double distance = Double.parseDouble( distanceText.replace("Distance:", "") .replace("miles", "") .trim() );
        return distance <= maxDistance;
    }

    private boolean validatePayRange(WebElement result, String selectedRange) {

        int[] filter = extractRange(selectedRange);
        int filterMin = filter[0];
        int filterMax = filter[1];
        String salaryText;
        try {
            salaryText=result.findElement(By.cssSelector("li[data-test='search-result-salary'] strong")).getText();
        } catch (Exception e) {
            return false;
        }
        int[] jobRange = extractRange(salaryText);
        int jobMin = jobRange[0];
        int jobMax = jobRange[1];
        return jobMax >= filterMin && jobMin <= filterMax;
    }

    private int[] extractRange(String text) {

        List<Integer> numbers = new ArrayList<>();
        Matcher matcher = Pattern.compile("(\\d{2,3}(?:,\\d{3})*)") .matcher(text);
        while (matcher.find()) {
            numbers.add( Integer.parseInt( matcher.group(1).replace(",", "") ) );
        }
        if (numbers.size() >= 2) {
            return new int[]{numbers.get(0), numbers.get(1)};
        }
        if (numbers.size() == 1) {
            return new int[]{numbers.get(0), numbers.get(0)};
        }
        return new int[]{0, 0};
    }
    private boolean employerMatches(String actualEmployer,
                                    WebElement result) {
        try {
            WebElement employerElement =
                    result.findElement(By.cssSelector("h3.nhsuk-u-font-weight-bold"));

            String expectedEmployer =
                    (String) ((JavascriptExecutor) driver)
                            .executeScript(
                                    "return arguments[0].childNodes[0].textContent.trim();",
                                    employerElement);



        if (expectedEmployer == null || expectedEmployer.isBlank()) {
            return true;
        }

        String actual = normalize(actualEmployer);
        String expected = normalize(expectedEmployer);

        Set<String> actualWords =
                new HashSet<>(Arrays.asList(actual.split(" ")));

        Set<String> expectedWords =
                new HashSet<>(Arrays.asList(expected.split(" ")));

        long matchedWords = expectedWords.stream()
                .filter(actualWords::contains)
                .count();

        double matchPercentage =
                (double) matchedWords / expectedWords.size();
        return matchPercentage >= 0.6;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    private String normalize(String text) {

        if (text == null || text.isBlank()) {
            return "";
        }

        return text.toLowerCase()
                .replaceAll("[^a-z0-9 ]", " ")
                .replaceAll("\\s+", " ")
                .trim();
    }
    public boolean areResultsSortedByNewestDate() {

        Select sort =
                new Select(find(sortDropdown));

        sort.selectByVisibleText("Date Posted (newest)");

        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOfElementLocated(resultsList));
        List<WebElement> results = driver.findElements(By.className("search-result"));

        if (results.isEmpty())
            return false;

        LocalDate previousDate = null;

        for (WebElement result : results) {

            String dateText = result.findElement( By.cssSelector("li[data-test='search-result-publicationDate'] strong") ).getText().trim();
            dateText = dateText.replaceAll("(?i)date posted:\\s*", "").trim();
            LocalDate currentDate = LocalDate.parse( dateText, DateTimeFormatter.ofPattern("d MMMM yyyy") );

            if (previousDate != null && currentDate.isAfter(previousDate)) {
                return false;
            }
            previousDate = currentDate;
        }
        return true;
    }

    public boolean areResultsDisplayed() {

        return !findAll(resultsList).isEmpty();
    }

    public boolean isNoResultsStateValid() {

        return isDisplayed(noResultsMessage);

    }

    private void logFailure(WebElement result, boolean keyword, boolean distance, boolean pay, boolean employer) {

        System.out.println("❌ INVALID RESULT:");
        System.out.println(result.getText());
        System.out.println("keyword=" + keyword);
        System.out.println("distance=" + distance);
        System.out.println("pay=" + pay);
        System.out.println("employer=" + employer);
    }


}