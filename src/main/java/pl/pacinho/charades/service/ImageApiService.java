package pl.pacinho.charades.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.pacinho.charades.config.ImageApiConfig;
import pl.pacinho.charades.model.WordImageDto;
import pl.pacinho.charades.utils.HttpConnectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ImageApiService {

    public List<String> getImage(String text) {
        WordImageDto wordInfoDto = getResponse(text);
        if (wordInfoDto == null) return Collections.emptyList();
        return wordInfoDto.getHits()
                .stream().limit(10)
                .map(i -> i.getWebformatURL())
                .toList();
    }

    private WordImageDto getResponse(String word) {
        try {
            URL url = new URL(getUrl(word));
            HttpURLConnection uc = HttpConnectionUtils.getHttpUrlConnection(url);
            if (uc.getResponseCode() != 200) return null;
            return getResponse(uc.getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private WordImageDto getResponse(InputStream inputStream) throws IOException {
        String json = HttpConnectionUtils.readInputStream(inputStream);
        Gson gson = new GsonBuilder().create();
        return (gson.fromJson(json, WordImageDto.class));
    }

    public String getUrl(String word) {
        return "https://pixabay.com/api/?key=" + ImageApiConfig.API_KEY + "&q=" + word;
    }
}
