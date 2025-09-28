package com.amazon.steps;

import com.flipkart.base.BaseTest;
import com.flipkart.config.ConfigReader;
import com.flipkart.driver.DriverManager;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.testng.Assert.assertTrue;

public class AmazonSteps {

    private static final Logger logger = LoggerFactory.getLogger(AmazonSteps.class);
    private WebDriver driver;
    private final ConfigReader config = ConfigReader.getInstance();

    @Given("I open the Amazon website")
    public void i_open_amazon() {
        // Use framework's driver management
        DriverManager driverManager = new DriverManager();
        driver = driverManager.initializeWebDriver();
        logger.info("🌐 Opened Amazon website");
        driver.get("https://www.amazon.in/");
    }

    @When("I search for {string}")
    public void i_search_for(String product) {
        logger.info("🔍 Searching for product: {}", product);
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.clear();
        searchBox.sendKeys(product);
        driver.findElement(By.id("nav-search-submit-button")).click();
    }

    @Then("I should see results related to {string}")
    public void i_should_see_results_related_to(String product) {
        logger.info("✅ Verifying search results for: {}", product);
        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.toLowerCase().contains(product.toLowerCase()),
            "Page title should contain the search term: " + product);
        logger.info("🎯 Search verification successful");
    }
}
