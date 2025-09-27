package com.amazon.steps;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.amazon.steps"},
        plugin = {"pretty", "html:target/cucumber-report.html"},
        // strict = true, // Deprecated in recent versions
        tags = "@AmazonTest",
        monochrome = true
)

public class TestRunner extends AbstractTestNGCucumberTests {
}
