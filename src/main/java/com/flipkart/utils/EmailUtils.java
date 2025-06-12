package com.flipkart.utils;

import com.flipkart.config.ConfigReader;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.util.Properties;

/**
 * Utility class for sending email reports
 * * This class provides methods to send test reports via email with attachments
 * * @author Flipkart Automation Team (Dewanshu sr.qa)
 * * @version 1.0
 * * @since 2023-10-01
 * * @description This class includes methods to send test reports via email, including the ability to attach HTML reports and send a summary of test results.
 * * This class is designed to be used in conjunction with the Flipkart automation framework and is intended to enhance test reporting capabilities.
 * * @package com.flipkart.utils
 * * @license This code is licensed under the Flipkart Open Source License.
 * * This class is part of the Flipkart automation framework and is intended to be used for sending test reports via email.
 * * This class provides a centralized way to manage email reporting in the Flipkart automation tests.
 * * This class is designed to be used in both nativeMobile and web automation tests, providing a consistent way to send test reports via email.
 * * This class includes methods to send test reports with attachments and a summary of test results.
 * * @Epic("Flipkart Automation Framework")
 * * @Feature("Email Reporting Utilities")
 * * This class is intended to be used in conjunction with TestNG test frameworks.
 * * This class provides methods to send test reports via email, including the ability to attach HTML reports and send a summary of test results.
 *
 */
public class EmailUtils {
    
    private static final ConfigReader config = ConfigReader.getInstance();
    
    public static void sendTestReport(String reportPath, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", config.getEmailHost());
            props.put("mail.smtp.port", config.getEmailPort());
            
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.getEmailUsername(), config.getEmailPassword());
                }
            });
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(config.getEmailUsername()));
            message.setRecipients(Message.RecipientType.TO, 
                                InternetAddress.parse(config.getEmailRecipients()));
            message.setSubject(subject);
            
            // Create multipart message
            Multipart multipart = new MimeMultipart();
            
            // Text part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);
            multipart.addBodyPart(messageBodyPart);
            
            // Attachment part
            if (reportPath != null && new File(reportPath).exists()) {
                messageBodyPart = new MimeBodyPart();
                DataSource source = new FileDataSource(reportPath);
                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("TestReport.html");
                multipart.addBodyPart(messageBodyPart);
            }
            
            message.setContent(multipart);
            
            Transport.send(message);
            System.out.println("Email sent successfully!");
            
        } catch (MessagingException e) {
            System.err.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void sendTestSummary(int totalTests, int passedTests, int failedTests, int skippedTests) {
        String subject = "Flipkart Automation Test Results";
        String body = String.format("""
            Test Execution Summary:
            
            Total Tests: %d
            Passed: %d
            Failed: %d
            Skipped: %d
            
            Pass Rate: %.2f%%
            
            Please find the detailed report attached.
            
            Thanks,
            Automation Team
            """, totalTests, passedTests, failedTests, skippedTests, 
            (passedTests * 100.0 / totalTests));
        
        sendTestReport(config.getReportPath() + "allure-report/index.html", subject, body);
    }
}
