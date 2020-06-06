package com.kidex.SnakeNLadder.mapper;

import com.kidex.SnakeNLadder.model.dto.BoardDTO;
import com.kidex.SnakeNLadder.request.CreateBoardRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface BoardDetailsMapper {

    BoardDetailsMapper INSTANCE = Mappers.getMapper(BoardDetailsMapper.class);

    @Mappings({
            @Mapping(target = "snakes", expression = "java(createBoardRequest.getSnakes().toString())"),
            @Mapping(target = "ladders",  expression = "java(createBoardRequest.getLadders().toString())"),
            @Mapping(target = "bombs",  expression = "java(createBoardRequest.getBombs().toString())")
    })
    BoardDTO createBoardRequestToBoardDTO(final CreateBoardRequest createBoardRequest);

}
