# ✈️ PHPTravels Automation Framework

## Overview

This project is a Hybrid Test Automation Framework developed for the PHPTravels travel booking application using Selenium WebDriver, Java, Cucumber, TestNG, Maven, and Extent Reports.

The framework follows industry-standard automation practices including Page Object Model (POM), Behavior Driven Development (BDD), reusable utility classes, and data-driven testing.

---

## 🚀 Technologies Used

- Java
- Selenium WebDriver
- Cucumber (BDD)
- TestNG
- Maven
- Apache POI
- Extent Reports
- Git & GitHub

---

## 🏗 Framework Architecture

### Hybrid Framework

The framework combines:

- Page Object Model (POM)
- Cucumber BDD
- Data-Driven Testing
- Utility Classes
- TestNG Integration

---

## 📂 Project Structure

```text
PHPTravels-Automation-Framework
│
├── src/test/java
│   ├── base
│   ├── pages
│   ├── stepdefinitions
│   ├── runners
│   └── utilities
│
├── src/test/resources
│   ├── features
│   ├── config
│   └── testdata
│
├── reports
├── screenshots
├── test-output
├── testng.xml
└── pom.xml
```

---

## ✅ Automated Modules

### User Authentication
- User Login
- User Logout
- Login Validation

### Hotel Booking
- Search Hotels
- Select Hotel
- Booking Workflow Validation

### Flight Booking
- Search Flights
- Select Flight
- Booking Validation

### User Dashboard
- Verify User Profile
- Verify Booking History

### Navigation Testing
- Menu Validation
- Page Navigation Verification

---

## 📖 Cucumber BDD Implementation

### Sample Feature

```gherkin
Feature: Login Functionality

Scenario: Successful Login
  Given User launches PHPTravels application
  When User enters valid username and password
  And User clicks Login button
  Then User should be logged in successfully
```

### Sample Step Definition

```java
@Given("User launches PHPTravels application")
public void launchApplication() {
    driver.get(baseURL);
}
```

---

## 📊 Reporting

The framework generates:

- Extent Reports
- TestNG Reports
- Cucumber Reports

Reports provide:

- Pass/Fail Status
- Execution Summary
- Detailed Logs
- Screenshots on Failure

---

## ⚙️ Prerequisites

Before execution, ensure the following are installed:

- Java JDK 8 or above
- Eclipse IDE / IntelliJ IDEA
- Maven
- Google Chrome
- TestNG Plugin
- Cucumber Plugin (Optional)

---

## 🔧 Setup Instructions

### Clone Repository

```bash
git clone https://github.com/ThisIsSurajShaw/PHPTravels-Selenium-Cucumber-Framework.git
```

### Import Project

1. Open Eclipse
2. File → Import
3. Existing Maven Project
4. Select Project Folder
5. Finish

### Update Dependencies

```bash
mvn clean install
```

---

## ▶️ Test Execution

### Run Through TestNG Runner

```text
Right Click Runner Class
→ Run As
→ TestNG Test
```

### Run Using Maven

```bash
mvn test
```

---

## 📈 Features Implemented

- Page Object Model (POM)
- Cucumber BDD Framework
- TestNG Integration
- Maven Build Management
- Extent Reports
- Data-Driven Testing
- Reusable Utility Methods
- Screenshot Capture

---

## 🔮 Future Enhancements

- Jenkins Integration
- Parallel Execution
- Cross Browser Testing
- Docker Integration
- CI/CD Pipeline
- Cloud Execution

---

## 👨‍💻 Author

### Suraj Shaw

- GitHub: https://github.com/ThisIsSurajShaw
- LinkedIn: https://www.linkedin.com/in/thisissurajshaw

---

## 📄 License

This project is created for educational, learning, and automation practice purposes.
