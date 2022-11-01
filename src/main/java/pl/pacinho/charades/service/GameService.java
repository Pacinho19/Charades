package pl.pacinho.charades.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.charades.exception.GameNotFoundException;
import pl.pacinho.charades.model.GameDto;
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
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new GameNotFoundException(gameId));
    }

    public boolean checkOpenGame(String gameId, String name) {
        GameDto game = findById(gameId);
        return game.getPlayers()
                .stream()
                .anyMatch(p -> p.getName() != null && p.getName().equals(name));
    }

    public void joinGame(String name, String gameId) {
        GameDto game = gameRepository.joinGame(name, gameId);
        game.setStatus(GameStatus.IN_PROGRESS);
    }

    public boolean checkStartGame(String gameId) {
        return findById(gameId)
                .getPlayers().size() == GameDto.MAX_PLAYERS;
    }

    public boolean canJoin(GameDto game, String name) {
        return game.getPlayers().size() < GameDto.MAX_PLAYERS
                && game.getPlayers().stream().noneMatch(p -> p.getName().equals(name));
    }
}