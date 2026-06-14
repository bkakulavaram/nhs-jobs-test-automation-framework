# NHS Jobs Test Automation Framework

## Overview

This project is a UI test automation framework developed to validate the NHS Jobs Search functionality.

The framework verifies that jobseekers can search for jobs using basic and advanced search criteria and receive relevant, recently posted job results.

The implementation follows industry-standard automation practices using:

- Java
- Selenium WebDriver
- Cucumber BDD
- TestNG
- Page Object Model (POM)
- Maven

---

## Business Requirements Covered

### Basic Search

Validate that users can search jobs using:

- What (job keyword)
- Where (location)
- Search distance

Validation includes:

- Search results are returned
- Results contain relevant job titles
- Results are within selected distance
- Results are recently posted
- Results are sorted by newest date first

---

### Advanced Search

Validate additional search filters:

- Employer
- Pay range
- Job reference (framework support available)

Validation includes:

- Returned jobs satisfy selected filters
- Search options can be expanded/collapsed

---

### Search Page Behaviour

Validate:

- Advanced search fields visibility
- Hiding advanced filters
- Clear filters functionality
- Empty search behaviour
- Invalid location handling

---

## Framework Design

### Design Patterns

#### Page Object Model (POM)

Encapsulates page interactions and locators.

Pages:

- BasePage
- SearchPage
- ResultsPage

---

#### Factory Pattern

DriverFactory manages browser creation.

Supported browsers:

- Chrome
- Firefox

---

#### Rule-Based Validation Engine

A reusable validation engine validates results against configurable search rules.

Example:

```java
ResultRule rule = new ResultRule()
        .setKeyword("nurse")
        .setEmployer("NHS")
        .setMaxDistance(5.0)
        .setPayRange("£20,000 to £30,000");
```

This allows new validation rules to be added without changing test logic.

---

## Project Structure

```text
src
 ├── main
 └── test
      ├── java
      │    ├── config
      │    ├── driver
      │    ├── hooks
      │    ├── models
      │    ├── pages
      │    ├── runners
      │    └── stepdefinitions
      │
      └── resources
           ├── features
           └── config.properties
```

---

## Technology Stack

| Tool | Purpose |
|--------|----------|
| Java | Programming Language |
| Selenium WebDriver | Browser Automation |
| Cucumber | BDD |
| TestNG | Test Execution |
| Maven | Build Management |
| WebDriverManager | Driver Management |

---

## Browser Support

Framework supports:

- Chrome
- Firefox

Run Chrome:

```bash
mvn clean test
```

Run Firefox:

```bash
mvn clean test -Dbrowser=firefox
```

---

## Execution

Execute all tests:

```bash
mvn clean test
```

Generate reports:

```bash
target/cucumber-report.html
```

---

## Reporting

Generated outputs:

- Console logs
- Cucumber HTML Report
- Cucumber JSON Report

Location:

```text
target/
```

---

## Key Validation Logic

### Relevance Validation

Each search result is validated against:

- Keyword match
- Distance match
- Date freshness
- Employer match
- Pay range match
- Job reference match

Results failing validation are logged for debugging.

---

### Date Validation

Jobs must be posted within the allowed freshness window.

Default:

```text
30 days
```

---

### Distance Validation

Returned jobs must fall within the selected search radius.

Examples:

```text
+5 Miles
+10 Miles
+20 Miles
```

---

## Assumptions

- NHS Jobs search results are returned in descending date order.
- Search filters behave consistently across supported browsers.
- Search result cards contain location, date and salary information.

---

## Potential Future Enhancements

- Screenshot capture on failure
- Parallel execution
- CI/CD integration
- Allure reporting
- Docker execution
- Selenium Grid support
- Cross-browser execution matrix
- API contract testing
- Accessibility testing

---

## Author

Bhargavi Kakulavaram

SDET Automation Exercise
NHS Jobs Search Automation Framework
