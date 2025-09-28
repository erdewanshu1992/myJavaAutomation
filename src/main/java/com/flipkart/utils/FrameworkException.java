package com.flipkart.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Custom exception class for framework-specific errors
 * Provides better error handling and logging for automation framework
 */
public class FrameworkException extends RuntimeException {

    private static final Logger logger = LoggerFactory.getLogger(FrameworkException.class);

    public FrameworkException(String message) {
        super(message);
        logger.error("Framework Exception: {}", message);
    }

    public FrameworkException(String message, Throwable cause) {
        super(message, cause);
        logger.error("Framework Exception: {} - Cause: {}", message, cause.getMessage());
    }

    public FrameworkException(Throwable cause) {
        super(cause);
        logger.error("Framework Exception - Cause: {}", cause.getMessage());
    }

    /**
     * Exception for driver initialization failures
     */
    public static class DriverInitializationException extends FrameworkException {
        public DriverInitializationException(String message) {
            super(message);
        }

        public DriverInitializationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception for configuration loading failures
     */
    public static class ConfigurationException extends FrameworkException {
        public ConfigurationException(String message) {
            super(message);
        }

        public ConfigurationException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception for element interaction failures
     */
    public static class ElementInteractionException extends FrameworkException {
        public ElementInteractionException(String message) {
            super(message);
        }

        public ElementInteractionException(String message, Throwable cause) {
            super(message, cause);
        }
    }

    /**
     * Exception for test data loading failures
     */
    public static class TestDataException extends FrameworkException {
        public TestDataException(String message) {
            super(message);
        }

        public TestDataException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}