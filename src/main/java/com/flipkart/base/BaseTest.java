package com.flipkart.base;

import com.flipkart.config.ConfigReader;
import com.flipkart.driver.DriverManager;
import com.flipkart.listeners.TestListener;
import com.flipkart.utils.AllureUtils;
import com.flipkart.utils.PlatformManager;
import com.flipkart.utils.ScreenshotUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Attachment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Unified base test class that provides common setup and teardown functionality
 * for all test classes across all platforms (desktop, responsive, native mobile)
 */
@Listeners(TestListener.class)
public abstract class BaseTest {
    
    protected static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    protected DriverManager driverManager;
    protected ConfigReader config;
    private boolean isFirstTestInClass = true;

    @BeforeSuite
    public void beforeSuite() {
        config = ConfigReader.getInstance();
        AllureUtils.setEnvironmentInfo();

        // Log current platform configuration
        logger.info("🎯 Platform configured: {}", config.getPlatform());
        logger.info("🎯 Detected platform type: {}", PlatformManager.getPlatform());

        // Set environment info based on platform
        switch (PlatformManager.getPlatform()) {
            case DESKTOP:
            case RESPONSIVE:
                AllureUtils.setWebEnvironmentInfo();
                break;
            case NATIVEMOBILE:
                AllureUtils.setMobileEnvironmentInfo(
                    config.getDeviceName(),
                    config.getPlatformName(),
                    config.getPlatformVersion()
                );
                break;
        }
        
        logger.info("✅ Test suite initialized for platform: {}", PlatformManager.getPlatform());
    }
    
    @BeforeMethod
    public void beforeMethod(Method method) {
        String testName = method.getName();
        logger.info("🚀 Starting test: {} on platform: {}", testName, PlatformManager.getPlatform());
        
        // Only initialize driver once per class unless it's been quit
        if (driverManager == null || isFirstTestInClass) {
            driverManager = new DriverManager();
            setupDriver();
            isFirstTestInClass = false;
            logger.info("🔄 Driver initialized for first test in class or after being quit");
        } else {
            logger.info("♻️ Reusing existing driver instance");
        }
    }
    
    @AfterMethod
    public void afterMethod(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        
        if (!result.isSuccess()) {
            logger.error("❌ Test failed: {}", testName);
            takeScreenshotOnFailure(testName);
        } else {
            logger.info("✅ Test passed: {}", testName);
        }
        
        // Don't quit the driver between tests in the same class
        // This prevents app relaunch between tests
        logger.info("⏭️ Keeping driver active for next test in class");
        logger.info("🏁 Test completed: {}", testName);
    }
    
    @AfterClass
    public void afterClass() {
        // Ensure driver is quit after all tests in the class
        tearDown();
        logger.info("🏁 All tests in class completed, driver quit");
    }
    
    @AfterSuite
    public void afterSuite() {
        logger.info("📊 Generating test reports for platform: {}", PlatformManager.getPlatform());
        generateReports();
    }
    
    /**
     * Setup driver based on platform configuration
     * This method can be overridden by child classes if needed
     */
    protected void setupDriver() {
        try {
            logger.info("🔧 Initializing driver for platform: {}", PlatformManager.getPlatform());
            driverManager.initializeDriver();
            logger.info("✅ Driver setup completed successfully");
        } catch (Exception e) {
            logger.error("❌ Failed to initialize driver: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to initialize driver for platform: " + PlatformManager.getPlatform(), e);
        }
    }
    
    /**
     * Clean up resources
     */
    protected void tearDown() {
        if (driverManager != null) {
            try {
                driverManager.quitDriver();
                driverManager = null;
                isFirstTestInClass = true;
                logger.info("🧹 Driver cleanup completed");
            } catch (Exception e) {
                logger.error("⚠️ Failed to quit driver: {}", e.getMessage());
            }
        }
    }
    
    /**
     * Take screenshot on test failure
     */
    @Attachment(value = "Screenshot", type = "image/png")
    private byte[] takeScreenshotOnFailure(String testName) {
        try {
            if (driverManager != null && driverManager.getDriver() != null) {
                byte[] screenshot = ScreenshotUtils.takeScreenshot(driverManager.getDriver(), testName);
                logger.info("📸 Screenshot captured for failed test: {}", testName);
                return screenshot;
            }
        } catch (Exception e) {
            logger.error("📸 Failed to take screenshot: {}", e.getMessage());
        }
        return new byte[0];
    }
    
    /**
     * Generate test reports
     */
    private void generateReports() {
        logger.info("📈 Test reports generation completed");
    }
    
    /**
     * Handle TestNG parameters
     */
    @Parameters({"platform"})
    @BeforeTest(alwaysRun = true)
    public void handleTestNGParameters(@Optional String platform) {
        if (platform != null && !platform.trim().isEmpty()) {
            System.setProperty("platform", platform);
            logger.info("🎯 Platform set from TestNG parameter: {}", platform);
        }
    }

    /**
     * Data provider for JSON test data
     */
    @DataProvider(name = "jsonDataProvider")
    public Object[][] jsonDataProvider(Method method) {
        String fileName = getTestDataFileName(method);
        List<Map<String, String>> testData = loadJsonData(fileName);
        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        return data;
    }

    /**
     * Data provider for CSV test data
     */
    @DataProvider(name = "csvDataProvider")
    public Object[][] csvDataProvider(Method method) {
        String fileName = getTestDataFileName(method);
        List<Map<String, String>> testData = loadCsvData(fileName);
        Object[][] data = new Object[testData.size()][1];
        for (int i = 0; i < testData.size(); i++) {
            data[i][0] = testData.get(i);
        }
        return data;
    }

    private String getTestDataFileName(Method method) {
        // Extract filename from method name or use default
        String methodName = method.getName();
        if (methodName.contains("Login")) {
            return "loginData.json";
        } else if (methodName.contains("Search")) {
            return "search.csv";
        }
        return "testdata.json"; // default
    }

    private List<Map<String, String>> loadJsonData(String fileName) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String filePath = config.getTestDataPath() + fileName;
            JsonNode rootNode = mapper.readTree(new java.io.File(filePath));

            if (rootNode.isArray()) {
                Iterator<JsonNode> elements = rootNode.elements();
                while (elements.hasNext()) {
                    JsonNode node = elements.next();
                    Map<String, String> map = mapper.convertValue(node, Map.class);
                    data.add(map);
                }
            }
        } catch (IOException e) {
            logger.error("Failed to load JSON test data: {}", fileName, e);
        }
        return data;
    }

    private List<Map<String, String>> loadCsvData(String fileName) {
        List<Map<String, String>> data = new ArrayList<>();
        try {
            String filePath = config.getTestDataPath() + fileName;
            java.io.BufferedReader reader = new java.io.BufferedReader(new FileReader(filePath));
            String line = reader.readLine(); // Read header
            if (line != null) {
                String[] headers = line.split(",");
                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    Map<String, String> map = new java.util.HashMap<>();
                    for (int i = 0; i < headers.length && i < values.length; i++) {
                        map.put(headers[i].trim(), values[i].trim());
                    }
                    data.add(map);
                }
            }
            reader.close();
        } catch (IOException e) {
            logger.error("Failed to load CSV test data: {}", fileName, e);
        }
        return data;
    }
}
