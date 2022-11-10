package pl.pacinho.charades.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum GameType {

    CLASSIC("game"),
    CANVAS("game-canvas");

    @Getter
    private final String gamePage;
}
