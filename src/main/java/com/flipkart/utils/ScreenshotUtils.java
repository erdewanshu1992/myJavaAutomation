package com.flipkart.utils;

import com.flipkart.config.ConfigReader;
import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static io.qameta.allure.Allure.addAttachment;

/**
 * * ScreenshotUtils is a utility class for capturing and managing screenshots
 * * Usage:
 * * * ScreenshotUtils.takeScreenshot(driver, "testName");
 * * * This will capture a screenshot of the current state of the WebDriver
 * * and return it as a byte array.
 * * * ScreenshotUtils.saveScreenshotToFile(takesScreenshot, "testName");
 * * * This will save the screenshot to the configured path
 * * with a timestamped filename.
 * * * ScreenshotUtils.attachScreenshot(driver, "screenshotName");
 * * * This will attach the screenshot to the Allure report
 * * with the specified name.
 * * * Note: Ensure that the WebDriver instance passed to these methods
 * * implements TakesScreenshot.
 * * This class is designed to be used in conjunction with
 * * Allure for reporting purposes.
 * * * This class is part of the Flipkart automation framework and is intended to be used
 * * for enhancing test reporting and debugging by capturing screenshots during test execution.
 * * * @see org.openqa.selenium.TakesScreenshot
 * * @see io.qameta.allure.Allure
 * * @see org.apache.commons.io.FileUtils
 * * This class is designed to be used in both nativeMobile and web automation tests,
 * * providing a consistent way to capture and manage screenshots across different platforms.
 * * @Epic("Flipkart Automation Framework")
 * * @Feature("Screenshot Utilities")
 * * This class is intended to be used in conjunction with TestNG or JUnit test frameworks.
 * * * This class provides a centralized way to manage screenshots in tests,
 * * allowing for more robust and reliable test execution.
 * * * This class is designed to be used in conjunction with Selenium WebDriver
 * * and can be extended or modified
 * * to suit specific needs for capturing and managing screenshots in web applications.
 * * * @author Flipkart Automation Team (Dewanshu sr.qa)
 * * @version 1.0
 * * @since 2023-10-01
 * * @description This utility class provides methods for taking and saving screenshots
 * * and attaching them to Allure reports.
 *
 */
public class ScreenshotUtils {
    
    private static final ConfigReader config = ConfigReader.getInstance();
    
    public static byte[] takeScreenshot(WebDriver driver, String testName) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            byte[] screenshot = takesScreenshot.getScreenshotAs(OutputType.BYTES);
            
            // Save screenshot to file system as well
            saveScreenshotToFile(takesScreenshot, testName);
            
            return screenshot;
        } catch (Exception e) {
            System.err.println("Failed to take screenshot: " + e.getMessage());
            return new byte[0];
        }
    }
    
    public static String saveScreenshotToFile(TakesScreenshot takesScreenshot, String testName) {
        try {
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String fileName = testName + "_" + timestamp + ".png";
            
            File destFile = new File(config.getScreenshotPath() + fileName);
            destFile.getParentFile().mkdirs();
            
            FileUtils.copyFile(sourceFile, destFile);
            
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Failed to save screenshot: " + e.getMessage());
            return "";
        }
    }
    
    public static String captureScreenshot(WebDriver driver, String testName) {
        if (driver instanceof TakesScreenshot) {
            return saveScreenshotToFile((TakesScreenshot) driver, testName);
        }
        return "";
    }

    // Attach screenshot from WebDriver
    public static void attachScreenshot(WebDriver driver, String name) {
        try {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            addAttachment(name, "image/png", new ByteArrayInputStream(screenshot), ".png");
        } catch (Exception e) {
            addAttachment(name + " - Screenshot Error", e.getMessage());
        }
    }
}
