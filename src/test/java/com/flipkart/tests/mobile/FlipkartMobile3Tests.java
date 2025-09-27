package com.flipkart.tests.mobile;

import io.appium.java_client.android.options.UiAutomator2Options;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

public class FlipkartMobile3Tests {

    private static final Logger logger = LogManager.getLogger(FlipkartMobile3Tests.class);

    RemoteWebDriver driver;

    @BeforeSuite
    public void cleanLogsFolder() {
        try {
            FileUtils.deleteDirectory(new File("logs"));
            new File("logs").mkdirs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeTest
    public void setUp() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setAutomationName("UiAutomator2");
        options.setAppPackage("com.flipkart.android");
        options.setAppActivity("com.flipkart.android.activity.HomeFragmentHolderActivity");
        options.setNoReset(true);

        driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/"), options);
        System.out.println("✅ Driver started successfully.");
        logger.info("Starting automation test...");

    }

    @Epic("Login")
    @Feature("Login functionality")
    @Story("Valid login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login with valid credentials")
    @Test(enabled = false)
    public void testW3CTap() {
        WebElement elementToTap = driver.findElement(By.id("com.flipkart.android:id/custom_back_icon"));
        // tapElementW3C(elementToTap);
        tapElement(elementToTap, driver);
    }

    @Epic("Scroll")
    @Feature("Scroll functionality")
    @Story("Scroll home")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login with valid credentials")
    @Test
    public void testContinuousScroll() {
        Dimension size = driver.manage().window().getSize();
        int width = size.getWidth();
        int height = size.getHeight();

        int x = width / 2;
        int startY = (int) (height * 0.70);
        int endY = (int) (height * 0.30);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");

        // Number of swipes to perform
        int swipeCount = 3;

        for (int i = 0; i < swipeCount; i++) {
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), x, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            driver.perform(Collections.singletonList(swipe));
            System.out.println("✅ Swipe #" + (i + 1) + " performed");

            // Short pause so content can load/render
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // ignore
            }
        }

        System.out.println("🎯 Completed " + swipeCount + " continuous swipes");
    }

    @Test(enabled = false)
    public void scrollTopToBottom() {
        // 1. Get size
        Dimension size = driver.manage().window().getSize();

        int width = size.getWidth();
        int height = size.getHeight();

        int x = width / 2; // middle of the screen horizontally
        int startY = (int) (height * 0.70); // near bottom
        int endY = (int) (height * 0.30);   // near top (upward scroll)

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence swipe = new Sequence(finger, 1);

        swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), x, startY));
        swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        swipe.addAction(finger.createPointerMove(Duration.ofMillis(800), PointerInput.Origin.viewport(), x, endY));
        swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(swipe));

        System.out.println("✅ Swipe gesture from bottom to top done.");
    }




    public void tapElementW3C(WebElement element) {
        int centerX = element.getRect().getX() + (element.getRect().getWidth() / 2);
        int centerY = element.getRect().getY() + (element.getRect().getHeight() / 2);

        PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
        Sequence tap = new Sequence(finger, 1);
        tap.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), centerX, centerY));
        tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
        tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

        driver.perform(Collections.singletonList(tap));
        System.out.println("✅ Tap action performed using W3C.");
    }

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
}
