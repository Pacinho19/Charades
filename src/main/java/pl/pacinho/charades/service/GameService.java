package pl.pacinho.charades.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.charades.exception.GameNotFoundException;
import pl.pacinho.charades.model.GameDto;
import pl.pacinho.charades.model.GuessOutputDto;
import pl.pacinho.charades.model.enums.GameStatus;
import pl.pacinho.charades.repository.GameRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GameService {

    private final GameRepository gameRepository;

    public List<GameDto> getAvailableGames() {
        return gameRepository.getAvailableGames();
    }

    public String newGame(String name) {
        List<GameDto> activeGames = getAvailableGames();
        if (activeGames.size() >= 10)
            throw new IllegalStateException("Cannot create new Game! Active game count : " + activeGames.size());
        return gameRepository.newGame(name);
    }

    public GameDto findById(String gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException(gameId));
    }

    public void joinGame(String name, String gameId) {
        GameDto game = gameRepository.joinGame(name, gameId);
        game.setStatus(GameStatus.IN_PROGRESS);
    }

    public boolean checkStartGame(String gameId) {
        return findById(gameId).getPlayers().size() == GameDto.MAX_PLAYERS;
    }

    public boolean canJoin(GameDto game, String name) {
        return game.getPlayers().size() < GameDto.MAX_PLAYERS && game.getPlayers().stream().noneMatch(p -> p.getName().equals(name));
    }

    public GuessOutputDto guess(String gameId, String playerName, String word) {
        if (word == null) return new GuessOutputDto(getGuessText(playerName, null), false);

        if (checkWord(gameId, word)) {
            findById(gameId).setStatus(GameStatus.FINISHED);
            return new GuessOutputDto(getWinnerText(playerName, word), true);
        }

        return new GuessOutputDto(getGuessText(playerName, word), false);

    }

    private boolean checkWord(String gameId, String word) {
        return gameRepository.checkWord(gameId, word);
    }

    private String getWinnerText(String playerName, String word) {
        return playerName + " win the game ! Correct word: " + word;
    }

    private String getGuessText(String playerName, String word) {
        return playerName + " : " + word;
    }

    public boolean checkPlayGame(String name, GameDto game) {
        return game.getPlayers()
                .stream()
                .anyMatch(p -> p.getName().equals(name));
    }
}