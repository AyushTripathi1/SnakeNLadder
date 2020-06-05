package com.kidex.SnakeNLadder.repository;

import com.kidex.SnakeNLadder.model.dto.BoardDTO;
import com.kidex.SnakeNLadder.model.dto.GameDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<GameDTO, Integer> {

    GameDTO getGameDTOById(final Integer gameId);
}
