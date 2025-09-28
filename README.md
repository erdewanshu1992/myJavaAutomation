# 🛍️ Flipkart & Amazon UI Automation Framework

This is an **enterprise-grade UI test automation framework** designed to test web and mobile versions of e-commerce platforms like Flipkart and Amazon. Built with Java, Selenium, Appium, TestNG, Cucumber, Docker, and Allure, it supports cross-platform testing (desktop, mobile, responsive) with robust CI/CD integration.

## 📋 Table of Contents

- [Features](#features)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [CI/CD](#cicd)
- [Reporting](#reporting)
- [Contributing](#contributing)

## ✨ Features

- **Cross-Platform Testing**: Supports desktop web, mobile native, mobile web, and responsive testing
- **Multi-Browser Support**: Chrome, Firefox, and mobile browsers
- **Page Object Model**: Implemented with interfaces, locators, and screen classes for maintainability
- **Test Orchestration**: TestNG suites with parallel execution
- **BDD Support**: Cucumber integration for Amazon tests
- **Retry & Listener Mechanisms**: Automatic retries and custom test listeners
- **Docker Integration**: Containerized test execution with Selenium Grid
- **Comprehensive Reporting**: Allure reports with screenshots and logs
- **CI/CD Ready**: GitHub Actions and Jenkins pipelines
- **Utilities**: Email notifications, device management, assertions, and more

## 📋 Prerequisites

- **Java**: JDK 8 or higher
- **Maven**: 3.6+
- **Node.js** (optional, for some utilities)
- **Docker** (for grid setup)
- **Appium** (for mobile testing)
- **Android SDK** (for Android mobile testing)
- **Xcode** (for iOS mobile testing, macOS only)

## 🛠️ Installation & Setup

1. **Clone the repository**:
   ```bash
   git clone <repository-url>
   cd myJavaAutomation
   ```

2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

3. **Configure environment**:
   - Update `src/main/resources/config/config.properties` or environment-specific files (`config-dev.properties`, `config-qa.properties`, `config-prod.properties`)
   - Set up browser capabilities in `src/main/resources/capabilities/`
   - For mobile testing, configure `infrastructure/execution-environment/devices-config.json`

4. **Docker Setup** (optional, for grid execution):
   ```bash
   cd infrastructure/execution-environment
   ./start-grid.sh
   ```

## 🚀 Usage

### Running Tests

Run all tests:
```bash
mvn clean test
```

Run tests for specific platforms:
```bash
# Desktop web tests
mvn clean test -Dplatform=web

# Mobile native tests
mvn clean test -Dplatform=android

# Mobile web tests
mvn clean test -Dplatform=mobile

# Responsive tests
mvn clean test -Dplatform=responsive
```

Run specific test suites:
```bash
# Using TestNG XML
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml

# Custom suite
mvn clean test -DsuiteXmlFile=infrastructure/orchestration/testng-suite.xml
```

Run Amazon BDD tests:
```bash
mvn clean test -Dcucumber.options="--tags @amazon"
```

### Test Execution Options

- **Parallel Execution**: Configure in `testng.xml` or suite files
- **Browser Selection**: Set in config properties or via `-Dbrowser=chrome`
- **Environment**: Use `-Denv=qa` to switch configurations
- **Headless Mode**: Add `-Dheadless=true`

## 📁 Project Structure

```
myJavaAutomation/
├── .gitignore
├── pom.xml
├── project_structure.txt
├── README.md
├── screenshot.png
├── infrastructure/
│   ├── ci/
│   │   ├── github-actions.yml
│   │   └── Jenkinsfile
│   ├── execution-environment/
│   │   ├── devices-config.json
│   │   ├── docker-compose.yml
│   │   └── start-grid.sh
│   └── orchestration/
│       └── testng-suite.xml
├── logs/
├── src/
│   ├── index.css
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── flipkart/
│   │   │           ├── base/
│   │   │           │   ├── Base.java
│   │   │           │   └── BaseTest.java
│   │   │           ├── config/
│   │   │           │   └── ConfigReader.java
│   │   │           ├── driver/
│   │   │           │   └── DriverManager.java
│   │   │           ├── interfaces/
│   │   │           │   ├── desktop/
│   │   │           │   │   └── IWebHomeScreen.java
│   │   │           │   ├── mobile/
│   │   │           │   │   ├── IMobileHomeScreen.java
│   │   │           │   │   └── IWelcomeScreen.java
│   │   │           │   └── responsive/
│   │   │           │       └── IResponsiveHomeScreen.java
│   │   │           ├── listeners/
│   │   │           │   ├── RetryListener.java
│   │   │           │   └── TestListener.java
│   │   │           ├── locators/
│   │   │           │   ├── DesktopHomeLocators.java
│   │   │           │   ├── MobileHomeLocators.java
│   │   │           │   └── ResponsiveHomeLocators.java
│   │   │           ├── screen/
│   │   │           │   └── ScreenFactory.java
│   │   │           ├── screens/
│   │   │           │   ├── desktop/
│   │   │           │   │   ├── DesktopHomePage.java
│   │   │           │   │   └── DesktopHomePageUpdated.java
│   │   │           │   ├── nativeMobile/
│   │   │           │   │   ├── MobileHomeScreen.java
│   │   │           │   │   └── MobileWelcomeScreen.java
│   │   │           │   └── responsive/
│   │   │           │       └── ResponsiveHomeScreen.java
│   │   │           └── utils/
│   │   │               ├── AllureUtils.java
│   │   │               ├── AssertionUtils.java
│   │   │               ├── BrowserCheck.java
│   │   │               ├── DeviceUtils.java
│   │   │               ├── EmailUtils.java
│   │   │               ├── FrameworkException.java
│   │   │               ├── LogUtils.java
│   │   │               ├── MobileBrowserCheck.java
│   │   │               ├── PlatformManager.java
│   │   │               ├── PlatformType.java
│   │   │               ├── RetryAnalyzer.java
│   │   │               ├── ScreenshotUtils.java
│   │   │               └── WaitUtils.java
│   │   └── resources/
│   │       ├── c-driver/
│   │       │   └── chromedriver
│   │       ├── capabilities/
│   │       │   ├── chrome.json
│   │       │   ├── firefox.json
│   │       │   └── mobile.json
│   │       └── config/
│   │           ├── config-dev.properties
│   │           ├── config-prod.properties
│   │           └── config-qa.properties
│   └── test/
│       ├── java/
│       │   └── com/
│       │       ├── amazon/
│       │       │   └── steps/
│       │       │       ├── AmazonSteps.java
│       │       │       └── TestRunner.java
│       │       └── flipkart/
│       │           └── tests/
│       │               ├── desktop/
│       │               │   ├── BrowserContextTest.java
│       │               │   ├── DemoLog.java
│       │               │   ├── FlipkartDesktopTests.java
│       │               │   ├── GoogleSearchTest.java
│       │               │   └── SearchTest.java
│       │               ├── mobile/
│       │               │   ├── FlipkartMobile3Tests.java
│       │               │   ├── FlipkartMobileTests.java
│       │               │   └── MobileBrowserTests.java
│       │               └── responsive/
│       │                   └── FlipkartResponsiveTests.java
│       └── resources/
│           ├── allure.properties
│           ├── config.properties
│           ├── log4j2.xml
│           ├── testng.xml
│           ├── features/
│           │   └── amazon.feature
│           └── testdata/
│               ├── loginData.json
│               └── search.csv
```

## ⚙️ Tools & Technologies

- **Java + TestNG**: Core testing framework
- **Selenium & Appium**: Web and mobile automation
- **Cucumber**: BDD for Amazon tests
- **Allure**: Test reporting
- **Docker + Selenium Grid**: Containerized execution
- **Jenkins / GitHub Actions**: CI/CD pipelines
- **Log4j2**: Logging framework
- **Maven**: Build and dependency management

## 🔄 CI/CD

### GitHub Actions
- Workflow file: `infrastructure/ci/github-actions.yml`
- Triggers on push/PR to main branch
- Runs tests in parallel across different platforms

### Jenkins
- Pipeline file: `infrastructure/ci/Jenkinsfile`
- Supports parameterized builds
- Integrates with Docker for isolated execution

## 📊 Reporting

### Allure Reports
Generate and serve reports after test execution:
```bash
allure serve target/allure-results
```

Features:
- Detailed test results with screenshots
- Historical trends
- Step-by-step execution logs
- Custom categories and severities

### Logs
- Application logs: `logs/` directory
- Configured via `src/test/resources/log4j2.xml`

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit changes (`git commit -am 'Add new feature'`)
4. Push to branch (`git push origin feature/new-feature`)
5. Create a Pull Request

### Code Standards
- Follow Page Object Model principles
- Use meaningful test names and descriptions
- Add appropriate waits and assertions
- Include screenshots on failures
- Update documentation for new features

## 📞 Support

For issues or questions:
- Check existing issues on GitHub
- Create a new issue with detailed description
- Include logs, screenshots, and environment details

---

**Note**: This framework is designed for educational and testing purposes. Ensure compliance with the terms of service of the applications being tested.
