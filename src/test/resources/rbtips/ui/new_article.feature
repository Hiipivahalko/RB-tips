Feature: as a user I want to be able to add new article tip

  Scenario: creating new article successfully with valid inputs
    Given User clicks the button Add new tip
    When headline "Headline" and author "Blog Author" and url "www.blog.com" are entered
    Then artile with headline "Headline" and author "Blog Author" and url "www.blog.com" is found

  Scenario: creating new article fails because invalid headline
    Given User clicks the button Add new tip
    When headline "ots" and author "Blog author" and url "www.blog.com" are entered
    Then Article with headline "ots" and author "Blog author" and url "www.blog.com" is not saved to database because headline is too short
