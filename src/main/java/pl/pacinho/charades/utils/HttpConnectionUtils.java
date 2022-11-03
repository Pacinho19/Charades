package pl.pacinho.charades.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.stream.Collectors;

public class HttpConnectionUtils {

    public static String readInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
            return in.lines()
                    .collect(Collectors.joining());
        }
    }

    public static HttpURLConnection getHttpUrlConnection(URL url) throws Exception {
        HttpURLConnection uc = (HttpURLConnection) url.openConnection();
        uc.setDoOutput(true);
        uc.setRequestMethod("GET");
        return uc;
    }
}
