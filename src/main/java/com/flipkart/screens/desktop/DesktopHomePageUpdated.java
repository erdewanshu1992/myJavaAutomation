package com.flipkart.screens.desktop;

import com.flipkart.locators.DesktopHomeLocators;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DesktopHomePageUpdated {
    private final WebDriver driver;

    public DesktopHomePageUpdated(WebDriver driver) {
        this.driver = driver;
    }

    public void searchProduct(String product) {
        WebElement element = driver.findElement(DesktopHomeLocators.searchBox);
        element.sendKeys(product);
        element.sendKeys(Keys.ENTER);
    }

    public void hoverThePrime (){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement headerPrime = wait.until(ExpectedConditions.elementToBeClickable(DesktopHomeLocators.menuPrime));
        // WebElement element = driver.findElement(DesktopHomeLocators.menuPrime);

        new Actions(driver)
                .moveToElement(headerPrime)
                // .perform();
                .pause(Duration.ofSeconds(1)).perform();
    }

    public void clickOnPrimeBtn (){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement joinPrime = wait.until(ExpectedConditions.elementToBeClickable(DesktopHomeLocators.joinPrimeBtn));
        joinPrime.click();

    }
}
