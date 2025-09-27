@AmazonTest
Feature: Amazon Search

  Scenario: Search for a product on Amazon
    Given I open the Amazon website
    When I search for "laptop"
    Then I should see results related to "laptop"
