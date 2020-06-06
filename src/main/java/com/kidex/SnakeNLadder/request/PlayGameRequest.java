package com.kidex.SnakeNLadder.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayGameRequest {

    @JsonProperty("playingRefNum")
    private Integer gameRefId;

    @JsonProperty("playerNumber")
    private Integer playerInGameId;

    @JsonProperty("diceRolled")
    private Integer steps;

}
