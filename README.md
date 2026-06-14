# NHS Jobs Search Automation Framework

## Overview

This project is a UI Test Automation Framework developed to validate the NHS Jobs Search functionality in accordance with the provided user story and acceptance criteria.

The framework verifies that jobseekers can search for jobs using both basic and advanced search criteria and receive relevant, recently posted job results sorted by newest date posted.

The solution follows industry-standard automation engineering practices using:

* Java 21
* Selenium WebDriver 4
* Cucumber BDD
* TestNG
* Maven
* Page Object Model (POM)
* Factory Design Pattern

---

## User Story

**As a jobseeker on NHS Jobs website**

**I want to search for a job with my preferences**

**So that I can get recently posted job results**

---

## Acceptance Criteria

* Given I am a jobseeker on NHS Jobs website
* When I put my preferences into the Search functionality
* Then I should get a list of jobs which matches my preferences
* And sort my search results with the newest Date Posted

---

## BDD Example

```gherkin
Feature: NHS Job Search

Scenario: Search jobs using preferences
  Given I am a jobseeker on NHS Jobs website
  When I search using my preferences
  Then I should see jobs matching my preferences
  And results should be sorted by newest date posted
```

---

## Requirements Coverage Matrix

| Requirement                          | Automated |
| ------------------------------------ | --------- |
| Search using job keyword             | ✅         |
| Search using location                | ✅         |
| Search using distance filter         | ✅         |
| Results returned successfully        | ✅         |
| Results match search preferences     | ✅         |
| Results sorted by newest date posted | ✅         |
| Advanced search filters              | ✅         |
| Employer filter validation           | ✅         |
| Pay range filter validation          | ✅         |
| Search options expand/collapse       | ✅         |
| Empty search behaviour               | ✅         |
| Invalid location handling            | ✅         |
| Clear filters functionality          | ✅         |

---

## Business Scenarios Covered

### Basic Search

Validate users can search using:

* What (job keyword)
* Where (location)
* Distance

Validation includes:

* Search results are returned
* Results contain relevant jobs
* Results fall within selected distance
* Results are recently posted
* Results are sorted by newest date first

---

### Advanced Search

Validate:

* Employer
* Pay range
* Job reference framework support

Validation includes:

* Returned jobs satisfy selected filters
* Advanced search filters appear correctly
* Search options can be expanded and collapsed

---

### Search Page Behaviour

Validate:

* More search options visibility
* Fewer search options functionality
* Clear filters functionality
* Empty search behaviour
* Invalid location handling

---

## Framework Design

### Design Patterns Used

#### Page Object Model (POM)

Separates page interactions from test logic to improve maintainability and readability.

Pages implemented:

* BasePage
* SearchPage
* ResultsPage

---

#### Factory Pattern

DriverFactory centralises browser creation and management.

Supported browsers:

* Chrome
* Firefox

---

#### Thread-Safe Driver Management

ThreadLocal WebDriver implementation enables future support for parallel execution without framework redesign.

---

#### Reusable Validation Layer

Search results are validated through reusable validation methods rather than embedding validation logic directly into step definitions.

Validation includes:

* Keyword relevance
* Distance validation
* Date validation
* Employer validation
* Pay range validation

---

## Framework Design Decisions

* Page Object Model was selected to minimise locator duplication and improve maintainability.
* Browser creation is abstracted through DriverFactory to support cross-browser execution.
* Validation logic is separated from page interactions to improve reusability.
* ThreadLocal WebDriver implementation supports future parallel execution.
* Explicit waits are used to improve test stability and reduce flaky failures.

---

## Project Structure

```text
src
 ├── main
 └── test
      ├── java
      │
      ├── config
      ├── driver
      ├── hooks
      ├── models
      ├── pages
      ├── runners
      ├── stepdefinitions
      │
      └── resources
           ├── features
           └── config.properties
```

---

## Technology Stack

| Tool                                           | Purpose                   |
| ---------------------------------------------- | ------------------------- |
| Java 21                                        | Programming Language      |
| Selenium WebDriver                             | Browser Automation        |
| Cucumber                                       | BDD Framework             |
| TestNG                                         | Test Execution            |
| Maven                                          | Build Management          |
| Selenium Manager / Automatic Driver Resolution | Browser Driver Management |

---

## Browser Support

The framework supports execution on:

* Chrome
* Firefox

Browser drivers are resolved automatically at runtime and are not committed to the repository.

### Run Chrome

```bash
mvn clean test
```

### Run Firefox

```bash
mvn clean test -Dbrowser=firefox
```

---

## Execution

Execute all tests:

```bash
mvn clean test
```

Execute specific browser:

```bash
mvn clean test -Dbrowser=chrome
```

```bash
mvn clean test -Dbrowser=firefox
```

---

## Reporting

Generated outputs include:

* Console Logs
* Cucumber HTML Report
* Cucumber JSON Report

Location:

```text
target/
```

Example:

```text
target/cucumber-report.html
```

---

## Key Validation Logic

### Relevance Validation

Each result is validated against:

* Search keyword
* Search location
* Search distance
* Employer filter
* Pay range filter
* Job reference filter

Results failing validation are logged for analysis and debugging.

---

### Date Validation

Search results are validated to ensure vacancies are recently posted.

Default validation window:

```text
30 Days
```

---

### Distance Validation

Returned vacancies are validated against the selected search radius.

Examples:

```text
+5 Miles
+10 Miles
+20 Miles
```

---

## Non-Functional Considerations

### Accessibility

The following accessibility areas would be covered:

* Keyboard navigation
* Screen reader compatibility
* WCAG 2.2 AA compliance
* Colour contrast validation
* Form field labelling validation
* Focus order verification

---

### Compatibility

Compatibility considerations include:

* Chrome browser testing
* Firefox browser testing
* Responsive viewport testing
* Different screen resolutions
* Cross-browser behaviour verification

---

### Performance Testing Considerations

Performance testing would focus on:

* Search response times
* High-volume search requests
* Concurrent user load
* Result rendering performance
* Backend search scalability
* Peak usage behaviour

Recommended tools:

* JMeter
* Gatling
* Azure Load Testing

---

### Data Migration Testing Considerations

For migration of vacancies and applications from a legacy NHS Trust system into NHS Jobs:

* Record count reconciliation
* Data completeness validation
* Data integrity verification
* Field mapping validation
* Historical vacancy validation
* Historical application validation
* Duplicate record detection
* User acceptance testing
* End-to-end migration verification

---

## Assumptions

* NHS Jobs returns results in descending date order.
* Search filters behave consistently across supported browsers.
* Search result cards contain sufficient information for validation.
* NHS Jobs search relevance algorithm is not publicly documented.

---

## Known Limitations

* Search relevance is validated using visible result information only.
* Salary formats may vary between vacancies.
* Some search combinations may naturally return limited or no results depending on live NHS Jobs data.

---

## Potential Future Enhancements

* Screenshot capture on failure
* Parallel execution
* CI/CD integration
* Allure reporting
* Docker execution
* Selenium Grid support
* Cross-browser execution matrix
* Accessibility automation integration
* API contract testing
* Cloud execution using BrowserStack or Sauce Labs

---

## Author

**Bhargavi Kakulavaram**

SDET Automation Exercise – NHS Jobs Search Automation Framework
