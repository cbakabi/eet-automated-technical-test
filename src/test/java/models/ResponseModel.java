package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class ResponseModel {

  @JsonProperty("playlists")
  private List<Playlists> playlists = new ArrayList<>();

  @JsonProperty("id")
  private String id;

  @JsonProperty("name")
  private String name;

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  public static class Content {

    private String id;
    private String name;
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  public static class Playlists {

    private String id;
    private String name;
    private List<Content> content = new ArrayList<>();

  }


}
