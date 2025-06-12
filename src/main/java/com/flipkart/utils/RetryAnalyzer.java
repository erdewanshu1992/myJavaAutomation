package com.flipkart.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;

/**
 * @author Flipkart Automation Team (Dewanshu sr.qa)
 * @version 1.0
 * @since 2023-10-01
 * @description This class provides a retry mechanism for flaky tests,
 * capturing screenshots and device information
 * during retries to aid in debugging.
 * @see IRetryAnalyzer
 * @see ITestResult
 * @see Allure
 * @see WebDriver
 * @see RemoteWebDriver
 * @package com.flipkart.utils
 * @license This code is licensed under the Flipkart Open Source License.
 *
 * * @Epic("Flipkart Mobile App Testing")
 * * @Feature("Retry Mechanism for Flaky Tests")
 * * This class is designed to be run on nativeMobile devices using Appium and TestNG.
 * * @Feature("Test Reliability and Reporting")
 * * This class includes annotations for Allure reporting to enhance test documentation and reporting.
 * * This class is designed to be used in conjunction with the Flipkart nativeMobile automation suite.
 * * * This class is intended to be run as part of the CI/CD pipeline to ensure test reliability.
 * * * This class is designed to work with TestNG and Allure reporting,
 * * providing detailed insights into test failures and retries.
 * * * * This implementation is designed to work with TestNG and Allure reporting,
 * * providing detailed insights into test failures and retries.
 * * * This class handles retries, logs steps, and captures screenshots on failure.
 * * * * It also retrieves device information for remote WebDriver instances.
 * * * This class uses reflection to access the WebDriver instance from the test class,
 * * allowing it to capture screenshots and device details during retries.
 * * * This class is useful for debugging flaky tests in nativeMobile web applications.
 */

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryLimit = 2;  // Customize as needed

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryLimit) {
            retryCount++;

            Allure.label("flaky", "true");
            Allure.step("🔁 Retry #" + retryCount + " for test: " + result.getName(), Status.BROKEN);

            // Rename test with retry info
            Allure.getLifecycle().updateTestCase(testCase ->
                    testCase.setName(result.getName() + " [Retry #" + retryCount + "]")
            );

            // Get driver from test instance via reflection
            WebDriver driver = getDriverFromResult(result);

            if (driver != null) {
                // Add screenshot attachment
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("🖼️ Retry Screenshot", "image/png", new ByteArrayInputStream(screenshot), ".png");

                // Add device info attachment
                if (driver instanceof RemoteWebDriver) {
                    String deviceInfo = DeviceUtils.getDeviceDetails((RemoteWebDriver) driver);
                    Allure.addAttachment("📱 Device Info", "text/plain", deviceInfo);
                }
            }

            return true;
        }
        return false;
    }

    private WebDriver getDriverFromResult(ITestResult result) {
        try {
            // Assumes your test class or superclass has a 'driver' field
            Field driverField = null;

            Class<?> clazz = result.getInstance().getClass();

            // Search in class and superclasses for 'driver' field
            while (clazz != null) {
                try {
                    driverField = clazz.getDeclaredField("driver");
                    if (driverField != null) break;
                } catch (NoSuchFieldException e) {
                    clazz = clazz.getSuperclass();
                }
            }

            if (driverField != null) {
                driverField.setAccessible(true);
                return (WebDriver) driverField.get(result.getInstance());
            } else {
                System.err.println("❌ 'driver' field not found in test class hierarchy.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("❌ Could not retrieve WebDriver from test instance: " + e.getMessage());
            return null;
        }
    }
}




















/*
package com.flipkart.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryLimit = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryLimit) {
            retryCount++;

            // ✅ Log retry step in Allure
            Allure.step("🔁 Retry #" + retryCount + " for test: " + result.getName(), Status.BROKEN);

            System.out.println("🔁 Retrying test: " + result.getName() + " | Attempt: " + retryCount);
            return true;
        }
        return false;
    }
}
*/













/*
package com.flipkart.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int maxRetryLimit = 2;  // Retry failed test 2 times

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < maxRetryLimit) {
            retryCount++;
            System.out.println("🔁 Retrying test: " + result.getName() + " | Attempt: " + retryCount);
            return true;
        }
        return false;
    }
}

 */
