package com.kidex.SnakeNLadder.response;

import com.kidex.SnakeNLadder.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class BaseMessageResponse {

    private Status status;
}
