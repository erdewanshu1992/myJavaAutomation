package com.flipkart.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BrowserCheck {
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        // Create a Chrome browser instance
        WebDriver driver = new ChromeDriver();
        // Open Google
        driver.get("https://www.google.com");
        // Optional: maximize window
        driver.manage().window().maximize();
        // Optional: Print the page title
        System.out.println("Page Title: " + driver.getTitle());
        // Optional: Wait for 5 seconds before closing (for demo purpose)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Close the browser
        driver.quit();
    }
}
