package com.flipkart.screens.nativeMobile;

import com.flipkart.base.Base;
import com.flipkart.interfaces.mobile.IWelcomeScreen;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;

/**
 * Native Mobile Welcome Screen for Flipkart mobile app
 */
public class MobileWelcomeScreen extends Base implements IWelcomeScreen {

    private static final Logger logger = LoggerFactory.getLogger(MobileWelcomeScreen.class);
    
    @AndroidFindBy(id = "com.flipkart.android:id/custom_back_icon")
    private WebElement skipButton;

    public MobileWelcomeScreen(WebDriver driver) {
        super(driver);
    }

    @Override
    @Step("Verify Welcome page is loaded")
    public boolean isPageLoaded() {
        try {
            boolean loaded = isElementDisplayed(skipButton);
            logger.info("Welcome screen loaded status: {}", loaded);
            return loaded;
        } catch (Exception e) {
            logger.info("Welcome screen not detected: {}", e.getMessage());
            return false;
        }
    }

    @Override
    @Step("Wait for Welcome page to load")
    public void waitForPageToLoad() {
        try {
            waitUtils.waitForElementToBeVisible(skipButton);
            logger.info("Welcome screen loaded successfully");
        } catch (Exception e) {
            logger.warn("Skip Button not found - app may already be past welcome screen: {}", e.getMessage());
        }
    }

    @Override
    @Step("Search functionality not available on Welcome screen")
    public void performSearch(String searchTerm) {
        throw new UnsupportedOperationException("Search functionality is not available on Welcome screen");
    }

    @Override
    @Step("Click on Skip button if present")
    public void skipLoginBtn() {
        try {
            if (isElementDisplayed(skipButton)) {
                skipButton.click();
                logger.info("✅ Skip button clicked successfully");
            } else {
                logger.info("ℹ️ Skip button not visible - app may already be past welcome screen");
            }
        } catch (Exception e) {
            logger.error("⚠️ Skip button not found or clickable: {}", e.getMessage());
        }
    }


    @Step("Tapping")
    public void tapElement(WebElement element, RemoteWebDriver driver){
        // 1. Create a finger input (touch input)
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // 2. Get element’s location (x, y)
        int x = element.getLocation().getX() + (element.getSize().getWidth() / 2);
        int y = element.getLocation().getY() + (element.getSize().getHeight() / 2);

        // 3. Create a sequence of actions: move, press down, and release
        Sequence tapSequence = new Sequence(finger, 1);
        tapSequence.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tapSequence.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tapSequence.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        // 4. Perform the action
        driver.perform(Arrays.asList(tapSequence));

    }

    @Step("Coordinate Tapping")
    public void tapAt(int x, int y, RemoteWebDriver driver) {
        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, y));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
        driver.perform(Arrays.asList(tap));
    }

}
