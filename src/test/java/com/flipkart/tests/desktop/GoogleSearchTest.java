package com.flipkart.tests.desktop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.stream.IntStream;


public class GoogleSearchTest {
    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = new ChromeDriver(options);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // ✅ Step 1: Go to Google
        driver.get("https://www.google.com");
        driver.manage().window().maximize();
        Thread.sleep(2000);

        // ✅ Step 2: Search product
        String searchQuery = "iPhone 16 Pro Max";
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys(searchQuery);
        Thread.sleep(1500);
        searchBox.sendKeys(Keys.ENTER);

        // ✅ Step 3: Scroll
        Thread.sleep(2000);
        for (int i = 0; i < 2; i++) {
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");
            Thread.sleep(2000);
        }

//        Converted version using Stream API:
//        IntStream.range(0, 2).forEach(i -> {
//            try {
//                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });

        // ✅ Step 4: Click Buy Link
        WebElement buyLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(text(),'iPhone 16 Pro Max')]")));
        buyLink.click();

        /*
        📦 Under the hood:
        public class ChromeDriver implements WebDriver, JavascriptExecutor, TakesScreenshot {
            ...
        }
        */

        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 400)");
        Thread.sleep(2000);

        // ✅ Step 5: Get Model Name
        WebElement modelHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
                // By.xpath("//h1[contains(text(),'Buy iPhone')]")));
                // By.xpath("//span[contains(text(), \"iPhone 16 Pro Max\")]\n")));
                 By.xpath("//span[contains(normalize-space(text()), \"iPhone 16 Pro Max\")]\n")));




        String pageTitle = modelHeading.getText();
        String actualModel = pageTitle
                .replace("Buy ", "")
                .replace("new", "")
                .replace("Your ", "")
                .replaceAll("[\\u00A0\\.]", "")  // removes NBSP and dot
                .trim();

        // System.out.println("🧾 Product on Apple page: " + actualModel);
        // System.out.println("▶️ actualModel (raw): '" + actualModel + "'");
        // System.out.println("▶️ Length actualModel: " + actualModel.length());
        // System.out.println("▶️ Length searchQuery: " + searchQuery.length());

        System.out.println("🧾 Product on Apple page: " + actualModel);
        System.out.println("▶️ Lengths: actual=" + actualModel.length() + ", search=" + searchQuery.length());



        // ✅ Step 6: Match Model
        // if (searchQuery.toLowerCase().contains(actualModel.toLowerCase())) {
        // if (searchQuery.equalsIgnoreCase(actualModel)) {

        if (searchQuery.equals(actualModel)) {
            System.out.println("✅ Model Match: Search vs Apple Page");
        } else {
            System.out.println("❌ Model Mismatch!");
        }

        // ✅ Step 7: Get Price
        WebElement cleanPrice = driver.findElement(By.xpath("(//span[@class=\"nowrap\"])[2]"));
        String fullText = cleanPrice.getText();
        String priceOnly = fullText.replaceAll("[^₹0-9.,]", "");
        System.out.println("💸 Clean Price: " + priceOnly);

        // ✅ One-liner: Screenshot as File
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        // Save the screenshot
        FileUtils.copyFile(screenshot, new File("screenshot.png"));

        // ✅ Step 8: Close
        Thread.sleep(4000);
        driver.quit();
    }
}








