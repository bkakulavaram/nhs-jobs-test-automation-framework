package com.nhs.automation.framework.pages;

import com.nhs.automation.framework.models.ResultRule;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResultsPage extends BasePage {

    public ResultsPage(WebDriver driver) {
        super(driver);
    }

    private By resultsList = By.className("search-result");
    private By jobTitle = By.cssSelector("a");
    private By datePosted = By.cssSelector("li[data-test='search-result-publicationDate'] strong");
    private By distanceField = By.id("distance");
    private By whatField = By.id("keyword");
    private By sortDropdown = By.id("sort");


    public boolean validateResults(ResultRule rule) {

        List<WebElement> results = driver.findElements(resultsList);


        for (WebElement result : results) {

            String title = result.findElement(jobTitle).getText().toLowerCase();
            System.out.println(rule);
            boolean keywordMatch =
                    rule.getKeyword() == null ||
                            title.contains(rule.getKeyword().toLowerCase());

            boolean dateMatch = validateDate(result, rule.getMaxDays());
            boolean distanceMatch =true;
            if (driver.findElement(distanceField).isEnabled()){
             distanceMatch =
                    rule.getMaxDistance() == null ||
                            validateDistance(result, rule.getMaxDistance());}

            boolean employerMatch =
                    rule.getEmployer() == null ||
                            result.getText().toLowerCase().contains(rule.getEmployer().toLowerCase());

            boolean jobRefMatch =
                    rule.getJobReference() == null ||
                            result.getText().toLowerCase().contains(rule.getJobReference().toLowerCase());

            boolean payMatch =
                    rule.getPayRange() == null ||
                            validatePayRange(result,rule.getPayRange());

            if (!(keywordMatch && dateMatch && distanceMatch
                    && employerMatch && jobRefMatch && payMatch)) {

                logFailure(result, keywordMatch, dateMatch, distanceMatch,payMatch,employerMatch);
                return false;
            }
        }

        return true;
    }


    private boolean validateDate(WebElement result, int maxDays) {

        String dateText = result.findElement(datePosted).getText().trim();
        dateText = dateText.replaceAll("(?i)date posted:\\s*", "").trim();

        LocalDate postedDate = LocalDate.parse(
                dateText,
                DateTimeFormatter.ofPattern("d MMMM yyyy")
        );

        long days = ChronoUnit.DAYS.between(postedDate, LocalDate.now());

        return days <= maxDays;
    }



    private boolean validateDistance(WebElement result, double maxDistance) {

        String distanceText = result.findElement(
                By.cssSelector("li[data-test='search-result-distance']")
        ).getText().trim();

        double distance = Double.parseDouble(
                distanceText.replace("Distance:", "")
                        .replace("miles", "")
                        .trim()
        );
        System.out.println(distance);
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

        return jobMax >= filterMin &&
                jobMin <= filterMax;
    }

    private int[] extractRange(String text) {

        List<Integer> numbers = new ArrayList<>();

        Matcher matcher = Pattern.compile("(\\d{2,3}(?:,\\d{3})*)")
                .matcher(text);

        while (matcher.find()) {
            numbers.add(
                    Integer.parseInt(
                            matcher.group(1).replace(",", "")
                    )
            );
        }

        if (numbers.size() >= 2) {
            return new int[]{numbers.get(0), numbers.get(1)};
        }

        if (numbers.size() == 1) {
            return new int[]{numbers.get(0), numbers.get(0)};
        }


        return new int[]{0, 0};
    }


    private String safeValue(By locator) {
        try {
            return driver.findElement(locator).getAttribute("value");
        } catch (Exception e) {
            return "";
        }
    }

    private void logFailure(WebElement result,
                            boolean keyword,
                            boolean date,
                            boolean distance,
                            boolean pay,
                            boolean employer) {

        System.out.println("❌ INVALID RESULT:");
        System.out.println(result.getText());
        System.out.println("keyword=" + keyword);
        System.out.println("date=" + date);
        System.out.println("distance=" + distance);
        System.out.println("pay=" + pay);
        System.out.println("employer=" + employer);

    }


    public boolean areResultsSortedByNewestDate() {
        new Select(driver.findElement(sortDropdown)).selectByVisibleText("Date Posted (newest)");

        List<WebElement> results = driver.findElements(By.className("search-result"));

        if (results.isEmpty()) return false;

        LocalDate previousDate = null;

        for (WebElement result : results) {

            String dateText = result.findElement(
                    By.cssSelector("li[data-test='search-result-publicationDate'] strong")
            ).getText().trim();

            dateText = dateText.replaceAll("(?i)date posted:\\s*", "").trim();

            LocalDate currentDate = LocalDate.parse(
                    dateText,
                    DateTimeFormatter.ofPattern("d MMMM yyyy")
            );

            if (previousDate != null && currentDate.isAfter(previousDate)) {
                return false;
            }

            previousDate = currentDate;
        }

        return true;
    }


    public boolean areResultsDisplayed() {
        return !driver.findElements(resultsList).isEmpty();
    }

}

