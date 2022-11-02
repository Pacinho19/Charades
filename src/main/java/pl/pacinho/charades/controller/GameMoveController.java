package pl.pacinho.charades.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import pl.pacinho.charades.model.GuessWordDto;
import pl.pacinho.charades.model.JoinGameDto;
import pl.pacinho.charades.model.GameActionDto;
import pl.pacinho.charades.service.GameService;

@RequiredArgsConstructor
@Controller
public class GameMoveController {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GameService gameService;

    @MessageMapping("/join")
    public void join(@Payload GameActionDto gameActionDto, Authentication authentication) {
        gameService.joinGame(authentication.getName(), gameActionDto.getGameId());
        simpMessagingTemplate.convertAndSend("/join/" + gameActionDto.getGameId(),
                new JoinGameDto(authentication.getName(), gameService.checkStartGame(gameActionDto.getGameId())));
    }

    @MessageMapping("/guess")
    public void start(@Payload GuessWordDto gameActionDto, Authentication authentication) {
        simpMessagingTemplate.convertAndSend("/guess/" + gameActionDto.getGameId(), gameService.guess(gameActionDto.getGameId(), authentication.getName(), gameActionDto.getWord()));
    }
}