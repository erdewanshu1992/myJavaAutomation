import io.appium.java_client.AppiumBy;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class MobileBrowserTests {
    public static void main(String[] args) throws MalformedURLException, InterruptedException {
        WebDriver driver;

             //Real device setup
//            UiAutomator2Options options = new UiAutomator2Options();
//            options.setCapability("chromedriverExecutable", "/Users/dewanshun/Drivers/chromedriver");
//            options.setCapability("platformName", "Android");
//            options.setCapability("browserName", "Chrome");
//            options.setCapability("automationName", "UiAutomator2");
//            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
//            System.out.println("✅ Started session using UiAutomator2Options");

        // Android emulator
        UiAutomator2Options options = new UiAutomator2Options();
        options.setPlatformName("Android");
        options.setDeviceName("emulator-5554");
        options.setAutomationName("UiAutomator2");
        options.setCapability("browserName", "Chrome");

        String projectPath = System.getProperty("user.dir");
        String chromeDriverPath = projectPath + "/src/main/resources/c-driver/chromedriver";
        options.setChromedriverExecutable(chromeDriverPath);

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
        System.out.println("✅ Chrome browser launched on Android Emulator");


        driver.get("https://www.google.com");
        System.out.println("🌐 Title: " + driver.getTitle());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

        driver.get("https://www.yesmadam.com");
        System.out.println("🌐 Title: " + driver.getTitle());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        System.out.println("🌐 Final Title: " + driver.getTitle());

        Thread.sleep(5000);
        // driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));


        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 1; i <= 3; i++) {
            js.executeScript("window.scrollBy({ top: 1500, left: 0, behavior: 'smooth' });");
            Thread.sleep(2000);
            System.out.println("✅ Smooth scroll #" + i + " done on mobile Chrome");
        }

        // Ab dusra page kholna
        driver.get("https://www.flipkart.com");
        Thread.sleep(3000);

        System.out.println("✅ Opened second URL in same tab");

        WebElement groceryLink = driver.findElement(By.xpath("//a[contains(@href, 'grocery')]"));
        groceryLink.click();
        Thread.sleep(3000);
        System.out.println("🌐 Final Title: " + driver.getTitle());

        WebElement pinCode = driver.findElement(By.xpath("//input[contains(@placeholder,  'Enter Pincode Here')]"));
        pinCode.sendKeys("110096");
        Thread.sleep(3000);

        driver.findElement(By.xpath("//div[contains(text(),  'CONTINUE')]")).click();
        Thread.sleep(5000);

         // ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
         // driver.navigate().back();

        // WebElement serchItems = driver.findElement(By.xpath("//div[contains(text(),  'Search grocery products')]"));
        // serchItems.sendKeys("Atta");
        // Thread.sleep(3000);

//        for (int i = 1; i <= 1; i++) {
//            js.executeScript("window.scrollBy({ top: 1500, left: 0, behavior: 'smooth' });");
//            Thread.sleep(2000);
//            System.out.println("✅ Smooth scroll #" + i + " done on mobile Chrome");
//        }

        driver.findElement(By.xpath("//img[contains(@src, 'b2e8b172-0af8-43c4-9251-67c6f2442d83')]")).click();
        Thread.sleep(3000);
        System.out.println("🌐 Final Title: " + driver.getTitle());


        // WebElement travelLink = driver.findElement(By.xpath("//a[contains(@href,  'Travel')]"));
        // travelLink.click();
        // Thread.sleep(3000);
        // System.out.println("🌐 Final Title: " + driver.getTitle());





        // driver.quit();
        System.out.println("✅ Session closed");
    }
}












