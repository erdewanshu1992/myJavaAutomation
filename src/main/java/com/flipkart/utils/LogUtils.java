package com.flipkart.utils;

import java.io.File;
import java.io.RandomAccessFile;

/* * LogUtils.java
 * Utility class to read the last N lines of a log file
 * Useful for debugging and monitoring application logs
 * * @author Flipkart Automation Team (Dewanshu sr.qa)
 * * @version 1.0
 * * @since 2023-10-01
 * * @description This class provides a method to read the last N lines of a specified log file.
 * * @see java.io.RandomAccessFile
 * * @see java.io.File
 * * @package com.flipkart.utils
 * * @license This code is licensed under the Flipkart Open Source License.
 * * @Epic("Flipkart Mobile App Testing")
 * * @Feature("Log Management")
 * * This class is designed to be used in conjunction with the Flipkart nativeMobile automation suite.
 * * This class is intended to be used for debugging purposes, especially in CI/CD pipelines.
 * * This class provides a simple way to access the last few lines of log files without loading the entire file into memory.
 * * This class can be extended to include more log management features in the future.
 * * This class is part of the Flipkart nativeMobile automation suite and is intended to be run as part of the CI/CD pipeline.
 * * This class is designed to be run on nativeMobile devices using Appium and TestNG.
 * * This class includes annotations for Allure reporting to enhance test documentation and reporting.
 * * This class is designed to be used in conjunction with the Flipkart nativeMobile automation suite.
 *
 */

public class LogUtils {

    // Reads last N lines of log file
    public static String getLastLogs() {
        File logFile = new File("logs/test.log"); // Adjust your log path

        if (!logFile.exists()) return "Log file not found.";

        StringBuilder logs = new StringBuilder();
        int linesToRead = 30; // You can adjust

        try (RandomAccessFile file = new RandomAccessFile(logFile, "r")) {
            long fileLength = file.length() - 1;
            int lines = 0;

            for(long pointer = fileLength; pointer >= 0; pointer--){
                file.seek(pointer);
                char c;
                c = (char)file.read();

                if(c == '\n') lines++;

                logs.insert(0, c);

                if(lines == linesToRead) break;
            }
        } catch (Exception e) {
            return "Failed to read logs: " + e.getMessage();
        }

        return logs.toString();
    }
}
