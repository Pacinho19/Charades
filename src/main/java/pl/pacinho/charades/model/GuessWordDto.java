package pl.pacinho.charades.model;

import lombok.Getter;

@Getter
public class GuessWordDto extends GameActionDto {
    private String word;
}
