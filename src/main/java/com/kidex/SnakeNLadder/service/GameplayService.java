package com.kidex.SnakeNLadder.service;

import com.kidex.SnakeNLadder.exception.ValidationException;
import com.kidex.SnakeNLadder.model.dto.BoardDTO;
import com.kidex.SnakeNLadder.model.dto.GameDTO;
import com.kidex.SnakeNLadder.model.dto.PlayerDTO;
import com.kidex.SnakeNLadder.reposervice.GameRepoService;
import com.kidex.SnakeNLadder.reposervice.PlayerRepoService;
import com.kidex.SnakeNLadder.request.PlayGameRequest;
import com.kidex.SnakeNLadder.response.GameplayResponse;
import com.kidex.SnakeNLadder.response.PlayerWiseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Integer.sum;

@Service
public class GameplayService {

    @Autowired
    private GameRepoService gameRepoService;

    @Autowired
    private PlayerRepoService playerRepoService;

    public GameplayResponse makeMove(final PlayGameRequest playGameRequest) throws Exception {

        GameDTO gameDTO = getGameData(playGameRequest.getGameRefId());
        Map<Integer, PlayerDTO>
                playerDTOHashMapPlayerNumberWise =
                makePlayersMapOfCurrentGame(gameDTO);
        Map<Integer, List<PlayerDTO>>
                playerDTOHashMapPositionWise =
                makePlayersMapOfCurrentGamePositionWise(gameDTO);
        PlayerDTO
                currentPlayer =
                playerDTOHashMapPlayerNumberWise.get(playGameRequest.getPlayerInGameId());
        if (checkPlayerExists(playGameRequest.getPlayerInGameId(),
                playerDTOHashMapPlayerNumberWise)) {
            return generateResponse(gameDTO, playerDTOHashMapPositionWise, currentPlayer,
                    playGameRequest);
        } else {
            return GameplayResponse.builder().build();
        }
    }

    private GameDTO getGameData(final Integer gameRefId) throws Exception {
        GameDTO gameDTO;
        try {
            gameDTO = gameRepoService.getGameDTOById(gameRefId);
        } catch (Exception ex) {
            throw new ValidationException(ex.getMessage());
        }
        return gameDTO;
    }

    private boolean checkPlayerExists(final Integer playerInGameId,
            final Map<Integer, PlayerDTO> playerDTOMap) {
        return playerDTOMap.containsKey(playerInGameId);
    }

    private Map<Integer, PlayerDTO> makePlayersMapOfCurrentGame(final GameDTO gameDTO) {
        return gameDTO.getPlayerDTOList().stream()
                .collect(Collectors.toMap(PlayerDTO::getPlayerNumber, PlayerDTO -> PlayerDTO));
    }

    private Map<Integer, List<PlayerDTO>> makePlayersMapOfCurrentGamePositionWise(
            final GameDTO gameDTO) {
        Map<Integer, List<PlayerDTO>> listMap = new HashMap<>();
        for (PlayerDTO playerDTO : gameDTO.getPlayerDTOList().stream().filter(PlayerDTO::getActive)
                .collect(Collectors.toList())) {
            if (listMap.containsKey(playerDTO.getCurrentPosition())) {
                listMap.get(playerDTO.getCurrentPosition()).add(playerDTO);
            } else {
                listMap.put(playerDTO.getCurrentPosition(), new ArrayList<PlayerDTO>() {{
                    add(playerDTO);
                }});
            }
        }

        return listMap;
    }

    private boolean startMoveCheck(final Integer steps, final PlayerDTO playerDTO) {
        return steps.equals(6) && !playerDTO.getActive();
    }

    private boolean notStartMove(final Integer steps, final PlayerDTO playerDTO) {
        return !playerDTO.getCurrentPosition().equals(0) && steps >= 1 && steps <= 6;
    }

    private GameplayResponse generateResponse(final GameDTO gameDTO,
            final Map<Integer, List<PlayerDTO>> positionWisePlayerMap,
            final PlayerDTO currentPlayer, final PlayGameRequest playGameRequest) {
        List<PlayerWiseResponse> playerWiseResponseList = new ArrayList<>();
        if (stepToSkip(currentPlayer) || startMoveCheck(playGameRequest.getSteps(),
                currentPlayer)) {
            //add db update call to make current player next move to set true
            PlayerWiseResponse playerWiseResponse = new PlayerWiseResponse();
            playerWiseResponse.setNewPosition(currentPlayer.getCurrentPosition());
            playerWiseResponse.setPlayerNumber(currentPlayer.getPlayerNumber());
            playerWiseResponse.setTracedThrough(new ArrayList<Integer>() {{
                add(currentPlayer.getCurrentPosition());
            }});
            playerWiseResponseList.add(playerWiseResponse);
            currentPlayer.setActive(Boolean.TRUE);
            currentPlayer.setNextMove(Boolean.TRUE);
            playerRepoService.getRepo().save(currentPlayer);
        } else {
            PlayerWiseResponse playerWiseResponse = new PlayerWiseResponse();
            List<Integer> stack = new ArrayList<>();
            stack.add(currentPlayer.getCurrentPosition());
            Integer
                    newPosition =
                    sum(currentPlayer.getCurrentPosition(), playGameRequest.getSteps());
            stack.add(newPosition);
            //check ladder/snake present to update current position
            Integer finalPos = updatePosSnakeLadder(currentPlayer, newPosition, gameDTO);
            if (!finalPos.equals(newPosition)) {
                stack.add(finalPos);
            }
            //final update coincinding objects
            playerWiseResponseList
                    .addAll(updateCoincidingObject(positionWisePlayerMap, finalPos, gameDTO));
            playerWiseResponse.setPlayerNumber(currentPlayer.getPlayerNumber());
            playerWiseResponse.setNewPosition(finalPos);
            playerWiseResponse.setTracedThrough(stack);
            playerWiseResponseList.add(playerWiseResponse);
        }
        return mapResponse(gameDTO.getId(), playerWiseResponseList);
    }

