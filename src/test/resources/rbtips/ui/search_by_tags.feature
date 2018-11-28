Feature: As a user, I want to be able to search reading tips I have added by their tags
  
  Scenario: Searching the article I recently added by its
    Given a valid article is saved with tag "tag"
    When Command search is given
    And Command search by tags is given
    Then Article with tag "tag" is found