//package com.flipkart.utils;
//
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.nativekey.AndroidKey;
//import io.appium.java_client.android.nativekey.KeyEvent;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import io.appium.java_client.AppiumBy;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Utility class to check mobile browser functionality using Appium
// * This example uses Android Chrome browser with Appium
// */
//public class MobileBrowserTests {
//    public static void main(String[] args) throws MalformedURLException, InterruptedException {
//        WebDriver driver = null;
//
//        try {
//            // 🔁 Option 1: Using DesiredCapabilities
//            DesiredCapabilities caps = new DesiredCapabilities();
//            caps.setCapability("platformName", "Android");
//            caps.setCapability("browserName", "Chrome");
//            caps.setCapability("automationName", "UiAutomator2");
//            caps.setCapability("chromedriverExecutable", "/Users/dewanshun/Drivers/chromedriver");
//
//            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), caps);
//            System.out.println("✅ Started session using DesiredCapabilities");
//
//        } catch (Exception e1) {
//            System.out.println("❌ Failed with DesiredCapabilities, trying with UiAutomator2Options");
//
//            // 🔁 Option 2: Using UiAutomator2Options
//            UiAutomator2Options options = new UiAutomator2Options();
//            options.setCapability("chromedriverExecutable", "/Users/dewanshun/Drivers/chromedriver");
//            options.setCapability("platformName", "Android");
//            options.setCapability("browserName", "Chrome");
//            options.setCapability("automationName", "UiAutomator2");
//
//            driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
//            System.out.println("✅ Started session using UiAutomator2Options");
//        }
//
//        // 🔍 Interact with browser
//        driver.get("https://www.google.com");
//        System.out.println("🌐 Title: " + driver.getTitle());
//
//        // Wait 5 seconds
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            System.out.println("⛔ Interrupted: " + e.getMessage());
//        }

//        WebElement search = driver.findElement(By.name("q"));
//        search.sendKeys("https://www.yesmadam.com");
//        search.sendKeys(Keys.ENTER);
//        System.out.println("🌐 Title: " + driver.getTitle());

//
////        WebElement search = driver.findElement(
////                AppiumBy.androidUIAutomator("new UiSelector().className(\"android.widget.EditText\").instance(0)")
////        );
////
////        search.sendKeys("https://www.yesmadam.com");
////        ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));

//        TouchAction action = new TouchAction((AndroidDriver) driver);
//        action
//                .press(PointOption.point(500, 1500))   // Start point (x, y)
//                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//                .moveTo(PointOption.point(500, 500))   // End point (x, y)
//                .release()
//                .perform();

// JavascriptExecutor js = (JavascriptExecutor) driver;
// js.executeScript("window.scrollBy(0, 1500);");
// System.out.println("✅ Scrolled down in mobile Chrome");

// JavascriptExecutor js = (JavascriptExecutor) driver;
// js.executeScript("window.scrollBy({ top: 1500, left: 0, behavior: 'smooth' });");
// Thread.sleep(2000);
// System.out.println("✅ Smooth scroll done on mobile Chrome");
//
//
//        // Quit
//        // driver.quit();
//        System.out.println("✅ Session closed");
//    }
//}

















//package com.flipkart.utils;
//
//import io.appium.java_client.android.AndroidDriver;
//import io.appium.java_client.android.options.UiAutomator2Options;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import java.net.MalformedURLException;
//import java.net.URL;
//
///**
// * Utility class to check mobile browser functionality using Appium
// * This example uses Android Chrome browser with Appium
// */
//public class MobileBrowserTests {
//    public static void main(String[] args) throws MalformedURLException {
////        DesiredCapabilities caps = new DesiredCapabilities();
////
////        caps.setCapability("platformName", "Android");
////        caps.setCapability("browserName", "Chrome");
////        caps.setCapability("automationName", "UiAutomator2");
////        caps.setCapability("chromedriverExecutable", "/Users/dewanshun/Drivers/chromedriver");
//
//        UiAutomator2Options options = new UiAutomator2Options();
//        options.setCapability("chromedriverExecutable", "/Users/dewanshun/Drivers/chromedriver");
//        options.setCapability("platformName", "Android");
//        options.setCapability("browserName", "Chrome");
//        options.setCapability("automationName", "UiAutomator2");
//
//
//        WebDriver driver = new AndroidDriver(new URL("http://127.0.0.1:4723/"), options);
//
//        // Interact with browser
//        driver.get("https://www.google.com");
//        System.out.println("Title: " + driver.getTitle());
//
//        // Close session after 5 seconds
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            System.out.println("Interrupted: " + e.getMessage());
//        }
//
//        driver.quit();
//    }
//}
