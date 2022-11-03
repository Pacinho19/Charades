package pl.pacinho.charades.repository;

import org.springframework.stereotype.Repository;
import pl.pacinho.charades.exception.GameNotFoundException;
import pl.pacinho.charades.model.GameDto;
import pl.pacinho.charades.model.PlayerDto;
import pl.pacinho.charades.model.enums.GameStatus;
import pl.pacinho.charades.service.ImageApiService;
import pl.pacinho.charades.service.WordDefinitionService;
import pl.pacinho.charades.service.WordService;

import java.util.*;

@Repository
public class GameRepository {

    private final WordDefinitionService wordDefinitionService;
    private final WordService wordService;
    private final ImageApiService imageApiService;
    private Map<String, GameDto> gameMap;
    private Map<String, String> wordMap;

    public GameRepository(WordDefinitionService wordDefinitionService, WordService wordService, ImageApiService imageApiService) {
        this.wordDefinitionService = wordDefinitionService;
        this.wordService = wordService;
        this.imageApiService = imageApiService;
        gameMap = new HashMap<>();
        wordMap = new HashMap<>();
    }

    public String newGame(String playerName) {
        GameDto game = new GameDto(playerName);
        List<String> definition = new ArrayList<>();
        List<String> images = new ArrayList<>();
        String word = "";
        while (definition.isEmpty() || images.isEmpty()) {
            word = wordService.getRandomWord();
            definition = wordDefinitionService.getDefinition(word);
            images = imageApiService.getImage(word);
        }

        System.out.println("=========" + word + "=========");

        game.setWordLetters(wordService.getWordLetters(word));
        game.setDefinition(definition);
        game.setImages(images);
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

    public GameDto joinGame(String name, String gameId) throws IllegalStateException {
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

    public boolean checkWord(String gameId, String word) {
        String gameWord = wordMap.get(gameId);
        if (gameWord == null)
            throw new GameNotFoundException(gameId);

        return gameWord.equalsIgnoreCase(word);
    }
}