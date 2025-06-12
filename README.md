# 🛍️ Flipkart-Like Automation Framework

This is an **enterprise-grade UI test automation framework** designed to test web and mobile versions of e-commerce platforms like Flipkart, built with Java, Selenium, Appium, TestNG, Docker, and Allure.

---

## 📁 Folder Structure
src
├── index.css
├── main
│   └── java
│   └── com
│   └── flipkart
│   ├── base
│   │   ├── Base.java
│   │   └── BaseTest.java
│   ├── config
│   │   └── ConfigReader.java
│   ├── driver
│   │   └── DriverManager.java
│   ├── interfaces
│   │   ├── desktop
│   │   │   └── IWebHomeScreen.java
│   │   ├── mobile
│   │   │   ├── IMobileHomeScreen.java
│   │   │   └── IWelcomeScreen.java
│   │   └── responsive
│   │   └── IResponsiveHomeScreen.java
│   ├── listeners
│   │   ├── RetryListener.java
│   │   └── TestListener.java
│   ├── screen
│   │   └── ScreenFactory.java
│   ├── screens
│   │   ├── desktop
│   │   │   └── DesktopHomePage.java
│   │   ├── nativeMobile
│   │   │   ├── MobileHomeScreen.java
│   │   │   └── MobileWelcomeScreen.java
│   │   └── responsive
│   │   └── ResponsiveHomeScreen.java
│   └── utils
│   ├── AllureUtils.java
│   ├── AssertionUtils.java
│   ├── DeviceUtils.java
│   ├── EmailUtils.java
│   ├── LogUtils.java
│   ├── PlatformManager.java
│   ├── PlatformType.java
│   ├── RetryAnalyzer.java
│   ├── ScreenshotUtils.java
│   └── WaitUtils.java
└── test
├── java
│   └── com
│   └── flipkart
│   └── tests
│   ├── desktop
│   │   └── FlipkartDesktopTests.java
│   ├── mobile
│   │   └── FlipkartMobileTests.java
│   └── responsive
│   └── FlipkartResponsiveTests.java
└── resources
├── allure.properties
├── config.properties
├── log4j2.xml
└── testng.xml

infrastructure
├── ci
│   ├── Jenkinsfile
│   └── github-actions.yml
├── execution-environment
│   ├── devices-config.json
│   ├── docker-compose.yml
│   └── start-grid.sh
└── orchestration
└── testng-suite.xml




---

## ⚙️ Tools & Technologies

- Java + TestNG
- Selenium & Appium
- Allure Reporting
- Docker + Appium Server + Grid
- Jenkins / GitHub Actions CI
- Log4j2 Logging

---

## 🚀 How to Run

```bash
# Run tests with Maven
mvn clean test


## To run specific platforms:
-Dplatform=web
-Dplatform=android


## 🐳 Docker Grid Setup
cd infrastructure/execution-environment
./start-grid.sh

## 📊 Allure Report
# After test execution
allure serve target/allure-results

## 🧪 Test Orchestration
infrastructure/orchestration/testng-suite.xml

