package com.kidex.SnakeNLadder.repository;

import com.kidex.SnakeNLadder.model.dto.BoardDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardDTO, Integer> {

    BoardDTO getBoardDTOById(final Integer boardId);
}
