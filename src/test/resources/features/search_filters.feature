Feature: NHS Job Advanced Search Filters

  Background:
    Given I am on the NHS Jobs search page

  Scenario: Validate advanced search filters appear correctly
    When I have opened more search options
    Then I should see more search filters

  Scenario: Hide advanced search filters
    Given I have opened more search options
    When I click on fewer search options
    Then advanced search fields should be hidden

  Scenario: Clear filters resets search page
    Given I enter search criteria "nurse", "reading" and "+5 Miles"
    When I click on clear filters
    Then all fields should be reset to default state