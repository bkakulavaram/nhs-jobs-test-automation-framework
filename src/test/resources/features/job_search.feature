Feature: NHS Job Search Functionality

  As a jobseeker
  I want to search for jobs using my preferences
  So that I can find relevant and recently posted job results

  Background:
    Given I am on the NHS Jobs search page

  Scenario Outline: Search jobs using basic valid preferences

    When I search using "<job>", "<location>" and "<distance>"
    Then relevant job results should be displayed
    Then results should be sorted by newest date posted first

    Examples:
      | job            | location  | distance |
      | engineer       | Manchester| +5 Miles  |
      | nurse          | Reading   | +5 Miles  |
      | doctor         | London    | +10 Miles |
      | pharmacist     | Leeds     | +20 Miles |



  Scenario: Search using advanced filters

    When I search using advanced criteria
      | job       | nurse |
      | location  | Reading |
      | distance  | +5 Miles |
      | employer  | Berkshire Healthcare NHS Foundation Trust |
      | payRange  | £20,000 to £30,000 |

    Then advanced search results should match my criteria