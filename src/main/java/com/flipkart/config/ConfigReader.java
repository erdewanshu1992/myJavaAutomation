package com.flipkart.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Configuration reader class to read properties from config files
 * Implements Singleton pattern with system property override support
 */
public class ConfigReader {
    
    private static final Logger logger = LoggerFactory.getLogger(ConfigReader.class);
    private static ConfigReader instance;
    private Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    
    private ConfigReader() {
        loadProperties();
    }
    
    public static synchronized ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }
    
    private void loadProperties() {
        properties = new Properties();
        try (FileInputStream fis = new FileInputStream(CONFIG_FILE_PATH)) {
            properties.load(fis);
            logger.info("✅ Loaded configuration from: {}", CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("❌ Failed to load configuration file: {}", CONFIG_FILE_PATH, e);
            throw new RuntimeException("Failed to load configuration file: " + CONFIG_FILE_PATH, e);
        }
    }
    
    /**
     * Get property with system property override and environment variable support
     */
    private String getProperty(String key, String defaultValue) {
        // First check system properties (from -D arguments or TestNG parameters)
        String systemValue = System.getProperty(key);
        if (systemValue != null && !systemValue.trim().isEmpty()) {
            logger.info("🔧 Using system property for {}: {}", key, systemValue);
            return systemValue;
        }

        // Then check config file
        String configValue = properties.getProperty(key, defaultValue);

        // Resolve environment variables if present (format: ${VAR_NAME})
        if (configValue != null && configValue.startsWith("${") && configValue.endsWith("}")) {
            String envVarName = configValue.substring(2, configValue.length() - 1);
            String envValue = System.getenv(envVarName);
            if (envValue != null && !envValue.trim().isEmpty()) {
                logger.info("🔧 Using environment variable for {}: {}", key, "[RESOLVED]");
                return envValue;
            } else {
                logger.warn("⚠️ Environment variable {} not found, using default value", envVarName);
                return defaultValue;
            }
        }

        logger.info("🔧 Using config file property for {}: {}", key, configValue);
        return configValue;
    }
    
    // Platform Configuration - FIXED with system property override
    public String getPlatform() {
        String platform = getProperty("platform", "desktop");
        logger.info("📱 Platform from config: {}", platform);
        return platform;
    }
    
    // Web Driver Properties
    public String getBrowser() {
        return getProperty("browser", "chrome");
    }
    
    public boolean isHeadless() {
        return Boolean.parseBoolean(getProperty("headless", "false"));
    }
    
    public String getBaseUrl() {
        return getProperty("base.url", "https://www.flipkart.com");
    }
    
    // Mobile Driver Properties
    public String getDeviceName() {
        return getProperty("device.name", "Android Emulator");
    }
    
    public String getPlatformName() {
        return getProperty("platform.name", "Android");
    }
    
    public String getPlatformVersion() {
        return getProperty("platform.version", "11.0");
    }
    
    public String getAppPackage() {
        return getProperty("app.package", "com.flipkart.android");
    }
    
    public String getAppActivity() {
        return getProperty("app.activity", "com.flipkart.android.SplashActivity");
    }
    
    public String getAppiumServerUrl() {
        return getProperty("appium.server.url", "http://localhost:4723/");
    }
    
    // Common Properties
    public int getImplicitWait() {
        return Integer.parseInt(getProperty("implicit.wait", "10"));
    }
    
    public int getExplicitWait() {
        return Integer.parseInt(getProperty("explicit.wait", "30"));
    }
    
    // Test Data Properties
    public String getTestDataPath() {
        return getProperty("test.data.path", "src/test/resources/testdata/");
    }
    
    // Report Properties
    public String getReportPath() {
        return getProperty("report.path", "target/reports/");
    }
    
    public String getScreenshotPath() {
        return getProperty("screenshot.path", "target/screenshots/");
    }
    
    // Email Properties
    public String getEmailHost() {
        return getProperty("email.host", "");
    }
    
    public String getEmailPort() {
        return getProperty("email.port", "587");
    }
    
    public String getEmailUsername() {
        return getProperty("email.username", "");
    }
    
    public String getEmailPassword() {
        return getProperty("email.password", "");
    }
    
    public String getEmailRecipients() {
        return getProperty("email.recipients", "");
    }
}