    private boolean stepToSkip(final PlayerDTO currentPlayer) {
        return !currentPlayer.getNextMove();
    }

    private GameplayResponse mapResponse(final Integer gameRefId,
            final List<PlayerWiseResponse> playerWiseResponses) {
        return GameplayResponse.builder().playingRefNum(gameRefId)
                .playerWiseResponseList(playerWiseResponses).build();
    }

    private void checkBomb(final PlayerDTO playerDTO, final Integer position) {
        playerDTO.setNextMove(Boolean.FALSE);
    }

    private Integer updatePosSnakeLadder(final PlayerDTO playerDTO, final Integer position,
            final GameDTO gameDTO) {
        BoardDTO boardDTO = gameDTO.getBoardDto();
        Map<Integer, Integer>
                laddersStartingPoint =
                boardDTO.getLadders().stream().collect(
                        Collectors.toMap(ladder -> ladder.get(0), ladder -> ladder.get(1)));
        Map<Integer, Integer>
                snakesStartingPoint =
                boardDTO.getSnakes().stream()
                        .collect(Collectors.toMap(snake -> snake.get(0), snake -> snake.get(1)));
        Integer finalPosition = position;
        while (laddersStartingPoint.containsKey(finalPosition)) {
            finalPosition = laddersStartingPoint.get(finalPosition);
        }
        while (snakesStartingPoint.containsKey(finalPosition)) {
            finalPosition = snakesStartingPoint.get(finalPosition);
        }
        //check if bomb present and update next move of user to false
        if (boardDTO.getBombs().contains(finalPosition)) {
            checkBomb(playerDTO, finalPosition);
        } else {
            playerDTO.setCurrentPosition(finalPosition);
        }
        playerRepoService.getRepo().save(playerDTO);
        return finalPosition;
    }

    private List<PlayerWiseResponse> updateCoincidingObject(
            final Map<Integer, List<PlayerDTO>> playerDTOMap, final Integer position,
            final GameDTO gameDTO) {
        List<PlayerWiseResponse> playerWiseResponses = new ArrayList<>();
        if (playerDTOMap.containsKey(position)) {
            for (PlayerDTO updatePlayer : playerDTOMap.get(position)) {
                playerWiseResponses
                        .add(updatePlayerPositionToNearestSnakeTail(updatePlayer, gameDTO));
            }
        }
        return playerWiseResponses;
    }

    private PlayerWiseResponse updatePlayerPositionToNearestSnakeTail(final PlayerDTO newPlayer,
            final GameDTO gameDTO) {
        PlayerWiseResponse playerWiseResponse = new PlayerWiseResponse();
        List<Integer>
                snakesStartingPoint =
                gameDTO.getBoardDto().getSnakes().stream().map(snake -> snake.get(0))
                        .collect(Collectors.toList());
        Map<Integer, Integer>
                snakesMap =
                gameDTO.getBoardDto().getSnakes().stream()
                        .collect(Collectors.toMap(snake -> snake.get(0), snake -> snake.get(1)));
        List<Integer> stack = new ArrayList<>();
        stack.add(newPlayer.getCurrentPosition());
        Collections.sort(snakesStartingPoint);
        int newPos = 0;
        while (snakesStartingPoint.get(newPos) <= newPlayer.getCurrentPosition()) {
            newPos += 1;
        }
        stack.add(snakesStartingPoint.get(newPos - 1));
        Integer finalPos = snakesMap.get(snakesStartingPoint.get(newPos - 1));
        stack.add(finalPos);
        newPos = updatePosSnakeLadder(newPlayer, finalPos, gameDTO);
        if (finalPos.intValue() != newPos) {
            stack.add(newPos);
            finalPos = newPos;
        }
        newPlayer.setCurrentPosition(finalPos);
        playerWiseResponse.setTracedThrough(stack);
        playerWiseResponse.setPlayerNumber(newPlayer.getPlayerNumber());
        playerWiseResponse.setNewPosition(finalPos);
        playerRepoService.getRepo().save(newPlayer);
        return playerWiseResponse;
    }
}

