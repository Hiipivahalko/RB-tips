Feature: List all tips from database

    Scenario: List all tips successfully
      Given Database is initialized
      When User add one tip to database and execute command list tips
      Then User see all tips from database, count is "1"