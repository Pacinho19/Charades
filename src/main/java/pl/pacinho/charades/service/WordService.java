package pl.pacinho.charades.service;

import org.springframework.stereotype.Service;
import pl.pacinho.charades.utils.FileUtils;
import pl.pacinho.charades.utils.RandomUtils;

import java.util.List;
import java.util.stream.IntStream;

@Service
public class WordService {

    public final String FILE_NAME = "words.txt";
    private final String WORDS_DICTIONARY_PATH = "src\\main\\resources\\" + FILE_NAME;
    private List<String> words;

    public WordService() {
//        words = FileUtils.read(WORDS_DICTIONARY_PATH);
        words = FileUtils.readFromResources(FILE_NAME);
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