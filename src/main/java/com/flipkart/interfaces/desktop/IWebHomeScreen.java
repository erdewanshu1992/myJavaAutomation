package com.flipkart.interfaces.desktop;

/**
 * Web-specific interface for desktop and responsive web platforms
 * Contains all methods needed for web-based testing
 */
public interface IWebHomeScreen {
    
    /**
     * Wait for home page to load
     */
    void waitForPageToLoad();
    
    /**
     * Check if home page is loaded
     */
    boolean isPageLoaded();
    
    /**
     * Perform search operation
     */
    void performSearch(String searchTerm);
    
    /**
     * Get page title
     */
    String getPageTitle();
    
    /**
     * Navigate to different sections - Web specific
     */
    void navigateTo(String destination);
    
    /**
     * Check if user is logged in - Web specific
     */
    boolean isUserLoggedIn();
    
    /**
     * Handle cookie consent - Web specific
     */
    void handleCookieConsent();
    
    /**
     * Switch to different language - Web specific
     */
    void switchLanguage(String language);
}
