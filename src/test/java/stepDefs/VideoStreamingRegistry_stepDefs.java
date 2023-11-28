package stepDefs;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.ResponseModel;
import models.ResponseModel.Content;
import models.ResponseModel.Playlists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VideoStreamingRegistry_stepDefs {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      VideoStreamingRegistry_stepDefs.class);
  private Response response;


  @Given("the base URL is {string}")
  public void theBaseURLIs(String baseUrl) {
    RestAssured.baseURI = baseUrl;
  }

  @Given("I register for a/an {string} account on the streaming platform")
  public void iRegisterForAAccountOnTheStreamingPlatform(String accountType) {
    response = RestAssured.when().get(accountType);
    LOGGER.info("Request sent");
  }

  @Then("the response status code is {int}")
  public void theResponseStatusCodeIs(int statusCode) {
    assertEquals(statusCode, response.getStatusCode());
    LOGGER.info("Request was successful");
  }

  @Then("the response body should contain the following playlists:")
  public void theResponseBodyShouldContainTheFollowingPlaylists(DataTable dataTable) {
    List<Map<String, String>> expectedPlaylists = dataTable.asMaps(String.class, String.class);

    ResponseModel responseModel = response.getBody().as(ResponseModel.class);
    List<Playlists> playlists = responseModel.getPlaylists();

    for (Map<String, String> expectedPlaylist : expectedPlaylists) {
      String expectedId = expectedPlaylist.get("id");
      Playlists actualPlaylist = findPlaylistById(playlists, expectedId);
      assertEquals(expectedPlaylist.get("name"), actualPlaylist.getName());
      LOGGER.info("Playlist details as expected: " + actualPlaylist.getId() + ", "
          + actualPlaylist.getName());

      List<Content> expectedContent = parseContentString(expectedPlaylist.get("content"));
      List<Content> actualContent = actualPlaylist.getContent();
      LOGGER.info("Items in content as expected: " +
          actualContent.get(0).getName());

      for (int i = 0; i < expectedContent.size(); i++) {
        Content expectedFilm = expectedContent.get(i);
        Content actualFilm = actualContent.get(i);

        assertEquals(expectedFilm.getId(), actualFilm.getId());
        assertEquals(expectedFilm.getName(), actualFilm.getName());
        LOGGER.info(
            "Film information is as expected: " + actualFilm.getId() + ", " + actualFilm.getName());
      }
    }
  }

  @And("I receive an {string} error message")
  public void iReceiveAnErrorMessage(String errorMessage) {
    assertTrue(response.getBody().asString().contains(errorMessage));
    LOGGER.info("Response body received is: " + response.getBody().asString());
  }

  @And("I confirm a {string} playlist is not present")
  public void iConfirmAPlaylistIsNotPresent(String playlistName) {
    assertFalse(response.getBody().asString().contains(playlistName));
  }


  private List<Content> parseContentString(String contentString) {
    String[] filmData = contentString.split(", ");
    List<Content> contentList = new ArrayList<>();
    for (String film : filmData) {
      String[] parts = film.split(";");
      Content content = new Content(parts[0], parts[1]);
      contentList.add(content);
    }
    return contentList;
  }

  private Playlists findPlaylistById(List<Playlists> playlists, String id) {
    return playlists.stream()
        .filter(playlist -> playlist.getId().equals(id))
        .findFirst()
        .orElse(null);
  }
}
