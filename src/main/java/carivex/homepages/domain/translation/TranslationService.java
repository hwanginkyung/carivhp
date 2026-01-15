package carivex.homepages.domain.translation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TranslationService {

    private final boolean enabled;
    private final String endpoint;
    private final String apiKey;
    private final RestTemplate restTemplate;

    public TranslationService(@Value("${app.translation.enabled:false}") boolean enabled,
                              @Value("${app.translation.endpoint:https://libretranslate.de/translate}") String endpoint,
                              @Value("${app.translation.api-key:}") String apiKey,
                              RestTemplateBuilder restTemplateBuilder) {
        this.enabled = enabled;
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.restTemplate = restTemplateBuilder.build();
    }

    public String translateToEnglishText(String text) {
        return translate(text, "text");
    }

    public String translateToEnglishHtml(String text) {
        return translate(text, "html");
    }

    private String translate(String text, String format) {
        if (!enabled || text == null || text.isBlank()) {
            return null;
        }

        Map<String, Object> payload = new HashMap<>();
        payload.put("q", text);
        payload.put("source", "auto");
        payload.put("target", "en");
        payload.put("format", format);
        if (apiKey != null && !apiKey.isBlank()) {
            payload.put("api_key", apiKey);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            TranslationResponse response = restTemplate.postForObject(
                    endpoint,
                    new HttpEntity<>(payload, headers),
                    TranslationResponse.class
            );
            if (response == null || response.translatedText() == null || response.translatedText().isBlank()) {
                return null;
            }
            return response.translatedText();
        } catch (RestClientException ex) {
            log.warn("Translation failed for endpoint {}: {}", endpoint, ex.getMessage());
            return null;
        }
    }

    private record TranslationResponse(String translatedText) {
    }
}
