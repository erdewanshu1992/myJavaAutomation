package com.flipkart.screens.responsive;

import com.flipkart.base.Base;
import com.flipkart.interfaces.responsive.IResponsiveHomeScreen;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Responsive Home Screen implementation
 */
public class ResponsiveHomeScreen extends Base implements IResponsiveHomeScreen {
    
    @FindBy(xpath = "//*[contains(text(), \"Search for Products\")]")
    private WebElement searchBoxHome;

    @FindBy(xpath = "//input[@type=\"search\"]")
    private WebElement searchBoxField;
    
    public ResponsiveHomeScreen(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Wait for responsive home page to load")
    public void waitForPageToLoad() {
        try {
            waitUtils.waitForPageToLoad();
            waitUtils.waitForElementToBeVisible(By.xpath("//*[contains(text(), 'Search for Products')]"));
            logger.info("✅ Responsive home page loaded successfully");
        } catch (Exception e) {
            logger.error("❌ Failed to load responsive home page: {}", e.getMessage());
        }
    }

    @Override
    @Step("Verify responsive home page is loaded")
    public boolean isPageLoaded() {
        try {
            boolean pageLoaded = driver.findElement(By.xpath("//*[contains(text(), 'Search for Products')]")).isDisplayed();
            logger.info("Responsive page loaded status: {}", pageLoaded);
            return pageLoaded;
        } catch (Exception e) {
            logger.error("Error checking responsive page load status: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Step("Search for '{searchTerm}' in responsive view")
    public void performSearch(String searchTerm) {
        validateSearchTerm(searchTerm);
        try {
            logger.info("🔍 Step 1: Finding search box on home page");
            clickElement(searchBoxHome);
            logger.info("✅ Successfully clicked search element - navigating to search page");
            
            Thread.sleep(2000);
            
            logger.info("🔍 Step 2: Waiting for search field on search page");
            WebElement searchField = waitUtils.waitForElementToBeVisible(By.xpath("//input[@type='search']"));
            
            logger.info("✅ Search field found on search page");
            clickElement(searchField);
            searchField.clear();
            enterText(searchField, searchTerm);
            searchField.sendKeys(org.openqa.selenium.Keys.ENTER);
            logger.info("✅ Search performed successfully for: '{}'", searchTerm);
            
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.error("❌ Thread interrupted during search: {}", e.getMessage());
            throw new RuntimeException("Search interrupted", e);
        } catch (Exception e) {
            logger.error("❌ Failed to perform responsive search: {}", e.getMessage());
            throw new RuntimeException("Responsive search failed: " + e.getMessage(), e);
        }
    }

    @Override
    @Step("Navigate to {destination} in responsive view")
    public void navigateTo(String destination) {
        throw new UnsupportedOperationException("Navigation not supported with current locators");
    }

    @Override
    @Step("Check if user is logged in in responsive view")
    public boolean isUserLoggedIn() {
        throw new UnsupportedOperationException("Login status check not supported");
    }

    @Override
    @Step("Handle cookie consent in responsive view")
    public void handleCookieConsent() {
        // Not implemented
    }

    @Override
    @Step("Switch language to {language} in responsive view")
    public void switchLanguage(String language) {
        throw new UnsupportedOperationException("Language switching not supported");
    }

    @Override
    @Step("Handle mobile menu in responsive view")
    public void handleMobileMenu() {
        // Not implemented
    }

    @Override
    @Step("Check if mobile menu is expanded")
    public boolean isMobileMenuExpanded() {
        return false;
    }

    @Override
    @Step("Get responsive page title")
    public String getPageTitle() {
        try {
            String title = driver.getTitle();
            logger.info("Retrieved page title: {}", title);
            return title;
        } catch (Exception e) {
            logger.warn("Could not get responsive page title: {}", e.getMessage());
            return "Unknown";
        }
    }
}
