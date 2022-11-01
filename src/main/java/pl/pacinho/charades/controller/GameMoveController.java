package pl.pacinho.charades.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import pl.pacinho.charades.model.GameDto;
import pl.pacinho.charades.model.JoinGameDto;
import pl.pacinho.charades.model.MoveDto;
import pl.pacinho.charades.service.GameService;

@RequiredArgsConstructor
@Controller
public class GameMoveController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameService gameService;

    @MessageMapping("/move")
    public void move(@Payload MoveDto moveDto, Authentication authentication) {
        simpMessagingTemplate.convertAndSend("/game/" + moveDto.getGameId(), authentication.getName());
    }

    @MessageMapping("/join")
    public void join(@Payload MoveDto moveDto, Authentication authentication) {
        gameService.joinGame(authentication.getName(), moveDto.getGameId());
        simpMessagingTemplate.convertAndSend("/join/" + moveDto.getGameId(),
                new JoinGameDto(authentication.getName(), gameService.checkStartGame(moveDto.getGameId())));
    }

    @MessageMapping("/start")
    public void start(@Payload MoveDto moveDto, Authentication authentication) {
        simpMessagingTemplate.convertAndSend("/game/" + moveDto.getGameId(), authentication.getName());
    }
}