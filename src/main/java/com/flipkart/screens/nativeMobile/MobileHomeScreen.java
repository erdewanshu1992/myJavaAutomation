package com.flipkart.screens.nativeMobile;

import com.flipkart.base.Base;
import com.flipkart.interfaces.mobile.IMobileHomeScreen;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

/**
 * Native Mobile Home Screen implementation
 */
public class MobileHomeScreen extends Base implements IMobileHomeScreen {
    
    @AndroidFindBy(uiAutomator = "new UiSelector().textContains(\"Brand Mall\")")
    private WebElement brandMallToggle;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(118)")
    private WebElement searchBoxHome;

    @AndroidFindBy(uiAutomator = "new UiSelector().className(\"android.view.ViewGroup\").instance(6)")
    private WebElement searchBoxField;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Categories']")
    private WebElement categoriesTab;

    @AndroidFindBy(uiAutomator = "new UiSelector().resourceId(\"com.flipkart.android:id/pincode\")")
    private WebElement pinCodeElement;

    @AndroidFindBy(uiAutomator = "new UiSelector().text(\"Grocery \")")
    private WebElement groceryCats;

    public MobileHomeScreen(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Wait for home page to load")
    public void waitForPageToLoad() {
        // waitUtils.waitForElementToBeVisible(brandMallToggle);
        // waitUtils.waitForElementToBeVisible(categoriesTab);

        try {
            waitUtils.waitForElementToBeVisible(brandMallToggle);
            waitUtils.waitForElementToBeVisible(categoriesTab);
            waitUtils.waitForElementToBeVisible(searchBoxHome);
        } catch (Exception e) {
            logger.error(" searchBox not visible within timeout: {}", e.getMessage());
        }
    }

    @Override
    @Step("Verify home page is loaded")
    public boolean isPageLoaded() {
        try {
            boolean brandMall = isElementDisplayed(brandMallToggle);
            boolean search = isElementDisplayed(searchBoxHome);
            boolean category = isElementDisplayed(categoriesTab);

            logger.info("BrandMall: {}, SearchBox: {}, CategoriesTab: {}", brandMall, search, category);

            return brandMall && category;
        } catch (Exception e) {
            logger.error("❌ Exception while verifying home page load: {}", e.getMessage());
            return false;
        }
    }


    @Override
    @Step("Search for '{searchTerm}' in mobile app")
    public void performSearch(String searchTerm) {
        validateSearchTerm(searchTerm);
        clickElement(searchBoxHome);
        enterText(searchBoxField, searchTerm);
        // clickElement(searchBoxField);
        logger.info("✅ Mobile search performed for: {}", searchTerm);
    }

    @Step("Validate dynamic pincode text on Home screen")
    public void validateDynamicPincode(String expectedPinCode) {
        waitUtils.waitForElementToBeVisible(pinCodeElement);

        String actualPinCode = pinCodeElement.getText();
        logger.info("📦 Actual Pincode displayed: {}", actualPinCode);
        logger.info("🧾 Expected Pincode: {}", expectedPinCode);

        Assert.assertEquals(actualPinCode, expectedPinCode, "Mismatch in pincode displayed on home screen!");
    }

    @Step("Click on Grocery category")
    public void clickGroceryCategory() {
        try {
            waitUtils.waitForElementToBeVisible(groceryCats);
            clickElement(groceryCats);
            logger.info("✅ Grocery category clicked successfully");
        } catch (Exception e) {
            logger.error("❌ Failed to click Grocery category: {}", e.getMessage());
            throw new RuntimeException("Failed to click Grocery category", e);
        }
    }

    @Step("Verify Grocery category is visible")
    public boolean isGroceryCategoryVisible() {
        try {
            boolean visible = isElementDisplayed(groceryCats);
            logger.info("Grocery category visible: {}", visible);
            return visible;
        } catch (Exception e) {
            logger.error("Error checking Grocery category visibility: {}", e.getMessage());
            return false;
        }
    }

    // Mobile-specific implementations
    @Override
    @Step("Navigate to {tabName} tab")
    public void navigateToTab(String tabName) {
        throw new UnsupportedOperationException("Tab navigation not supported with current locators");
    }

    @Override
    @Step("Handle app permissions")
    public void handlePermissions() {
        logger.info("Handling mobile app permissions");
    }

    @Override
    @Step("Swipe left")
    public void swipeLeft() {
        if (driver instanceof AppiumDriver) {
            logger.info("Swiping left on mobile");
        }
    }

    @Override
    @Step("Swipe right")
    public void swipeRight() {
        if (driver instanceof AppiumDriver) {
            logger.info("Swiping right on mobile");
        }
    }

    @Override
    @Step("Swipe up")
    public void swipeUp() {
        if (driver instanceof AppiumDriver) {
            logger.info("Swiping up on mobile");
        }
    }

    @Override
    @Step("Swipe down")
    public void swipeDown() {
        if (driver instanceof AppiumDriver) {
            logger.info("Swiping down on mobile");
        }
    }

    @Override
    @Step("Press back button")
    public void pressBackButton() {
        if (driver instanceof AppiumDriver) {
            ((AppiumDriver) driver).navigate().back();
            logger.info("Pressed back button on mobile");
        }
    }

//    @Step("Coordinate Tapping")
//    public void tapAt(int x, int y, RemoteWebDriver driver) {
//        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
//        Sequence tap = new Sequence(finger, 1);
//        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
//        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
//        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
//        driver.perform(Arrays.asList(tap));
//    }

    @Step("Coordinate Tapping")
    public void tapAt(int x, int y, AndroidDriver driver) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);

        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Arrays.asList(tap));
    }

    @Step("Swiping")
    public void swipeHorizontal(int startX, int startY, int endX, int endY, RemoteWebDriver driver) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), startX, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(700), PointerInput.Origin.viewport(), endX, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));
    }



}
