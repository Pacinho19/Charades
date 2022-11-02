package pl.pacinho.charades.model.definitions;

import lombok.Getter;

import java.util.List;

@Getter
public class WordInfoDto {
    public String word;
    public List<MeaningDto> meanings;
}
