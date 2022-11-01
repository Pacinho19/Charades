package pl.pacinho.charades.config;

public class UIConfig {
    public static final String HOME = "/charades";
    public static final String GAMES = HOME + "/games";
    public static final String NEW_GAME = GAMES + "/new";
    public static final String GAME_ROOM = GAMES + "/{gameId}/room";
    public static final String GAME_PAGE = GAMES + "/{gameId}";
    public static final String PLAYERS = GAME_ROOM + "/players";
}