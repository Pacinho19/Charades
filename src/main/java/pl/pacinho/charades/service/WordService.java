package pl.pacinho.charades.service;

import org.springframework.stereotype.Service;
import pl.pacinho.charades.utils.FileUtils;
import pl.pacinho.charades.utils.RandomUtils;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class WordService {

    private final String WORDS_DICTIONARY_PATH = "src\\main\\resources\\words.txt";
    private List<String> words;

    public WordService() {
        words = FileUtils.read(WORDS_DICTIONARY_PATH);
    }

    public String getRandomWord() {
        return RandomUtils.randomString(words, words.size());
    }

    public List<String> getWordLetters(String word) {
        return IntStream.rangeClosed(1, word.length())
                .boxed()
                .map(i -> "_")
                .toList();
    }
}
