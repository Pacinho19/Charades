package pl.pacinho.charades.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JoinGameDto {

    private String name;
    private boolean start;
    private String exception;
}