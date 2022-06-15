#Author: Tae-Hyung Kim
#Date: 13/06/2022
Feature: Validate Linux downloads

Scenario: Download all linux versions
		Given user is on Mega desktop page
    When user select Linux button
    And download all versions of Linux version
    #And download all of them
    Then all linux versions are stored in local project directory