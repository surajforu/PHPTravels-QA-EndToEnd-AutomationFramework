Feature: PHPTravels User Registration Module

  @Regression @Registration
  Scenario: Validate new user registration with mandatory details
    Given user launches browser
    When user opens registration page
    And user enters mandatory registration details
    And user submits registration form
    Then user should see successful registration message