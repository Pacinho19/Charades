package pl.pacinho.charades.config;

import pl.pacinho.charades.model.enums.GameType;

public class UIConfig {
    public static final String HOME = "/charades";
    public static final String GAMES = HOME + "/games";
    private static final String NEW_GAME = GAMES + "/new";

    public static final String NEW_GAME_CLASSIC = NEW_GAME + "/classic";
    public static final String NEW_GAME_CANVAS = NEW_GAME + "/canvas";
    public static final String GAME_ROOM = GAMES + "/{gameId}/room";
    public static final String GAME_PAGE = GAMES + "/{gameId}";
    public static final String PLAYERS = GAME_ROOM + "/players";
    public static final String CANVAS = GAME_PAGE + "/canvas";
}