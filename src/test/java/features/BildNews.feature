@bild_news_login
Feature: Bild News Login Feature

  @verify_login_features_with_correct_credentials
  Scenario: Verify the login process with correct credentials
  Given I navigated to the Bild News Home screen
  And I goto the Login screen
  When I perform login using correct credentials "harisehsan50@gmail.com" and "Test1234"
  Then I should be loggedIn as "harisehsan50@gmail.com"

#  @verify_login_features_with_incorrect_credentials
#  Scenario: Verify the login process error with incorrect credentials
#    Given I navigated to the Bild News Home screen
#    And I goto the Login screen
#    When I perform login using incorrect credentials "harisehsan50@gmail.com" and "Test123"
#    Then I should see login error message