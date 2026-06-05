Feature: PHPTravels Booking Table Validation

  @Regression @Table
  Scenario: Validate booking table dynamically
    Given user launches browser
    When user logs in with valid credentials from excel row 1
    And user opens bookings page
    Then fetch complete booking table dynamically
    And validate duplicate booking rows
    And validate highest booking amount
    And validate lowest booking amount
    And convert table data into map