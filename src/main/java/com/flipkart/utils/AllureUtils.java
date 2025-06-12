package com.flipkart.utils;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import java.io.ByteArrayInputStream;
import java.util.Properties;

/**
 * Utility class for Allure reporting
 * Only use these methods INSIDE test methods, not in listeners or setup/teardown
 */
public class AllureUtils {

    /**
     * Check if we're currently in a test execution context
     */
    private static boolean isTestRunning() {
        try {
            // Try to get current test case - if this fails, no test is running
            Allure.getLifecycle().getCurrentTestCase();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static void addTestInfo(String testName, String className) {
        if (!isTestRunning()) return;
        try {
            Allure.getLifecycle().updateTestCase(testCase -> {
                testCase.setName(testName);
                testCase.setFullName(className + "." + testName);
            });
        } catch (Exception e) {
            System.err.println("Failed to update test info: " + e.getMessage());
        }
    }

    public static void addAttachment(String name, String content) {
        if (!isTestRunning()) return;
        try {
            Allure.addAttachment(name, content);
        } catch (Exception e) {
            System.err.println("Failed to add attachment: " + e.getMessage());
        }
    }

    public static void addAttachment(String name, byte[] content, String type) {
        if (!isTestRunning()) return;
        try {
            Allure.addAttachment(name, type, new ByteArrayInputStream(content), "png");
        } catch (Exception e) {
            System.err.println("Failed to add attachment: " + e.getMessage());
        }
    }

    public static void addStep(String stepName) {
        if (!isTestRunning()) return;
        try {
            Allure.step(stepName);
        } catch (Exception e) {
            System.err.println("Failed to add step: " + e.getMessage());
        }
    }

    public static void addStep(String stepName, Status status) {
        if (!isTestRunning()) return;
        try {
            Allure.step(stepName, status);
        } catch (Exception e) {
            System.err.println("Failed to add step: " + e.getMessage());
        }
    }

    public static void setEnvironmentInfo() {
        Properties props = new Properties();
        props.setProperty("Browser", System.getProperty("browser", "Chrome"));
        props.setProperty("Platform", System.getProperty("os.name"));
        props.setProperty("Environment", System.getProperty("environment", "QA"));
        props.setProperty("Java Version", System.getProperty("java.version"));

        try {
            java.io.File resultsDir = new java.io.File("target/allure-results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }
            props.store(new java.io.FileOutputStream("target/allure-results/environment.properties"),
                       "Environment Information");
        } catch (Exception e) {
            System.err.println("Failed to write environment properties: " + e.getMessage());
        }
    }

    public static void setMobileEnvironmentInfo(String deviceName, String platformName, String platformVersion) {
        Properties props = new Properties();
        props.setProperty("Device Name", deviceName);
        props.setProperty("Platform Name", platformName);
        props.setProperty("Platform Version", platformVersion);
        props.setProperty("Java Version", System.getProperty("java.version"));

        try {
            java.io.File resultsDir = new java.io.File("target/allure-results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }
            props.store(new java.io.FileOutputStream("target/allure-results/environment.properties"),
                    "Environment Information");
        } catch (Exception e) {
            System.err.println("Failed to write environment properties: " + e.getMessage());
        }
    }

    public static void addDescription(String description) {
        if (!isTestRunning()) return;
        try {
            Allure.description(description);
        } catch (Exception e) {
            System.err.println("Failed to add description: " + e.getMessage());
        }
    }

    public static void addFeature(String feature) {
        if (!isTestRunning()) return;
        try {
            Allure.feature(feature);
        } catch (Exception e) {
            System.err.println("Failed to add feature: " + e.getMessage());
        }
    }

    public static void addStory(String story) {
        if (!isTestRunning()) return;
        try {
            Allure.story(story);
        } catch (Exception e) {
            System.err.println("Failed to add story: " + e.getMessage());
        }
    }

    public static void addSeverity(io.qameta.allure.SeverityLevel severity) {
        if (!isTestRunning()) return;
        try {
            Allure.label("severity", severity.value());
        } catch (Exception e) {
            System.err.println("Failed to add severity: " + e.getMessage());
        }
    }

    public static void logSuccess(String message) {
        if (!isTestRunning()) return;
        try {
            Allure.step("✅ " + message, Status.PASSED);
        } catch (Exception e) {
            System.err.println("Failed to log success: " + e.getMessage());
        }
    }

    public static void logFailure(String message) {
        if (!isTestRunning()) return;
        try {
            Allure.step("❌ " + message, Status.FAILED);
        } catch (Exception e) {
            System.err.println("Failed to log failure: " + e.getMessage());
        }
    }

    public static void setWebEnvironmentInfo() {
        Properties props = new Properties();
        props.setProperty("Browser", System.getProperty("browser", "Chrome"));
        props.setProperty("Platform", System.getProperty("os.name"));
        props.setProperty("Environment", System.getProperty("environment", "QA"));
        props.setProperty("Java Version", System.getProperty("java.version"));

        try {
            java.io.File resultsDir = new java.io.File("target/allure-results");
            if (!resultsDir.exists()) {
                resultsDir.mkdirs();
            }
            props.store(new java.io.FileOutputStream("target/allure-results/environment.properties"),
                    "Environment Information");
        } catch (Exception e) {
            System.err.println("Failed to write environment properties: " + e.getMessage());
        }
    }

    /**
     * Safe step execution - only executes if test is running
     */
    public static void safeStep(String stepName, Runnable action) {
        if (!isTestRunning()) {
            // If no test is running, just execute the action without Allure
            try {
                action.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            return;
        }

        try {
            Allure.step(stepName, () -> {
                try {
                    action.run();
                } catch (Exception e) {
                    System.err.println("Step execution failed: " + e.getMessage());
                    throw new RuntimeException(e);
                }
            });
        } catch (Exception e) {
            System.err.println("Failed to execute Allure step: " + e.getMessage());
            // Execute the action anyway, even if Allure fails
            try {
                action.run();
            } catch (Exception actionException) {
                throw new RuntimeException(actionException);
            }
        }
    }
}
