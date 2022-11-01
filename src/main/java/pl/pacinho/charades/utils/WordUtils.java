package pl.pacinho.charades.utils;

import java.util.List;
import java.util.stream.IntStream;

public class WordUtils {
    public static String randomWord() {
        return "Barcelona";
    }

    public static List<String> parseWord(String word) {
        return IntStream.rangeClosed(1, word.length())
                .boxed()
                .map(i -> "_")
                .toList();
    }
}