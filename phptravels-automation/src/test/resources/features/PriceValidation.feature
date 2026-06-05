Feature: PHPTravels Dynamic Price Validation

  @Regression @Price
  Scenario: Validate highest, lowest, average price and duplicate hotels
    Given user launches browser
    When user searches hotel with destination "Dubai"
    And user clicks hotel search button
    Then fetch all hotel prices dynamically
    And validate highest hotel price
    And validate lowest hotel price
    And validate average hotel price
    And validate duplicate hotel names