package com.kidex.SnakeNLadder.reposervice;

import com.kidex.SnakeNLadder.exception.DataAccessException;
import com.kidex.SnakeNLadder.model.dto.BoardDTO;
import com.kidex.SnakeNLadder.model.dto.GameDTO;
import com.kidex.SnakeNLadder.repository.BoardRepository;
import com.kidex.SnakeNLadder.repository.GameRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@Service
public class GameRepoService extends AbstractRepoService<GameDTO, Integer> {

    @Autowired
    private GameRepository gameRepository;

    @Override
    public JpaRepository<GameDTO, Integer> getRepo() {
        return gameRepository;
    }


    public GameDTO getGameDTOById(@NonNull final Integer gameRefId) {

        log.info("DB query to fetch the boards");
        GameDTO gameDTO = null;

        try {
            gameDTO = gameRepository.getGameDTOById(gameRefId);
        } catch (Exception ex) {
            throw new DataAccessException("Error while fetching data from DB", ex);
        }
        if (Objects.isNull(gameDTO)) {
            throw new DataAccessException("No Game found for game id" + gameRefId);
        }
        return gameDTO;
    }
}
