package com.flipkart.interfaces.mobile;

/**
 * Interface for Welcome Screen functionality across different platforms
 */
public interface IWelcomeScreen {
    
    /**
     * Wait for welcome page to load
     */
    void waitForPageToLoad();
    
    /**
     * Check if welcome page is loaded
     */
    boolean isPageLoaded();
    
    /**
     * Skip login functionality
     */
    void skipLoginBtn();
}
