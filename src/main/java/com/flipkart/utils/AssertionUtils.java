package com.flipkart.utils;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

/**
 * Utility class for handling assertions in Flipkart tests.
 * This class provides methods for both hard and soft assertions.
 * It uses TestNG's Assert for hard assertions and SoftAssert for soft assertions.
 *
 * @author Flipkart Automation Team (Dewanshu sr.qa)
 * @version 1.0
 * @since 2023-10-01
 * @description This class includes methods to perform hard assertions that will immediately fail the test if the condition is not met,
 * and soft assertions that will collect assertion failures and allow the test to continue.
 * * @see org.testng.Assert
 * * @see org.testng.asserts.SoftAssert
 * * @package com.flipkart.utils
 * * @license This code is licensed under the Flipkart Open Source License.
 * * This class is part of the Flipkart automation framework and is intended to be used for enhancing test reliability and reporting.
 * * This class provides a centralized way to manage assertions in tests, allowing for both strict failure conditions and flexible reporting of multiple assertion failures.
 * * @Epic("Flipkart Automation Framework")
 * * @Feature("Assertion Utilities")
 * * This class is designed to be used in both nativeMobile and web automation tests, providing a consistent way to handle assertions across different test scenarios.
 * * @Listeners({com.flipkart.listeners.RetryListener.class})
 * * This class is intended to be used in conjunction with TestNG test frameworks.
 * *
 */

public class AssertionUtils {

    // Single soft assert instance per test (can also be instantiated per method if preferred)
    private static ThreadLocal<SoftAssert> softAssert = ThreadLocal.withInitial(SoftAssert::new);

    /*** HARD ASSERTIONS ***/

    public static void hardAssertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }

    public static void hardAssertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }

    public static void hardAssertEquals(Object actual, Object expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }

    public static void hardAssertNotEquals(Object actual, Object expected, String message) {
        Assert.assertNotEquals(actual, expected, message);
    }

    /*** SOFT ASSERTIONS ***/

    public static void softAssertTrue(boolean condition, String message) {
        softAssert.get().assertTrue(condition, message);
    }

    public static void softAssertFalse(boolean condition, String message) {
        softAssert.get().assertFalse(condition, message);
    }

    public static void softAssertEquals(Object actual, Object expected, String message) {
        softAssert.get().assertEquals(actual, expected, message);
    }

    public static void softAssertNotEquals(Object actual, Object expected, String message) {
        softAssert.get().assertNotEquals(actual, expected, message);
    }

    /**
     * Call this at the end of your test method to assert all soft asserts.
     * It will throw collected assertion errors if any soft assert failed.
     */
    public static void assertAll() {
        softAssert.get().assertAll();
        // Reset for next test
        softAssert.remove();
    }
}
