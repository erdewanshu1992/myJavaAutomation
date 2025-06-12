package com.flipkart.screen;

import com.flipkart.interfaces.desktop.IWebHomeScreen;
import com.flipkart.interfaces.mobile.IMobileHomeScreen;
import com.flipkart.interfaces.mobile.IWelcomeScreen;
import com.flipkart.interfaces.responsive.IResponsiveHomeScreen;
import com.flipkart.screens.desktop.DesktopHomePage;
import com.flipkart.screens.nativeMobile.MobileHomeScreen;
import com.flipkart.screens.nativeMobile.MobileWelcomeScreen;
import com.flipkart.screens.responsive.ResponsiveHomeScreen;
import com.flipkart.utils.PlatformManager;
import org.openqa.selenium.WebDriver;

/**
 * Factory class to create screen objects based on platform
 * Returns appropriate interface implementations
 */
public class ScreenFactory {

    /**
     * Get platform-appropriate welcome screen implementation
     */
    public static IWelcomeScreen getWelcomeScreen(WebDriver driver) {
        switch (PlatformManager.getPlatform()) {
            case DESKTOP:
                throw new UnsupportedOperationException("Welcome screen not applicable for desktop");
            case RESPONSIVE:
                throw new UnsupportedOperationException("Welcome screen not applicable for responsive");
            case NATIVEMOBILE:
                return new MobileWelcomeScreen(driver);
            default:
                throw new UnsupportedOperationException("Platform not supported.");
        }
    }
    
    /**
     * Type-safe method to get web-specific home screen
     */
    public static IWebHomeScreen getWebHomeScreen(WebDriver driver) {
        switch (PlatformManager.getPlatform()) {
            case DESKTOP:
                return new DesktopHomePage(driver);
            default:
                throw new UnsupportedOperationException("Web home screen not supported for platform: " + PlatformManager.getPlatform());
        }
    }
    
    /**
     * Type-safe method to get responsive home screen
     */
    public static IResponsiveHomeScreen getResponsiveHomeScreen(WebDriver driver) {
        if (PlatformManager.getPlatform() == PlatformManager.PlatformType.RESPONSIVE) {
            return new ResponsiveHomeScreen(driver);
        }
        throw new UnsupportedOperationException("Responsive home screen not supported for platform: " + PlatformManager.getPlatform());
    }
    
    /**
     * Type-safe method to get mobile-specific home screen
     */
    public static IMobileHomeScreen getMobileHomeScreen(WebDriver driver) {
        if (PlatformManager.getPlatform() == PlatformManager.PlatformType.NATIVEMOBILE) {
            return new MobileHomeScreen(driver);
        }
        throw new UnsupportedOperationException("Mobile home screen not supported for platform: " + PlatformManager.getPlatform());
    }
}
