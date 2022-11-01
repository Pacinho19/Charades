package pl.pacinho.charades.repository;

import org.springframework.stereotype.Repository;
import pl.pacinho.charades.exception.GameNotFoundException;
import pl.pacinho.charades.model.GameDto;
import pl.pacinho.charades.model.PlayerDto;
import pl.pacinho.charades.model.enums.GameStatus;
import pl.pacinho.charades.service.GameService;
import pl.pacinho.charades.utils.WordUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class GameRepository {

    private Map<String, GameDto> gameMap;
    private Map<String, String> wordMap;

    public GameRepository() {
        gameMap = new HashMap<>();
        wordMap = new HashMap<>();
    }

    public String newGame(String playerName) {
        GameDto game = new GameDto(playerName);
        String word = WordUtils.randomWord();
        game.setWordLetters(WordUtils.parseWord(word));
        gameMap.put(game.getId(), game);
        wordMap.put(game.getId(), word);
        return game.getId();
    }

    public List<GameDto> getAvailableGames() {
        return gameMap.values()
                .stream()
                .filter(game -> game.getStatus() != GameStatus.FINISHED)
                .toList();
    }

    public Optional<GameDto> findById(String gameId) {
        return Optional.ofNullable(gameMap.get(gameId));
    }

    public GameDto joinGame(String name, String gameId) {
        GameDto game = gameMap.get(gameId);
        if (game == null)
            throw new GameNotFoundException(gameId);

        if (game.getStatus() != GameStatus.NEW)
            throw new IllegalStateException("Cannot join to " + gameId + ". Game status : " + game.getStatus());

        if (game.getPlayers().get(0).getName().equals(name))
            throw new IllegalStateException("Game " + gameId + " was created by you!");

        if (game.getPlayers().size() == GameDto.MAX_PLAYERS)
            throw new IllegalStateException("Players limit reached!");

        game.getPlayers().add(new PlayerDto(name));
        return game;
    }
}