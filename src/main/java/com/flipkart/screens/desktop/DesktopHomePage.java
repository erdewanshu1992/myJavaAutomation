package com.flipkart.screens.desktop;

import com.flipkart.base.Base;
import com.flipkart.interfaces.desktop.IWebHomeScreen;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Desktop Home Page implementation
 */
public class DesktopHomePage extends Base implements IWebHomeScreen {
    
    @FindBy(xpath = "//input[@type=\"text\"]")
    private WebElement searchBox;
    
    public DesktopHomePage(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Wait for desktop home page to load")
    public void waitForPageToLoad() {
        waitUtils.waitForElementToBeVisible(searchBox);
        waitUtils.waitForPageToLoad();
    }

    @Override
    @Step("Verify desktop home page is loaded")
    public boolean isPageLoaded() {
        return isElementDisplayed(searchBox);
    }

    @Override
    @Step("Search for '{searchTerm}' on desktop")
    public void performSearch(String searchTerm) {
        validateSearchTerm(searchTerm);
        clickElement(searchBox);
        enterText(searchBox, searchTerm);
        searchBox.sendKeys(org.openqa.selenium.Keys.ENTER);
        logger.info("✅ Desktop search performed for: {}", searchTerm);
    }

    // Web-specific implementations
    @Override
    @Step("Navigate to {destination} on desktop")
    public void navigateTo(String destination) {
        throw new UnsupportedOperationException("Navigation not supported with current locators");
    }

    @Override
    @Step("Check if user is logged in on desktop")
    public boolean isUserLoggedIn() {
        throw new UnsupportedOperationException("Login status check not supported");
    }

    @Override
    @Step("Handle cookie consent on desktop")
    public void handleCookieConsent() {
        // Not implemented
    }

    @Override
    @Step("Switch language to {language} on desktop")
    public void switchLanguage(String language) {
        throw new UnsupportedOperationException("Language switching not supported");
    }
}
