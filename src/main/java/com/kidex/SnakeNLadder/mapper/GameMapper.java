package com.kidex.SnakeNLadder.mapper;

import com.kidex.SnakeNLadder.model.dto.BoardDTO;
import com.kidex.SnakeNLadder.model.dto.GameDTO;
import com.kidex.SnakeNLadder.model.dto.PlayerDTO;
import com.kidex.SnakeNLadder.request.CreateBoardRequest;
import com.kidex.SnakeNLadder.request.SetupGame;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface GameMapper {

    GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);


    GameDTO setupGameRequestOnBoard(final BoardDTO boardDto, final List<PlayerDTO> playerDTOList);

}
