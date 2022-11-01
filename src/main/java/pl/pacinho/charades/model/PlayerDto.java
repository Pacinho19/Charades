package pl.pacinho.charades.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerDto {

    private String name;

    public PlayerDto(String name) {
        this.name = name;
    }
}