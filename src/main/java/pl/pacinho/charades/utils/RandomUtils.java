package pl.pacinho.charades.utils;

import java.util.List;
import java.util.Random;

public class RandomUtils {
    public static String randomString(List<String> strings, int size) {
        Random rand = new Random();
        return strings.get(rand.nextInt(strings.size()));
    }
}
