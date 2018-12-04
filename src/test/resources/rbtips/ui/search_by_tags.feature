Feature: As a user, I want to be able to search reading tips I have added by their tags

  Scenario: Searching the article I recently added by its
    Given a valid article is saved with tag "tag"
    When Command search is given
    And Command search by tags is given
    Then Article with tag "tag" is found

  Scenario: Searching with a tag that does not have any matches
    Given a valid article is saved with tag "tag"
    When Command search is given
    And Command search by tags is given
    Then Articles are not found when searched with tag "doesnotexist"

  Scenario: Searching with multiple tags
    Given a valid article is saved with tag "tag"
    And a valid article is saved with the tag "tagi"
    When Command search is given
    And Command search by tags is given
    Then Two articles are found when searched with tag "tag,tagi"
