package com.flipkart.utils;

import com.flipkart.config.ConfigReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Platform manager to determine the current platform
 */
public class PlatformManager {
    private static final Logger logger = LoggerFactory.getLogger(PlatformManager.class);

    public enum PlatformType {
        DESKTOP, RESPONSIVE, NATIVEMOBILE
    }

    public static PlatformType getPlatform() {
        String platform = ConfigReader.getInstance().getPlatform().toLowerCase();
        
        // Debug log to see what's being read from config
        logger.info("🔍 Reading platform from config: '{}'", platform);
        
        // Force platform based on test class name
        String className = Thread.currentThread().getStackTrace()[2].getClassName();
        
        if (className.contains("FlipkartDesktopTests")) {
            logger.info("🔄 Forcing DESKTOP platform for FlipkartDesktopTests");
            return PlatformType.DESKTOP;
        }
        
        if (className.contains("FlipkartResponsiveTests")) {
            logger.info("🔄 Forcing RESPONSIVE platform for FlipkartResponsiveTests");
            return PlatformType.RESPONSIVE;
        }
        
        if (className.contains("FlipkartMobileTests")) {
            logger.info("🔄 Forcing NATIVEMOBILE platform for FlipkartMobileTests");
            return PlatformType.NATIVEMOBILE;
        }
        
        switch (platform) {
            case "desktop":
                return PlatformType.DESKTOP;
            case "responsive":
                return PlatformType.RESPONSIVE;
            case "nativemobile":
            case "mobile":
                return PlatformType.NATIVEMOBILE;
            default:
                logger.warn("⚠️ Invalid platform in config: '{}', defaulting to DESKTOP", platform);
                return PlatformType.DESKTOP;
        }
    }
}
