package com.flipkart.tests.desktop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.ArrayList;
import java.util.List;

public class BrowserContextTest {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
//        WebDriverManager.firefoxdriver().setup();
//        WebDriver driver = new FirefoxDriver();

        driver.manage().window().maximize();
        driver.get("https://www.google.com");

        // ✅ Accept cookies if present (EU region)
//        try {
//            WebElement agreeBtn = driver.findElement(By.xpath("//div[contains(text(),'I agree')]"));
//            agreeBtn.click();
//        } catch (Exception e) {
//            // Ignore if not found
//        }
//
//        // ✅ Search
//        WebElement searchBox = driver.findElement(By.name("q"));
//        searchBox.sendKeys("iPhone 16 Pro Max");
//        searchBox.sendKeys(Keys.ENTER);
//
//        Thread.sleep(3000); // wait for results

        // driver.get("https://duckduckgo.com");
        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("iPhone 16 Pro Max");
        searchBox.sendKeys(Keys.ENTER);

        // ✅ Scroll
        JavascriptExecutor js = (JavascriptExecutor) driver;
        for (int i = 1; i <= 3; i++) {
            js.executeScript("window.scrollBy(0, 1500)");
            Thread.sleep(2000);
        }

        // ✅ Get First Result (skip ads/maps)
        List<WebElement> results = driver.findElements(By.cssSelector("div.yuRUbf > a")); // Normal search result links

        if (!results.isEmpty()) {
            String firstURL = results.get(0).getAttribute("href");
            System.out.println("🔗 First result URL: " + firstURL);

            // ✅ Open in new tab
            ((JavascriptExecutor) driver).executeScript("window.open()");
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

            driver.switchTo().window(tabs.get(1)); // Go to new tab
            driver.get(firstURL);

            Thread.sleep(3000);
            System.out.println("🧾 Title of new tab: " + driver.getTitle());
        } else {
            System.out.println("❌ No valid results found.");
        }

        driver.quit();
    }
}















/*
package com.flipkart.tests.desktop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;

public class BrowserContextTest {
    public static void main(String[] args) throws InterruptedException {


        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.google.com");
        System.out.println("🌐 Title: " + driver.getTitle());

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));

        driver.get("https://www.yesmadam.com");
        System.out.println("🌐 Title: " + driver.getTitle());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        System.out.println("🌐 Final Title: " + driver.getTitle());


        // open new tab
        ((JavascriptExecutor) driver).executeScript("window.open()");

        // Wait for the tab to open
        Thread.sleep(3000);

        // Now collect the handles
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());

        // Switch to new tab
        driver.switchTo().window(tabs.get(1));
        driver.get("https://www.flipkart.com");

        System.out.println("✅ Opened Flipkart in second tab");

        // Switch back to original tab
        driver.switchTo().window(tabs.getFirst());
        System.out.println("🔙 Switched back to YesMadam tab");



    }

}

 */
