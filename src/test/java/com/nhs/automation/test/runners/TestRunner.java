package com.nhs.automation.test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "com.nhs.automation.test.stepdefinitions",
                "com.nhs.automation.framework.hooks"
        },
        plugin = {
                "pretty",
                "html:target/cucumber-report.html",
                "json:target/cucumber.json"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {


}
