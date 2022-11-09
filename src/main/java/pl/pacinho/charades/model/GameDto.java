package pl.pacinho.charades.model;

import lombok.Getter;
import lombok.Setter;
import pl.pacinho.charades.model.enums.GameStatus;
import pl.pacinho.charades.model.enums.GameType;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
public class GameDto {

    public static final int MAX_PLAYERS = 2;
    private String id;
    @Setter
    private GameStatus status;
    private LinkedList<PlayerDto> players;
    private LocalDateTime startTime;
    @Setter
    private String winnerInfo;
    @Setter
    private List<String> wordLetters;
    @Setter
    private List<String> definition;
    @Setter
    private List<String> images;
    private GameType type;

    public GameDto(String player1, GameType type) {
        this.type = type;
        players = new LinkedList<>();
        players.add(new PlayerDto(player1));
        this.id = UUID.randomUUID().toString();
        this.status = GameStatus.NEW;
        this.startTime = LocalDateTime.now();
    }

}