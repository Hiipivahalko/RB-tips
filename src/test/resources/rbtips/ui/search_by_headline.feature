Feature: As a user, I want to be able to search reading tips I have added by their headlines

  Scenario: Searching the article I recently added by its title
    Given a valid blog is saved with headline "otsikko"
    When Command search is given
    And Command search by headline is given
    Then Article with headline "otsikko" is found
    