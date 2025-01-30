package hr.products.ms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Configuration class for WebClient bean.
 */
@Configuration
public class WebClientConfiguration {

    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}
