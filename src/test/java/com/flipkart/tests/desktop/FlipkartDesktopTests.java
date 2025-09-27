package com.flipkart.tests.desktop;

import com.flipkart.base.BaseTest;
import com.flipkart.screens.desktop.DesktopHomePage;
import com.flipkart.utils.PlatformManager;
import io.qameta.allure.*;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

/**
 * Test class for Flipkart desktop web application
 */
@Epic("Flipkart Desktop Web Testing")
@Feature("Desktop Shopping Features")
public class FlipkartDesktopTests extends BaseTest {
     private static final Logger logger = LogManager.getLogger(FlipkartDesktopTests.class);

    private DesktopHomePage homePage;

    @BeforeSuite
    public void cleanLogsFolder() {
        try {
            FileUtils.deleteDirectory(new File("logs"));
            new File("logs").mkdirs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @BeforeClass
    public void setDesktopPlatform() {
        // Force desktop platform for this test class
        System.setProperty("platform", "desktop");
         logger.info("🖥️ Forcing platform to DESKTOP for this test class");
    }

    private void initializeScreens() {
        if (homePage == null) {
            homePage = new DesktopHomePage(driverManager.getDriver());
             logger.info("Desktop screen objects initialized");
        }
    }

    @Epic("Search")
    @Feature("Search functionality")
    @Story("Search items")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verify user can login with valid credentials")
    @Test(description = "Verify search functionality on desktop")
    public void testDesktopSearch() {
        // Verify we're running in desktop mode
        PlatformManager.PlatformType platform = PlatformManager.getPlatform();
         logger.info("🎯 Current platform: {}", platform);
        Assert.assertEquals(platform, PlatformManager.PlatformType.DESKTOP,
            "Test should run in DESKTOP mode");
        
        // Initialize screen objects
        initializeScreens();
        
        // Ensure home page is loaded
        homePage.waitForPageToLoad();
        Assert.assertTrue(homePage.isPageLoaded(), "Home page should be loaded before search");
        
        // Perform search for 'Laptop'
        homePage.performSearch("Laptop");
        
        // Verify search results navigation
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        String currentUrl = driverManager.getDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("search") || currentUrl.contains("laptop"), 
            "Should navigate to search results page, current URL: " + currentUrl);
    }
}
