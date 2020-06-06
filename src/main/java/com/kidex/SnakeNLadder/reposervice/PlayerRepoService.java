package com.kidex.SnakeNLadder.reposervice;

import com.kidex.SnakeNLadder.exception.DataAccessException;
import com.kidex.SnakeNLadder.model.dto.GameDTO;
import com.kidex.SnakeNLadder.model.dto.PlayerDTO;
import com.kidex.SnakeNLadder.repository.GameRepository;
import com.kidex.SnakeNLadder.repository.PlayerRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@Service
public class PlayerRepoService extends AbstractRepoService<PlayerDTO, Integer> {

    @Autowired
    private PlayerRepository playerRepository;

    @Override
    public JpaRepository<PlayerDTO, Integer> getRepo() {
        return playerRepository;
    }

}
