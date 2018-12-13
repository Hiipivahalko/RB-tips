Feature: as a user I can delete exist tip

    Scenario: User can delete tip successfully from program
        Given User can see five tips in program
        When User delete one tip
        Then User can see 4 tip in program