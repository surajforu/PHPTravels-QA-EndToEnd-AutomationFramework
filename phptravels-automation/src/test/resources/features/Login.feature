Feature: PHPTravels Login Module

  @Smoke @Regression
  Scenario Outline: Validate Login Functionality
    Given user launches browser
    When user enters login username "<username>" and password "<password>"
    And user clicks on login button
    Then validate login result for "<expectedResult>"

    Examples: 
      | username            | password  | expectedResult |
      | user@phptravels.com | demouser  | valid          |
      | invalid@gmail.com   | invalid   | invalid        |
      | demouser            | invalid   | invalid        |
      |                     | user@phptravels.com | blankUsername |
      | user@phptravels.com |           | blankPassword  |