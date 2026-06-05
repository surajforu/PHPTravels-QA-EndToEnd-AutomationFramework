Feature: PHPTravels Complete Booking Workflow

  @E2E @Regression
  Scenario: Complete hotel booking flow
    Given user launches browser
    When user logs in with valid credentials from excel row 1
    # This resets the layout back to the public homepage while keeping your login session active!
    And user navigates to homepage "https://phptravels.net/"
    And user searches hotel with destination "Dubai"
    And user selects first available hotel
    And user enters traveller details
    And user confirms hotel booking
    Then booking confirmation message should be displayed
    And user logs out