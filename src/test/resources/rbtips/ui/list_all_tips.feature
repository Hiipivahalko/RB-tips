Feature: List all tips from database

    Scenario: List all tips successfully
      Given Database is initialized
      When User adds one tip to database
      Then User sees all tips from database in the GUI and the list size is "1"