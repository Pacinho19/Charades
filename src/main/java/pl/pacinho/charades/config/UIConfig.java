package pl.pacinho.charades.config;

public class UIConfig {
    public static final String HOME = "/charades";
    public static final String GAMES = HOME + "/games";
    public static final String NEW_GAME = GAMES + "/new";
    public static final String GAME_PAGE = GAMES + "/{gameId}";
    public static final String JOIN_TO_GAME = GAME_PAGE + "/join";
    public static final String PLAYERS = GAME_PAGE + "/players";
    public static final String START_GAME = GAME_PAGE + "/start-game";
}