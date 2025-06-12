package com.flipkart.utils;


import org.openqa.selenium.remote.RemoteWebDriver;

/*
 * Utility class to fetch device details from Appium driver capabilities
 * * @author Flipkart Automation Team (Dewanshu sr.qa)
 * * @version 1.0
 * * @since 2023-10-01
 * * @description This class provides a method to retrieve device details such as platform name, device name, and OS version from the Appium driver capabilities.
 * * * This class is designed to be used in nativeMobile automation tests using Appium and Selenium.
 * * @package com.flipkart.utils
 * * @license This code is licensed under the Flipkart Open Source License.
 * * This class is part of the Flipkart automation framework and is intended to be used for enhancing test documentation and reporting.
 * * This class provides a centralized way to manage device information retrieval in nativeMobile tests.
 * * This class is designed to be used in conjunction with Appium and Selenium WebDriver for nativeMobile automation.
 * * @Epic("Flipkart Mobile Automation")
 * * @Feature("Device Information Retrieval")
 * * This class is intended to be used in nativeMobile automation tests to fetch device details dynamically from the Appium driver.
 * * This class provides a method to retrieve device details such as platform name, device name, and OS version from the Appium driver capabilities.
 * * This class is designed to be used in nativeMobile automation tests using Appium and Selenium WebDriver.
 * * @see org.openqa.selenium.remote.RemoteWebDriver
 * * @see io.appium.java_client.android.AndroidDriver
 *
 */

public class DeviceUtils {

    public static String getDeviceDetails(RemoteWebDriver driver) {
        try {
            String platformName = driver.getCapabilities().getPlatformName().toString();
            String deviceName = driver.getCapabilities().getCapability("deviceName").toString();
            String platformVersion = driver.getCapabilities().getCapability("platformVersion").toString();

            return "Platform: " + platformName +
                    "\nDevice: " + deviceName +
                    "\nOS Version: " + platformVersion;
        } catch (Exception e) {
            return "Device info not available: " + e.getMessage();
        }
    }
}
