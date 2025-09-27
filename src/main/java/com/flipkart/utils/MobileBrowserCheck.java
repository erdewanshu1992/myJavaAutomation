package com.flipkart.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Utility class to check mobile browser functionality using Appium
 * This example uses Android Chrome browser with Appium
 */
public class MobileBrowserCheck {
    public static void main(String[] args) throws MalformedURLException {
        WebDriver driver = null;

        UiAutomator2Options options = new UiAutomator2Options();
        options.setCapability("chromedriverExecutable", "/Users/dewanshun/Drivers/chromedriver");
        // options.setCapability("appium:chromedriverAutodownload", true);
        options.setCapability("platformName", "Android");
        options.setCapability("browserName", "Chrome");
        options.setCapability("automationName", "UiAutomator2");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        System.out.println("✅ Started session using UiAutomator2Options");

        // Interact with browser
        driver.get("https://www.google.com");
        System.out.println("Title: " + driver.getTitle());

        // Close session after 5 seconds
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            System.out.println("Interrupted: " + e.getMessage());
        }

        driver.quit();
    }
}
