package com.flipkart.tests.responsive;

import com.flipkart.base.BaseTest;
import com.flipkart.listeners.TestListener;
import com.flipkart.screens.responsive.ResponsiveHomeScreen;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

/**
 * Test class for Flipkart responsive web application
 */
@Epic("Flipkart Responsive Web Testing")
@Feature("Mobile Emulation Testing")
@Listeners(TestListener.class)
public class FlipkartResponsiveTests extends BaseTest {
    private static final Logger logger = LogManager.getLogger(FlipkartResponsiveTests.class);


    private ResponsiveHomeScreen homeScreen;

    @BeforeSuite
    public void cleanLogsFolder() {
        try {
            FileUtils.deleteDirectory(new File("logs"));
            new File("logs").mkdirs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeMethod
    /**
     * Initialize screen objects
     */
    private void initializeScreens() {
        if (homeScreen == null) {
            homeScreen = new ResponsiveHomeScreen(driverManager.getDriver());
            logger.info("Responsive screen objects initialized");
            logger.info("Starting automation test...");
        }
    }

    @Epic("Scroll")
    @Feature("Scroll functionality")
    @Story("Scroll home")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login with valid credentials")
    @Test(description = "Verify search functionality in responsive view")
    public void testResponsiveSearch() {
        // Initialize screen objects
        initializeScreens();
        
        // Ensure home page is loaded
        homeScreen.waitForPageToLoad();
        Assert.assertTrue(homeScreen.isPageLoaded(), "Home page should be loaded before search");
        
        // Perform search for 'iPhone'
        homeScreen.performSearch("iPhone");
        
        // Verify search results navigation
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String currentUrl = driverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("search") || currentUrl.contains("iPhone"), 
            "Should navigate to search results page, current URL: " + currentUrl);
    }
}
