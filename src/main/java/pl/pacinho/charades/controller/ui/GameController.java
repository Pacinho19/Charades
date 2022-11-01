package pl.pacinho.charades.controller.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.pacinho.charades.config.UIConfig;
import pl.pacinho.charades.model.GameDto;
import pl.pacinho.charades.service.GameService;

@RequiredArgsConstructor
@Controller
public class GameController {

    private final GameService gameService;

    @GetMapping(UIConfig.HOME)
    public String gameHome(Model model) {
        return "home";
    }

    @PostMapping(UIConfig.GAMES)
    public String availableGames(Model model) {
        model.addAttribute("games", gameService.getAvailableGames());
        return "available-games :: availableGamesFrag";
    }

    @PostMapping(UIConfig.NEW_GAME)
    public String newGame(Model model, Authentication authentication) {
        try {
            return "redirect:" + UIConfig.GAMES + "/" + gameService.newGame(authentication.getName());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return gameHome(model);
        }
    }

    @GetMapping(UIConfig.GAME_PAGE)
    public String gamePage(@PathVariable(value = "gameId") String gameId, Model model, Authentication authentication) {
        try {
            GameDto game = gameService.findById(gameId);
            model.addAttribute("game", game);
            model.addAttribute("joinGame", gameService.canJoin(game, authentication.getName()));
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return gameHome(model);
        }
        return "game-room";
    }

    @PostMapping(UIConfig.PLAYERS)
    public String players(@PathVariable(value = "gameId") String gameId, Model model) {
        GameDto game = gameService.findById(gameId);
        model.addAttribute("game", game);
        return "game-players :: gamePlayersFrag";
    }


}