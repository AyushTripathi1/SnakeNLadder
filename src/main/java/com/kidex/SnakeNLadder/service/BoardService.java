package com.kidex.SnakeNLadder.service;

import com.kidex.SnakeNLadder.exception.DataAccessException;
import com.kidex.SnakeNLadder.exception.SavingDataException;
import com.kidex.SnakeNLadder.exception.ValidationException;
import com.kidex.SnakeNLadder.mapper.BoardDetailsMapper;
import com.kidex.SnakeNLadder.mapper.GameMapper;
import com.kidex.SnakeNLadder.model.dto.BoardDTO;
import com.kidex.SnakeNLadder.model.dto.GameDTO;
import com.kidex.SnakeNLadder.model.dto.PlayerDTO;
import com.kidex.SnakeNLadder.reposervice.BoardRepoService;
import com.kidex.SnakeNLadder.reposervice.GameRepoService;
import com.kidex.SnakeNLadder.request.CreateBoardRequest;
import com.kidex.SnakeNLadder.request.SetupGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class BoardService {

    @Autowired
    private BoardRepoService boardReposervice;

    @Autowired
    private GameRepoService gameRepoService;

    @Transactional(propagation = Propagation.REQUIRED)
    public String createBoard(final CreateBoardRequest createBoardRequest)
            throws SavingDataException {

        BoardDTO
                boardDTO =
                BoardDetailsMapper.INSTANCE.createBoardRequestToBoardDTO(createBoardRequest);
        try {
            return boardReposervice.getRepo().save(boardDTO).getId().toString();
        } catch (Exception ex) {
            throw new SavingDataException(
                    "Some error occurred while committing data to database: " + ex);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String setupGameOnBoard(final SetupGame setupGame) throws ValidationException,SavingDataException {

        try {
            List<PlayerDTO> playerDTOList = getPlayersDTOS(setupGame);
            BoardDTO boardDTO = boardReposervice.getBoardsById(setupGame.getBoardId());
            GameDTO gameDTO = GameMapper.INSTANCE.setupGameRequestOnBoard(boardDTO, playerDTOList);
            boardDTO.getGameDTOList().add(gameDTO);
            playerDTOList.forEach(player->player.setGameDto(gameDTO));
            return gameRepoService.getRepo().save(gameDTO).getId().toString();

        } catch (DataAccessException ex) {
            throw new ValidationException(
                    "No Board found for board id : " + setupGame.getBoardId());
        } catch (Exception ex) {
            throw new SavingDataException(
                    "Some error occurred while committing data to database: " + ex);
        }
    }

    private List<PlayerDTO> getPlayersDTOS(final SetupGame setupGame) {
        List<PlayerDTO> playerDTOList = new ArrayList<>();
        for (int i = 1; i <= setupGame.getNoOfPlayers(); i++) {
            playerDTOList.add(PlayerDTO.builder().playerNumber(i).build());
        }
        return playerDTOList;
    }
}
