package com.amazon.steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.testng.Assert.assertTrue;

public class AmazonSteps {

    WebDriver driver;

    @Given("I open the Amazon website")
    public void i_open_amazon() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.amazon.in/");
    }

    @When("I search for {string}")
    public void i_search_for(String product) {
        WebElement searchBox = driver.findElement(By.id("twotabsearchtextbox"));
        searchBox.sendKeys(product);
        driver.findElement(By.id("nav-search-submit-button")).click();
    }

    @Then("I should see results related to {string}")
    public void i_should_see_results_related_to(String product) {
        String pageTitle = driver.getTitle();
        assertTrue(pageTitle.toLowerCase().contains(product.toLowerCase()));
        driver.quit();
    }
}
