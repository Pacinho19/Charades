package pl.pacinho.charades.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class WordImageDto {

    private List<ImageDto> hits;
}
