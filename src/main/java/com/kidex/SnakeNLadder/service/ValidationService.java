package com.kidex.SnakeNLadder.service;

import com.kidex.SnakeNLadder.exception.DataAccessException;
import com.kidex.SnakeNLadder.exception.ValidationException;
import com.kidex.SnakeNLadder.reposervice.BoardRepoService;
import com.kidex.SnakeNLadder.request.CreateBoardRequest;
import com.kidex.SnakeNLadder.request.SetupGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ValidationService {

    @Autowired
    private BoardRepoService boardRepoService;

    public void validateBoard(final CreateBoardRequest createBoardRequest)
            throws ValidationException {
        Integer
                dimensionalProduct =
                createBoardRequest.getDimension() * createBoardRequest.getDimension();
        if (!(validateListOfPairs(dimensionalProduct, createBoardRequest.getSnakes())
                && validateListOfPairs(dimensionalProduct, createBoardRequest.getLadders())
                && validateBombs(dimensionalProduct, createBoardRequest.getBombs()))) {
            throw new ValidationException("Invalid Data");
        }
    }

    private boolean validateListOfPairs(final Integer dimensionalProduct,
            final List<List<Long>> val) {
        List<List<Long>>
                invalidData =
                val.stream().filter(pairVal -> pairVal.get(0) <= 1
                        || pairVal.get(0) >= dimensionalProduct || pairVal.get(1) <= 1
                        || pairVal.get(1) >= dimensionalProduct).collect(Collectors.toList());
        return invalidData.isEmpty();
    }

    private boolean validateBombs(final Integer dimensionalProduct, final List<Long> val) {
        List<Long>
                invalidData =
                val.stream().filter(obj -> obj <= 1 && obj >= dimensionalProduct)
                        .collect(Collectors.toList());
        return invalidData.isEmpty();
    }

    public void validateGame(final SetupGame setupGame)
            throws ValidationException {
        try {
            boardRepoService.getBoardsById(setupGame.getBoardId());
        }catch (DataAccessException ex){
            throw new ValidationException("No board exists with given id");
        }
        if(setupGame.getNoOfPlayers()<1){
            throw new ValidationException("Players should be more than one");
        }
    }
}
