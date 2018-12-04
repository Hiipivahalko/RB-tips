Feature: As a user I can see all reading tips I have saved

    Scenario: User has not saved any tips so UI shows empty list
      Given Database is initialized
      Then User sees all tips from database in the GUI and the list size is 0

    Scenario: User saves one reading tip and can see it on the list
      Given Database is initialized
      When User adds one tip to database
      Then User sees all tips from database in the GUI and the list size is 1