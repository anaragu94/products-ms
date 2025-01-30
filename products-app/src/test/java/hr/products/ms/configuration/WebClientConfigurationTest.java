package hr.products.ms.configuration;

import org.springframework.web.reactive.function.client.WebClient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for {@code WebClientConfiguration}.
 */
@Tag("ut")
@ExtendWith(MockitoExtension.class)
class WebClientConfigurationTest {

    private WebClientConfiguration webClientConfiguration;

    @BeforeEach
    void setUp() {
        webClientConfiguration = new WebClientConfiguration();
    }

    @Test
    void testWebClient() {
        WebClient webClient = webClientConfiguration.webClient();
        assertNotNull(webClient);
    }

}