//package com.flipkart.tests.desktop;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class GoogleSearchTest {
//    public static void main(String[] args) throws InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("useAutomationExtension", false);
//        options.addArguments("--disable-blink-features=AutomationControlled");
//
//        WebDriver driver = new ChromeDriver(options);
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//
//        // ✅ Step 1: Go to Google
//        driver.get("https://www.google.com");
//        Thread.sleep(2000); // wait like a human
//
//        // ✅ Step 2: Search for product
//        String searchQuery = "iPhone 16 Pro Max";
//        WebElement searchBox = driver.findElement(By.name("q"));
//        searchBox.sendKeys(searchQuery);
//        Thread.sleep(1500);
//        searchBox.sendKeys(Keys.ENTER);
//
//        // ✅ Step 3: Scroll to simulate real user
//        Thread.sleep(2000);
//        for (int i = 0; i < 2; i++) {
//            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 500)");
//            Thread.sleep(2000);
//        }
//
//        // ✅ Step 4: Click on a Buy link from Apple
//        WebElement buyLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(),'iPhone 16 Pro Max')]")));
//        buyLink.click();
//
//        // ✅ Step 5: Extract title of the model from Apple page
//        WebElement modelHeading = wait.until(ExpectedConditions.visibilityOfElementLocated(
//                By.xpath("//h1[contains(text(),'Buy iPhone')]")
//        ));
//        String pageTitle = modelHeading.getText(); // e.g. "Buy iPhone 16 Pro"
//        String actualModel = pageTitle.replace("Buy ", "").trim();
//
//        System.out.println("🧾 Product on Apple page: " + actualModel);
//
//        // ✅ Step 6: Compare searched term vs an actual model
//        if (searchQuery.toLowerCase().contains(actualModel.toLowerCase())) {
//            System.out.println("✅ Model Match: Search vs Apple Page");
//        } else {
//            System.out.println("❌ Model Mismatch!");
//        }
//
//        // ✅ Step 7: Get Price
//        WebElement cleanPrice = driver.findElement(By.xpath("(//span[@class=\"nowrap\"])[2]"));
//
//        String fullText = cleanPrice.getText();
//        String priceOnly = fullText.replaceAll("[^₹0-9.,]", "");
//        System.out.println("💸 Clean Price: " + priceOnly);
//
//        WebElement productName = driver.findElement(By.xpath("//h1"));
//        System.out.println("Model Found: " + productName);
//
//
//        String pageProductName = driver.findElement(By.xpath("//h1")).getText();
//        String searchText = "iPhone 16 Pro Max";
//
//        if (pageProductName.toLowerCase().contains(searchText.toLowerCase())) {
//            System.out.println("✅ Model Match: Search vs Apple Page");
//        } else {
//            System.out.println("❌ Model Mismatch! Found: " + pageProductName);
//        }
//
//
//
//
//        // ✅ Final Step: Close browser after short delay
//        Thread.sleep(4000);
//        driver.quit();
//    }
//}








//package com.flipkart.tests.desktop;
//
//import org.openqa.selenium.By;
//import org.openqa.selenium.Keys;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.time.Duration;
//
//public class GoogleSearchTest {
//    public static void main(String[] args) throws InterruptedException {
//        ChromeOptions options = new ChromeOptions();
//
//        // 🚫 Remove automation flags
//        options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
//        options.setExperimentalOption("useAutomationExtension", false);
//        options.addArguments("--disable-blink-features=AutomationControlled");
//
//        WebDriver driver = new ChromeDriver(options);
//
//        // ✅ Navigate to Google
//        driver.get("https://www.google.com");
//
//        // ✅ Wait 3 seconds to appear human
//        Thread.sleep(3000);
//
//        WebElement searchBox = driver.findElement(By.name("q"));
//        searchBox.sendKeys("iPhone 16 Pro Max");
//        Thread.sleep(2000); // simulate human pause
//        searchBox.sendKeys(Keys.ENTER);
//
//        // ✅ Wait and scroll (simulate scroll behavior)
//        Thread.sleep(2000);
//        for (int i = 1; i <= 2; i++) {
//            ((org.openqa.selenium.JavascriptExecutor) driver)
//                    .executeScript("window.scrollBy(0, 500)");
//            Thread.sleep(2000);
//        }
//
//        WebElement buyLink = new WebDriverWait(driver, Duration.ofSeconds(10))
//                .until(ExpectedConditions.elementToBeClickable(
//                        By.xpath("//a[contains(text(),'Buy iPhone 16 Pro Max')]")
//                ));
//        buyLink.click();
//
//
//
//
//
//
//
//
//        // ✅ Click 1st link if available
////        WebElement firstResult = driver.findElement(By.cssSelector("h3"));
////        String url = firstResult.findElement(By.xpath("./ancestor::a")).getAttribute("href");
////        System.out.println("🌐 Found URL: " + url);
////
////        // ✅ Open in new tab
////        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.open(arguments[0])", url);
////        Thread.sleep(5000);
//
//
//
//        //driver.quit();
//    }
//}

