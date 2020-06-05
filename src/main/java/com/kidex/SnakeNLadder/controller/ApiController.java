package com.kidex.SnakeNLadder.controller;

import com.kidex.SnakeNLadder.enums.Status;
import com.kidex.SnakeNLadder.exception.SavingDataException;
import com.kidex.SnakeNLadder.exception.ValidationException;
import com.kidex.SnakeNLadder.request.CreateBoardRequest;
import com.kidex.SnakeNLadder.request.SetupGame;
import com.kidex.SnakeNLadder.response.ServiceResponse;
import com.kidex.SnakeNLadder.response.SetupBoardResponse;
import com.kidex.SnakeNLadder.response.SetupGameResponse;
import com.kidex.SnakeNLadder.service.BoardService;
import com.kidex.SnakeNLadder.service.ValidationService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@Log4j2
public class ApiController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ValidationService validationService;

    @PostMapping("setupBoard")
    public ServiceResponse createBoard(
            @RequestBody @NonNull @Valid final CreateBoardRequest createBoardRequest) {
        String boardId;
        try {
            validationService.validateBoard(createBoardRequest);
            boardId = boardService.createBoard(createBoardRequest);

        } catch (ValidationException ex) {
            return new ServiceResponse<>(SetupBoardResponse.builder().status(Status.FAILURE)
                    .message("Board does not exist").build(), HttpStatus.BAD_REQUEST);
        } catch (SavingDataException ex) {
            return new ServiceResponse<>(SetupBoardResponse.builder().status(Status.FAILURE)
                    .message("Error while creating board. Contact Admin").build(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ServiceResponse<>(
                SetupBoardResponse.builder().status(Status.SUCCESS).boardId(boardId).build(),
                HttpStatus.OK);
    }

    @PostMapping("setup")
    public ServiceResponse setupGame(@RequestBody @NonNull @Valid final SetupGame setupGame) {
        String gameRefId;
        try {
            validationService.validateGame(setupGame);
            gameRefId = boardService.setupGameOnBoard(setupGame);

        } catch (ValidationException ex) {
            return new ServiceResponse<>(SetupGameResponse.builder().status(Status.FAILURE)
                    .message("Board does not exist").build(), HttpStatus.BAD_REQUEST);
        } catch (SavingDataException ex) {
            return new ServiceResponse<>(SetupGameResponse.builder().status(Status.FAILURE)
                    .message("Error while creating board. Contact Admin").build(),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ServiceResponse<>(
                SetupGameResponse.builder().status(Status.SUCCESS).playingRefNum(gameRefId).build(),
                HttpStatus.OK);
    }

}
