package com.flipkart.tests.desktop;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DemoLog {
    private static final Logger log = LogManager.getLogger(DemoLog.class);

    public static void main(String[] args) {
        log.info("Test log - info");
        log.error("Test log - error");
    }
}

