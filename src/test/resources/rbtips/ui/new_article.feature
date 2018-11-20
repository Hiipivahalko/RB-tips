Feature: as a user I want to be able to add new article tip
    
    Scenario: creating new article successfully with valid inputs
        Given new tip command is given
        When a valid headline "otsikko" and valid author "tekijä" and valid url "www.blog.fi"
        Then artile, which headline is "otsikko" and author is "tekijä" and url is "www.blog.fi" is found


    Scenario: creating new article fails because invalid headline
        Given new tip command is given, and invalid headline "ots" with valid author "tekijä" and url "www.blog.fi are insert"
        Then Article is not saved to database and with invalid headline "ots" and valid author "tekijä", url "www.blog.fi" input