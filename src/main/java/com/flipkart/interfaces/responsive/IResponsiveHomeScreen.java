package com.flipkart.interfaces.responsive;

/**
 * Responsive-specific interface for responsive web testing on mobile devices
 * Contains all methods needed for responsive web testing
 */
public interface IResponsiveHomeScreen {
    
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
    
    /**
     * Handle mobile-specific navigation elements in responsive mode
     */
    void handleMobileMenu();
    
    /**
     * Check if mobile menu is expanded
     */
    boolean isMobileMenuExpanded();
}
