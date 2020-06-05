package com.kidex.SnakeNLadder.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateBoardRequest {

    @JsonProperty("n")
    private Integer dimension;

    @JsonProperty("snakes")
    private List<List<Long>> snakes;

    @JsonProperty("ladders")
    private List<List<Long>> ladders;

    @JsonProperty("bombs")
    private List<Long> bombs;

}
