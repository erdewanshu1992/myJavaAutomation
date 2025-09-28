package com.amazon.steps;

import com.flipkart.base.BaseTest;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.amazon.steps"},
        plugin = {"pretty", "html:target/cucumber-report.html", "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"},
        tags = "@AmazonTest",
        monochrome = true
)

public class TestRunner extends BaseTest {
}
