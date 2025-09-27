package com.flipkart.base;

import com.flipkart.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import java.util.List;

/**
 * Base page class that provides common functionality for all page objects.
 * Implements page object model pattern with common operations.
 */
public abstract class Base {
    
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected WaitUtils waitUtils;
    protected static final Logger logger = LoggerFactory.getLogger(Base.class);
    
    public Base(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    /**
     * Get page title - Common across all platforms
     */
    public String getPageTitle() {
        try {
            return driver.getTitle();
        } catch (Exception e) {
            logger.warn("Could not get page title: {}", e.getMessage());
            return "Unknown";
        }
    }

    /**
     * Perform search operation - to be implemented by child classes
     */
    public abstract void performSearch(String searchTerm);

    /**
     * Click on an element with wait
     */
    @Step("Click on element: {locator}")
    protected void clickElement(By locator) {
        WebElement element = waitUtils.waitForElementToBeClickable(locator);
        element.click();
    }
    /**
     * Click on web element with wait
     */
    @Step("Click on element")
    protected void clickElement(WebElement element) {
        waitUtils.waitForElementToBeClickable(element);
        element.click();
    }
    
    /**
     * Enter text in element
     */
    @Step("Enter text '{text}' in element: {locator}")
    protected void enterText(By locator, String text) {
        WebElement element = waitUtils.waitForElementToBeVisible(locator);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Enter text in web element
     */
    @Step("Enter text '{text}' in element")
    protected void enterText(WebElement element, String text) {
        waitUtils.waitForElementToBeVisible(element);
        element.clear();
        element.sendKeys(text);
    }
    
    /**
     * Get text from element
     */
    @Step("Get text from element: {locator}")
    protected String getText(By locator) {
        WebElement element = waitUtils.waitForElementToBeVisible(locator);
        return element.getText();
    }
    
    /**
     * Get text from web element
     */
    @Step("Get text from element")
    protected String getText(WebElement element) {
        waitUtils.waitForElementToBeVisible(element);
        return element.getText();
    }
    
    /**
     * Check if element is displayed
     */
    @Step("Check if element is displayed: {locator}")
    protected boolean isElementDisplayed(By locator) {
        try {
            return waitUtils.waitForElementToBeVisible(locator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Check if web element is displayed
     */
    @Step("Check if element is displayed")
    protected boolean isElementDisplayed(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Get all elements by locator
     */
    protected List<WebElement> getElements(By locator) {
        return waitUtils.waitForElementsToBeVisible(locator);
    }

    /**
     * Common search validation - can be used by all platforms
     */
    protected void validateSearchTerm(String searchTerm) {
        if (searchTerm == null || searchTerm.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be null or empty");
        }
    }
    
    /**
     * Scroll to element (for mobile)
     */
    @Step("Scroll to element")
    protected void scrollToElement(WebElement element) {
        if (driver instanceof AppiumDriver) {
            // Mobile specific scrolling logic
            ((AppiumDriver) driver).executeScript("mobile: scroll",
                "{'strategy': 'accessibility id', 'selector': '" + element.toString() + "'}");
        }
    }

    public void clickSearchResultByText(String text) {
        String dynamicXpath = String.format("//android.widget.TextView[@text='%s']", text);
        WebElement element = driver.findElement(By.xpath(dynamicXpath));
        element.click();
    }

    /**
     * wait for an element visible with custom timeout
     */
    @Step("Check if element is visible ")
    public void waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        WebDriverWait customWait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        customWait.until(ExpectedConditions.visibilityOf(element));
    }
    
    /**
     * Wait for page to load - to be implemented by child classes
     */
    public abstract void waitForPageToLoad();
    
    /**
     * Verify page is loaded - to be implemented by child classes
     */
    public abstract boolean isPageLoaded();
}
