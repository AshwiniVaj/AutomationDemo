Feature: Website Security Scan
 @Securitytesting
  Scenario: Check for Security Vulnerabilities on ginandjuice website
    Given I have the "ginandjuice" website URL
    When I run a passive security scan on the website
    Then I should receive a security report for "https://ginandjuice.shop/"