package com.kidex.SnakeNLadder.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SetupGame {

    @JsonProperty("noOfPlayers")
    private Integer noOfPlayers;

    @JsonProperty("boardId")
    private Integer boardId;

}
