package com.flipkart.locators;

import org.openqa.selenium.By;

public class DesktopHomeLocators {

    public static final By searchBox = By.xpath("//input[@type=\"text\"]");
    public static final By menuPrime = By.cssSelector("#nav-link-amazonprime");
    public static final By joinPrimeBtn = By.xpath("//a[contains(text(), 'Join Prime Now')]");
}
