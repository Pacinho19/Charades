package pl.pacinho.charades.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@PropertySource("classpath:image-api.properties")
@Configuration
public class ImageApiConfig {

    public final String API_KEY;
    public ImageApiConfig(@Value("${image.apiKey}") String API_KEY) {
        this.API_KEY = API_KEY;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeHolderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}