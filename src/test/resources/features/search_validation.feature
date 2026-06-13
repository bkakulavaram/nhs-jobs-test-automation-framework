Feature: NHS Job Search Validation
  Background:
    Given I am on the NHS Jobs search page

  Scenario: Invalid location should show error page
    When I search for a job using invalid location "@@@INVALID LOCATION###"
    Then I should be redirected to location_not_found or too_many_locations page

  Scenario: Empty search should still allow submission
    When I click search without entering any data
    Then I should see all the jobs on NHS