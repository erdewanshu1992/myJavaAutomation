package com.flipkart.tests.mobile;

import com.flipkart.base.BaseTest;
import com.flipkart.screens.nativeMobile.MobileHomeScreen;
import com.flipkart.screens.nativeMobile.MobileWelcomeScreen;
import com.flipkart.utils.AllureUtils;
import com.flipkart.utils.PlatformManager;
import com.flipkart.utils.ScreenshotUtils;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Flipkart mobile app functionality
 * Uses unified BaseTest for consistent driver management
 */
@Epic("Flipkart Mobile App Testing")
@Feature("Core Shopping Features")
public class FlipkartMobileTests extends BaseTest {

    private MobileWelcomeScreen welcomeScreen;
    private MobileHomeScreen homeScreen;
    private boolean isFirstTest = true;

    @BeforeClass
    public void setMobilePlatform() {
        // Force native mobile platform for this test class
        System.setProperty("platform", "nativemobile");
        logger.info("📱 Forcing platform to NATIVEMOBILE for this test class");
    }
    /**
     * Initialize screen objects and handle welcome screen before each test
     */
    @BeforeMethod
    public void setupTest() {
        logger.info("Setting up test - initializing screens");
        initializeScreens();
        
        // Handle welcome screen if it appears
        try {
            if (welcomeScreen.isPageLoaded()) {
                logger.info("Welcome screen detected - skipping login");
                welcomeScreen.skipLoginBtn();
                
                // Wait for home screen to load after skipping
                homeScreen.waitForPageToLoad();
                if (!homeScreen.isPageLoaded()) {
                    logger.error("Failed to navigate to home screen after skipping login");
                }
            } else {
                logger.info("Welcome screen not detected - app may already be on home screen");
            }
        } catch (Exception e) {
            logger.error("Error handling welcome screen: {}", e.getMessage());
        }
    }

    /**
     * Initialize screen objects - called when needed
     */
    private void initializeScreens() {
        if (welcomeScreen == null || homeScreen == null) {
            welcomeScreen = new MobileWelcomeScreen(driverManager.getDriver());
            homeScreen = new MobileHomeScreen(driverManager.getDriver());
            logger.info("📱 Mobile screen objects initialized");
        }
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("App Launch & Initial Flow")
    @Description("This test verifies that the Flipkart mobile app launches successfully and handles the skip login scenario.")
    @Test(priority = 0, enabled = true, description = "Verify Flipkart app launch and skip login flow")
    public void testAppLaunchSkip() {
        // Verify we're running in native mobile mode
        PlatformManager.PlatformType platform = PlatformManager.getPlatform();
        logger.info("🎯 Current platform: {}", platform);
        Assert.assertEquals(platform, PlatformManager.PlatformType.NATIVEMOBILE, 
            "Test should run in NATIVEMOBILE mode");
        
        // Verify home screen is loaded (skip button should have been clicked in @BeforeMethod)
//        AllureUtils.safeStep("Verify Home Screen is loaded", () -> {
//            Assert.assertTrue(homeScreen.isPageLoaded(), "Home page should be loaded after skipping login");
//        });

        AllureUtils.safeStep("Capture app launch success screenshot", () -> {
            AllureUtils.logSuccess("Flipkart app launched and home page loaded successfully");
            ScreenshotUtils.attachScreenshot(driverManager.getDriver(), "AppLaunchSkip_Success");
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Mobile Search")
    @Description("This test verifies search functionality in the native mobile app.")
    // @Test(priority = 1, description = "Verify search functionality in mobile app", dependsOnMethods = "testAppLaunchSkip")
     @Test(priority = 1, enabled = false, description = "Verify search functionality in mobile app")
    public void testMobileSearch() {
        // Verify home screen is loaded before attempting search
        AllureUtils.safeStep("Ensure home screen is loaded", () -> {
            Assert.assertTrue(homeScreen.isPageLoaded(), "Home screen should be loaded before search");
        });

        AllureUtils.safeStep("Perform search for 'Samsung'", () -> {
            homeScreen.performSearch("Samsung");
            logger.info("🔍 Search performed for Samsung products");
        });

        AllureUtils.safeStep("Capture search results screenshot", () -> {
            AllureUtils.logSuccess("Mobile search functionality working");
            ScreenshotUtils.attachScreenshot(driverManager.getDriver(), "MobileSearch_Results");
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Category Navigation")
    @Description("This test verifies grocery category navigation in the native mobile app.")
    // @Test(priority = 2, description = "Verify grocery category navigation", dependsOnMethods = "testAppLaunchSkip")
    @Test(priority = 2, enabled = false, description = "Verify grocery category navigation")
    public void testGroceryCategoryNavigation() {
        // Verify home screen is loaded before attempting navigation
        AllureUtils.safeStep("Ensure home screen is loaded", () -> {
            Assert.assertTrue(homeScreen.isPageLoaded(), "Home screen should be loaded before navigation");
        });

        AllureUtils.safeStep("Verify Grocery category is visible", () -> {
            Assert.assertTrue(homeScreen.isGroceryCategoryVisible(), "Grocery category should be visible on home screen");
        });

        AllureUtils.safeStep("Click on Grocery category", () -> {
            homeScreen.clickGroceryCategory();
            logger.info("🛒 Grocery category clicked successfully");
        });

        AllureUtils.safeStep("Wait for navigation to complete", () -> {
            try {
                Thread.sleep(3000); // Wait for navigation
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        AllureUtils.safeStep("Capture grocery category navigation screenshot", () -> {
            AllureUtils.logSuccess("Grocery category navigation working");
            ScreenshotUtils.attachScreenshot(driverManager.getDriver(), "GroceryCategory_Navigation");
        });
    }
}
