package com.flipkart.interfaces.mobile;

/**
 * Mobile-specific interface for native mobile app platform
 * Contains all methods needed for mobile app testing
 */
public interface IMobileHomeScreen {
    
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
     * Navigate using bottom navigation - Mobile specific
     */
    void navigateToTab(String tabName);
    
    /**
     * Handle app permissions - Mobile specific
     */
    void handlePermissions();
    
    /**
     * Swipe gestures - Mobile specific
     */
    void swipeLeft();
    void swipeRight();
    void swipeUp();
    void swipeDown();
    
    /**
     * Handle device back button - Mobile specific
     */
    void pressBackButton();
}
