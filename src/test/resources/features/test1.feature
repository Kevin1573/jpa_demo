@unitTest
Feature: Learn How to use Karate for testing
  Scenario: Testing valid GET request
    Given url 'http://localhost:8090/api/users/2'
    When method GET
    Then status 200

  Scenario: Testing the exact response of a GET request
    Given url 'http://localhost:8090/api/users/2'
    When method GET
    Then status 200
    And match response == {id: "1234", name: "John Doe", email: "john@doe.com"}

  Scenario: Testing that GET response contains a specific value
    Given url 'http://localhost:8090/api/users/2'
    When method GET
    Then status 200
    And match $ contains {id: "1234"}