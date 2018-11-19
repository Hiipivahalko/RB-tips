Feature: as a user I want to be able to add new article tip
    
    Scenario: creating new article successfully with valid inputs
        Given new tip command is given
        When a valid headline "otsikko" and valid author "tekijä" and valid url "www.blog.fi"
        Then artile, which headline is "otsikko" and author is "tekijä" and url is "www.blog.fi" is found 