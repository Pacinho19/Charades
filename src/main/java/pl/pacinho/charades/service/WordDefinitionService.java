package pl.pacinho.charades.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;
import pl.pacinho.charades.model.definitions.DefinitionDto;
import pl.pacinho.charades.model.definitions.WordInfoDto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordDefinitionService {

    private static final String API_PATH = "https://api.dictionaryapi.dev/api/v2/entries/en/";

    public List<String> getDefinition(String word) {
        try {
            URL url = new URL(API_PATH + word);
            HttpURLConnection uc = getHttpUrlConnection(url);
            if (uc.getResponseCode() != 200) return Collections.emptyList();
            return parseResponse(uc.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<String> parseResponse(InputStream inputStream) throws IOException {
        WordInfoDto wordInfoDto = getResponse(inputStream);
        return wordInfoDto.getMeanings()
                .stream()
                .map(m -> m.getDefinitions().stream()
                        .map(DefinitionDto::getDefinition)
                        .toList())
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    private WordInfoDto getResponse(InputStream inputStream) throws IOException {
        String json = readInputStream(inputStream);
        Gson gson = new GsonBuilder().create();
        Type type = new TypeToken<List<WordInfoDto>>() {
        }.getType();
        return ((List<WordInfoDto>) gson.fromJson(json, type)).get(0);
    }

    private String readInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
            return in.lines()
                    .collect(Collectors.joining());
        }
    }

    private HttpURLConnection getHttpUrlConnection(URL url) throws Exception {
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setDoOutput(true);
        uc.setRequestMethod("GET");
        return uc;
    }
}
