Feature: PHPTravels Hotel Search Module

  @Smoke @Regression @HotelSearch
  Scenario: Validate hotel search results
    Given user launches browser
    When user searches hotel with destination "Dubai"
    And user selects checkin date "20-06-2026"
    And user selects checkout date "25-06-2026"
    And user selects travellers adults 2 and children 1
    And user clicks hotel search button
    Then hotel search results should be displayed
    And available hotels count should be greater than 0