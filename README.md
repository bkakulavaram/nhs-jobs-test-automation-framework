# NHS Jobs Search Automation Framework

## Overview
This project is a UI Test Automation Framework developed to validate the NHS Jobs Search functionality in alignment with the provided user story and acceptance criteria.

The framework validates that jobseekers can perform job searches using basic and advanced filters and receive relevant results. It also verifies filter behaviour, search result correctness, and user-triggered sorting functionality.

The solution is designed using modern automation principles with emphasis on scalability, maintainability, and cross-browser compatibility.

---

## Tech Stack

- Java 21  
- Selenium WebDriver 4+  
- Cucumber (BDD)  
- TestNG  
- Maven  
- Page Object Model (POM)  
- Factory Design Pattern  
- Selenium Manager (native driver resolution)

---

## Testing Approach

A **user-centric, risk-based testing strategy** was followed.

Focus areas:
- End-to-end validation of job search journey from user perspective  
- Functional correctness of filters and search results  
- Validation of UI-driven behaviours (expand/collapse, filtering, sorting)  
- Cross-browser consistency (Chrome & Firefox)  
- Data-driven variability handling due to live NHS Jobs dataset  

Testing prioritised:
- Critical user flows (search → filter → results validation)  
- High-risk areas (filters and result correctness)  
- UI consistency and usability

---

## User Story

As a jobseeker on NHS Jobs website  
I want to search for a job with my preferences  
So that I can get relevant job results

---

## Acceptance Criteria

- Search results are returned based on user preferences  
- Results match applied filters (keyword, location, distance, employer, pay range where applicable)  
- Advanced search filters behave correctly (expand/collapse/apply/remove)  
- Empty search behaviour is handled correctly  
- Invalid location scenarios are handled gracefully  
- Results sorting is validated when explicitly selected by the user (e.g., “Newest First”)

---

## BDD Sample

```gherkin
Feature: NHS Job Search

Scenario: Search jobs using preferences
  Given I am a jobseeker on NHS Jobs website
  When I search using my preferences
  Then I should see jobs matching my preferences
```

---

## Requirements Coverage

| Requirement | Automated |
|------------|----------|
| Keyword search | ✅ |
| Location search | ✅ |
| Distance filter | ✅ |
| Advanced filters | ✅ |
| Employer filter | ✅ |
| Pay range filter | ✅ |
| Expand/collapse filters | ✅ |
| Clear filters | ✅ |
| Empty search handling | ✅ |
| Invalid location handling | ✅ |
| User-triggered sorting (“Newest First”) | ✅ |

---

## Framework Architecture

### Design Patterns

#### Page Object Model (POM)
- Separates UI actions from test logic
- Improves maintainability and reduces duplication
- Centralised locator management per page

#### Factory Pattern
- Centralised driver creation via `DriverFactory`
- Supports multi-browser execution strategy

#### ThreadLocal WebDriver
- Enables future parallel execution
- Ensures thread-safe test execution

---

## Driver Strategy (Selenium Manager)

This framework uses **Selenium Manager (Selenium 4.6+)** for automatic driver resolution.

Key characteristics:
- No WebDriverManager dependency  
- No manual driver binaries required  
- No local chromedriver/geckodriver setup  
- Drivers are resolved dynamically at runtime  
- Fully compatible with CI/CD pipelines  

This approach ensures environment independence and aligns with modern Selenium standards.

---

## Project Structure

```
src
 ├── main
 │    └── java
 │         ├── config
 │         ├── driver
 │         ├── pages
 │         ├── models
 │         └── utils
 │
 └── test
      ├── java
      │    ├── hooks
      │    ├── runners
      │    ├── stepdefinitions
      │    └── framework
      │
      └── resources
           └── features
```

---

## Browser Compatibility

- Chrome  
- Firefox  

All browser drivers are resolved automatically using Selenium Manager.  
No external driver setup or machine-specific configuration is required.

---

## Execution

Run all tests:
```bash
mvn clean test
```

Run on Chrome:
```bash
mvn clean test -Dbrowser=chrome
```

Run on Firefox:
```bash
mvn clean test -Dbrowser=firefox
```

---

## Locators Strategy

A stability-first locator approach is used:

- Preferred: `id`, `name`, `aria-label`  
- Secondary: CSS selectors for structured elements  
- Avoided: brittle absolute XPath  
- Dynamic elements handled using explicit waits  

This ensures resilience against UI changes and reduces flaky tests.

---

## Key Validation Strategy

### Functional Validation
- Keyword relevance
- Location matching
- Distance validation
- Employer filter validation
- Pay range validation

### Sorting Validation (User-Triggered)
- Results are validated for correct order when “Newest First” is explicitly selected

---

## Non-Functional Testing Strategy

### Accessibility
- Keyboard navigation validation considerations  
- Screen reader compatibility awareness  
- WCAG 2.2 AA alignment  
- Focus order and form labeling validation  

### Compatibility
- Cross-browser testing (Chrome, Firefox)  
- Responsive viewport validation  
- UI consistency across browsers  

### Performance Considerations
- Search response time validation  
- Large result set handling  
- Concurrent user simulation  
- UI rendering performance under load  

### Data Migration Testing (High Level)
- Record count reconciliation  
- Field mapping validation  
- Data integrity checks  
- Duplicate detection  
- End-to-end migration validation  

---

## Issues Identified During Execution

- No critical functional defects observed in search workflow  
- Minor variability in salary formatting across job listings (data-driven inconsistency from source system)  
- Result availability depends on live NHS Jobs dataset  

---

## Assumptions

- NHS Jobs UI reflects real-time data  
- Filters behave consistently across supported browsers  
- Sorting is only validated when explicitly triggered  
- Relevance is derived from visible UI data only  

---

## Known Limitations

- No API-level validation included  
- Relevance validation is UI-based only  
- Salary and job data vary based on live postings  
- Test stability depends on external NHS dataset availability  

---

## Improvements (Next Iteration)

- Parallel execution via Selenium Grid  
- CI/CD integration (GitHub Actions / Jenkins)  
- Allure reporting integration  
- Dockerized execution environment  
- Cloud execution (BrowserStack / Sauce Labs)  
- API + UI hybrid validation layer  
- Accessibility automation tooling integration  
- Performance testing integration (JMeter / Gatling)

---

## Author
Bhargavi Kakulavaram

Automation Exercise – NHS Jobs Search Automation Framework
