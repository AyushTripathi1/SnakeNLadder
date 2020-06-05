package com.kidex.SnakeNLadder.reposervice;

import com.kidex.SnakeNLadder.exception.DataAccessException;
import com.kidex.SnakeNLadder.model.dto.BoardDTO;
import com.kidex.SnakeNLadder.repository.BoardRepository;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Log4j2
@Service
public class BoardRepoService extends AbstractRepoService<BoardDTO, Integer> {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public JpaRepository<BoardDTO, Integer> getRepo() {
        return boardRepository;
    }

    public BoardDTO getBoardsById(@NonNull final Integer boardId) {

        log.info("DB query to fetch the boards");
        BoardDTO boardDTO = null;

        try {
            boardDTO = boardRepository.getBoardDTOById(boardId);
        } catch (Exception ex) {
            throw new DataAccessException("Error while fetching data from DB", ex);
        }
        if (Objects.isNull(boardDTO)) {
            throw new DataAccessException("No Board found for board id" + boardId);
        }
        return boardDTO;
    }

}
