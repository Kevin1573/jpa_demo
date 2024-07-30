Feature: Sample Test

  Background:
    * url baseUrl

  Scenario: get all
    Given path "/api/sample"
#    * header Authorization = call read('utils/basic-auth.js') { username: 'admin', password: 'admin' }
    When method get
    Then status 200