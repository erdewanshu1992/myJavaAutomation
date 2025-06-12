package com.flipkart.listeners;

import com.flipkart.utils.EmailUtils;
import com.flipkart.utils.ScreenshotUtils;
import io.qameta.allure.Attachment;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.ISuite;
import org.testng.ISuiteListener;

import java.lang.reflect.Field;

/**
 * TestNG listener for custom test execution handling
 * NO ALLURE CALLS IN LISTENERS - they cause "no test case running" errors
 */
public class TestListener implements ITestListener, ISuiteListener {
    
    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;
    private int skippedTests = 0;
    
    @Override
    public void onStart(ITestContext context) {
        System.out.println("Test Suite Started: " + context.getName());
        // NO ALLURE CALLS HERE - causes "no test case running" error
    }
    
    @Override
    public void onFinish(ITestContext context) {
        System.out.println("Test Suite Finished: " + context.getName());
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Skipped: " + skippedTests);
        
        // Send email summary
        try {
            EmailUtils.sendTestSummary(totalTests, passedTests, failedTests, skippedTests);
        } catch (Exception e) {
            System.err.println("Failed to send email summary: " + e.getMessage());
        }
    }
    
    @Override
    public void onTestStart(ITestResult result) {
        totalTests++;
        String testName = result.getMethod().getMethodName();
        String className = result.getTestClass().getName();
        System.out.println("Starting Test: " + className + "." + testName);
        // NO ALLURE CALLS HERE - test case is not fully initialized yet
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        passedTests++;
        String testName = result.getMethod().getMethodName();
        System.out.println("Test PASSED: " + testName);
        // NO ALLURE CALLS HERE - test case might be finishing
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
        failedTests++;
        String testName = result.getMethod().getMethodName();
        System.out.println("Test FAILED: " + testName);
        System.out.println("Failure Reason: " + result.getThrowable().getMessage());
        
        // Take screenshot on failure but don't use Allure here
        WebDriver driver = getDriverFromResult(result);
        if (driver != null) {
            try {
                ScreenshotUtils.takeScreenshot(driver, testName);
                System.out.println("Screenshot captured for failed test: " + testName);
            } catch (Exception e) {
                System.err.println("Failed to capture screenshot: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void onTestSkipped(ITestResult result) {
        skippedTests++;
        String testName = result.getMethod().getMethodName();
        System.out.println("Test SKIPPED: " + testName);
        // NO ALLURE CALLS HERE
    }
    
    @Override
    public void onStart(ISuite suite) {
        System.out.println("Suite Started: " + suite.getName());
    }
    
    @Override
    public void onFinish(ISuite suite) {
        System.out.println("Suite Finished: " + suite.getName());
    }
    
    private WebDriver getDriverFromResult(ITestResult result) {
        try {
            Object instance = result.getInstance();
            Field driverManagerField = findField(instance.getClass(), "driverManager");
            
            if (driverManagerField != null) {
                driverManagerField.setAccessible(true);
                Object driverManager = driverManagerField.get(instance);
                
                if (driverManager != null) {
                    java.lang.reflect.Method getDriverMethod = driverManager.getClass().getMethod("getDriver");
                    return (WebDriver) getDriverMethod.invoke(driverManager);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to get WebDriver from test instance: " + e.getMessage());
        }
        return null;
    }
    
    private Field findField(Class<?> clazz, String fieldName) {
        Class<?> current = clazz;
        while (current != null) {
            try {
                return current.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                current = current.getSuperclass();
            }
        }
        return null;
    }
}
