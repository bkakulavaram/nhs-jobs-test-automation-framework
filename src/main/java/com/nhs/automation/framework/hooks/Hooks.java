package com.nhs.automation.framework.hooks;

import com.nhs.automation.framework.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before
    public void setUp() {
        String browser = System.getProperty("browser", "chrome");
        DriverFactory.initDriver(browser);
        DriverFactory.getDriver().manage().window().maximize();
    }


    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts =
                    (TakesScreenshot) DriverFactory.getDriver();

            byte[] screenshot =
                    ts.getScreenshotAs(OutputType.BYTES);

            scenario.attach(
                    screenshot,
                    "image/png",
                    scenario.getName()
            );
        }
        DriverFactory.quitDriver();
    }
}