package pl.pacinho.charades.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GuessOutputDto {

    private String text;
    private boolean correct;


}
