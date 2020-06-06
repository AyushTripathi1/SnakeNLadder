package com.kidex.SnakeNLadder.repository;

import com.kidex.SnakeNLadder.model.dto.GameDTO;
import com.kidex.SnakeNLadder.model.dto.PlayerDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerDTO, Integer> {

    PlayerDTO getPlayerDTOById(final Integer playerId);
}
