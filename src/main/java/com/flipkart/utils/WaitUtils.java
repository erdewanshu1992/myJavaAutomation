package com.flipkart.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Utility class for explicit waits
 * Provides methods to wait for elements to be visible, clickable, or invisible,
 * as well as waiting for text to be present in an element.
 * This class uses WebDriverWait to handle waits in a more readable and maintainable way.
 * * Example usage:
 * * WebDriver driver = new ChromeDriver();
 * * WaitUtils waitUtils = new WaitUtils(driver);
 * * * By locator = By.id("elementId");
 * * WebElement element = waitUtils.waitForElementToBeVisible(locator);
 * * WebElement clickableElement = waitUtils.waitForElementToBeClickable(locator);
 * * List<WebElement> visibleElements = waitUtils.waitForElementsToBeVisible(locator);
 * * * boolean isInvisible = waitUtils.waitForElementToBeInvisible(locator);
 * * boolean isTextPresent = waitUtils.waitForTextToBePresentInElement(element, "Expected Text");
 * * This class is designed to be used with Selenium WebDriver and can be extended or modified
 * * to suit specific needs for waiting on elements in web applications.
 * * * Note: Ensure that the WebDriver instance is properly initialized before using this utility.
 * * @author Flipkart Automation Team (Dewanshu sr.qa)
 * * @version 1.0
 * * @since 2023-10-01
 * * @description This utility class provides methods for explicit waits in Selenium WebDriver tests.
 * * @package com.flipkart.utils
 * * @license This code is licensed under the Flipkart Open Source License.
 * * This class is part of the Flipkart automation framework and is intended to be used for enhancing test reliability by managing waits effectively.
 * * @see org.openqa.selenium.WebDriver
 * * @see org.openqa.selenium.support.ui.WebDriverWait
 * * @see org.openqa.selenium.support.ui.ExpectedConditions
 * * This class is designed to be used in both nativeMobile and web automation tests, providing a consistent way to handle waits across different platforms.
 * * @Epic("Flipkart Automation Framework")
 * * @Feature("Explicit Wait Utilities")
 * * This class is intended to be used in conjunction with TestNG or JUnit test frameworks.
 * * This class provides a centralized way to manage explicit waits for elements in web applications,
 * * allowing for more robust and reliable test execution.
 * * This class is designed to be used in conjunction with Selenium WebDriver and can be extended or modified
 * * to suit specific needs for waiting on elements in web applications.
 *
 */
public class WaitUtils {
    
    private final WebDriverWait wait;
    private final WebDriver driver;
    
    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    
    public WebElement waitForElementToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    public WebElement waitForElementToBeVisible(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
    
    public WebElement waitForElementToBeClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    public WebElement waitForElementToBeClickable(WebElement element) {
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public List<WebElement> waitForElementsToBeVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }
    
    public List<WebElement> waitForElementsToBeVisible(List<WebElement> elements) {
        return wait.until(ExpectedConditions.visibilityOfAllElements(elements));
    }
    
    public boolean waitForElementToBeInvisible(By locator) {
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }
    
    public boolean waitForTextToBePresentInElement(WebElement element, String text) {
        return wait.until(ExpectedConditions.textToBePresentInElement(element, text));
    }
    
    public void waitForPageToLoad() {
        wait.until(webDriver -> ((org.openqa.selenium.JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public void waitForUrlToContain(String urlFragment) {
        wait.until(ExpectedConditions.urlContains(urlFragment));
    }

    public void waitForUrlToBe(String url) {
        wait.until(ExpectedConditions.urlToBe(url));
    }
}
