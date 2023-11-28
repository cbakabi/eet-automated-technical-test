Feature: Verify the playlist corresponds to the registered account type

  Background:
    Given the base URL is "https://a8e38tulbj.execute-api.eu-west-2.amazonaws.com/api/playlists/"

  Scenario: Verify the registry for the Premium account
    When I register for a "premium" account on the streaming platform
    Then the response status code is 200
    And the response body should contain the following playlists:
      | id                                   | name         | content                                                                                                                                  |
      | 4ac7507f-9a99-4b3b-941d-a51c6dd6198e | Featured     | 40463e78-e17f-457a-b5b9-c94aeb2a29db;The Shawshank Redemption, 77ed0926-04fa-44c4-bb94-d2b8f52a12f3;The Godfather                        |
      | f7be7b1f-31d7-4199-b14d-c46223a05279 | Premium      | 2e266ec9-41dc-474c-83e4-3f7ea16a980c;Pulp Fiction, 6ab89efe-20a4-48e3-bc48-45fe9bdf001e;Fight Club                                       |
      | 7ba52fa7-e04d-4457-829f-cb99323ca5b4 | Most watched | 439000c8-5b3d-41cc-bf6b-76c3ee157218;The Dark Knight, 51fe8a24-d806-44ad-98e3-634639205bfb;The Lord of the Rings: The Return of the King |

  Scenario: Verify the registry for the Free account
    When I register for a "free" account on the streaming platform
    Then the response status code is 200
    And the response body should contain the following playlists:
      | id                                   | name         | content                                                                                                                                  |
      | 4ac7507f-9a99-4b3b-941d-a51c6dd6198e | Featured     | 40463e78-e17f-457a-b5b9-c94aeb2a29db;The Shawshank Redemption, 77ed0926-04fa-44c4-bb94-d2b8f52a12f3;The Godfather                        |
      | 7ba52fa7-e04d-4457-829f-cb99323ca5b4 | Most watched | 439000c8-5b3d-41cc-bf6b-76c3ee157218;The Dark Knight, 51fe8a24-d806-44ad-98e3-634639205bfb;The Lord of the Rings: The Return of the King |
    And I confirm a "Premium" playlist is not present


  Scenario: Verify an Unknown account cannot be registered
    Given I register for an "plus" account on the streaming platform
    Then the response status code is 400
    And I receive an "Unknown user type" error message
