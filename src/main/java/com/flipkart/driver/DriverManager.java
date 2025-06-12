package com.flipkart.driver;

import com.flipkart.config.ConfigReader;
import com.flipkart.utils.PlatformManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Unified Driver manager class to handle WebDriver and AppiumDriver instances
 * Supports desktop, responsive, and native mobile platforms
 */
public class DriverManager {
    
    private static final Logger logger = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static DriverManager instance;
    private final ConfigReader config;
    
    public DriverManager() {
        this.config = ConfigReader.getInstance();
    }
    
    public static DriverManager getInstance() {
        if (instance == null) {
            instance = new DriverManager();
        }
        return instance;
    }

    /**
     * Initialize driver based on platform configuration
     */
    public WebDriver initializeDriver() {
        WebDriver driver;
        
        switch (PlatformManager.getPlatform()) {
            case DESKTOP:
                driver = initializeWebDriver();
                break;
            case RESPONSIVE:
                driver = initializeResponsiveDriver();
                break;
            case NATIVEMOBILE:
                try {
                    driver = initializeMobileDriver();
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Failed to initialize mobile driver", e);
                }
                break;
            default:
                throw new IllegalArgumentException("Unsupported platform: " + PlatformManager.getPlatform());
        }
        
        driverThreadLocal.set(driver);
        logger.info("✅ Driver initialized successfully for platform: {}", PlatformManager.getPlatform());
        return driver;
    }
    
    /**
     * Initialize WebDriver for desktop web testing
     */
    public WebDriver initializeWebDriver() {
        String browserName = config.getBrowser().toLowerCase();
        WebDriver driver;
        
        logger.info("🖥️ Initializing DESKTOP web driver with browser: {}", browserName);
        
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--disable-web-security");
                chromeOptions.addArguments("--allow-running-insecure-content");
                
                // Ensure NO mobile emulation for desktop
                logger.info("🖥️ Desktop mode - NO mobile emulation");
                
                if (config.isHeadless()) {
                    chromeOptions.addArguments("--headless");
                }
                driver = new ChromeDriver(chromeOptions);
                break;
            
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (config.isHeadless()) {
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;
            
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        
        // Navigate to base URL for web testing
        if (config.getBaseUrl() != null && !config.getBaseUrl().isEmpty()) {
            driver.get(config.getBaseUrl());
        }
        
        logger.info("✅ Desktop web driver initialized: {}", browserName);
        return driver;
    }
    
    /**
     * Initialize WebDriver with mobile emulation for responsive testing
     */
    public WebDriver initializeResponsiveDriver() {
        WebDriverManager.chromedriver().setup();
        
        // Configure mobile device emulation
        Map<String, Object> deviceMetrics = new HashMap<>();
        deviceMetrics.put("width", 375);    // iPhone X width
        deviceMetrics.put("height", 812);   // iPhone X height
        deviceMetrics.put("pixelRatio", 3.0);

        Map<String, Object> mobileEmulation = new HashMap<>();
        mobileEmulation.put("deviceMetrics", deviceMetrics);
        mobileEmulation.put("userAgent", "Mozilla/5.0 (iPhone; CPU iPhone OS 13_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0 Mobile/15E148 Safari/604.1");

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--disable-web-security");
        chromeOptions.addArguments("--allow-running-insecure-content");
        
        if (config.isHeadless()) {
            chromeOptions.addArguments("--headless");
        }

        WebDriver driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        
        // Navigate to base URL for responsive testing
        if (config.getBaseUrl() != null && !config.getBaseUrl().isEmpty()) {
            driver.get(config.getBaseUrl());
        }
        
        logger.info("✅ Responsive web driver initialized with mobile emulation");
        return driver;
    }
    
    /**
     * Initialize AppiumDriver for native mobile testing
     */
    public AppiumDriver initializeMobileDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();
        options.setDeviceName(config.getDeviceName());
        options.setPlatformName(config.getPlatformName());
        options.setPlatformVersion(config.getPlatformVersion());
        options.setAppPackage(config.getAppPackage());
        options.setAppActivity(config.getAppActivity());
        options.setAutomationName("UiAutomator2");
        options.setNewCommandTimeout(Duration.ofSeconds(300));

        // Additional capabilities for Flipkart app
        options.setCapability("autoGrantPermissions", true);
        // options.setCapability("noReset", true);
        // Set noReset to true to prevent app from being reset between sessions
        options.setNoReset(false);
        logger.info("🔧 Setting noReset=true to maintain app state between sessions");

        options.setCapability("fullReset", false);

        AppiumDriver driver = new AndroidDriver(
            new URL(config.getAppiumServerUrl()), options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
        logger.info("✅ Native mobile driver initialized for device: {}", config.getDeviceName());
        
        return driver;
    }

    /**
     * Get current driver instance
     */
    public WebDriver getDriver() {
        return driverThreadLocal.get();
    }
    
    /**
     * Quit driver and remove from ThreadLocal
     */
    public void quitDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver != null) {
            driver.quit();
            driverThreadLocal.remove();
            logger.info("🛑 Driver session ended for platform: {}", PlatformManager.getPlatform());
        }
    }
}
