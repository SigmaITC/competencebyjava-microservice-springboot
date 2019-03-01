Feature: Scenarios for creating, reading, updating and deleting users

  Scenario: Read all users

    Given REST URL <"/users">
    When GET request is sent
    Then response status should be <200>
    And response body should match

    """
    {
      "users": [
        {
          "id": "8390159a-87b3-4364-9202-8d0cd73ee06b",
          "name": "Chris"
        },
        {
          "id": "c95dc295-1aa7-4ec2-8007-afee43b31b16",
          "name": "Kevin"
        }
      ]
    }
    """

  Scenario: Read a specific user

    Given REST URL <"/users/c95dc295-1aa7-4ec2-8007-afee43b31b16">
    When GET request is sent
    Then response status should be <200>
    And response body should match

    """
    {
      "users": [
        {
          "id": "c95dc295-1aa7-4ec2-8007-afee43b31b16",
          "name": "Kevin"
        }
      ]
    }
    """