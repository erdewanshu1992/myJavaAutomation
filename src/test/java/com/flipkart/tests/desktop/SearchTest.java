package com.flipkart.tests.desktop;

import com.flipkart.screens.desktop.DesktopHomePageUpdated;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.time.Duration;

public class SearchTest {

    private WebDriver driver;
    private DesktopHomePageUpdated hP;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.get("https://www.amazon.in/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        hP = new DesktopHomePageUpdated(driver);
        System.out.println("Test setup Runing!..");
    }

    @Test(priority = 0)
    public void testProductSearch() {
        hP.searchProduct("iPhone");
        System.out.println("Test testProductSearch closed!..");
    }

    @Test(priority = 1)
    public void testHoverEffect (){
        hP.hoverThePrime();
        System.out.println("Test hoverThePrime closed!..");
    }

    @Test(dependsOnMethods = {"testHoverEffect"})
    public void clickOnJoinPrimeNowBtn(){
        hP.clickOnPrimeBtn();
        System.out.println("Test clickOnPrimeBtn closed!..");
    }


    @AfterClass
    public void tearDown() {
         driver.quit();
         System.out.println("Test closed!..");
    }
}
