package carivex.homepages.domain.translation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private final ObjectMapper objectMapper;

    public TranslationService(@Value("${app.translation.enabled:false}") boolean enabled,
                              @Value("${app.translation.endpoint:https://libretranslate.de/translate}") String endpoint,
                              @Value("${app.translation.api-key:}") String apiKey,
                              RestTemplateBuilder restTemplateBuilder,
                              ObjectMapper objectMapper) {
        this.enabled = enabled;
        this.endpoint = endpoint;
        this.apiKey = apiKey;
        this.restTemplate = restTemplateBuilder.build();
        this.objectMapper = objectMapper;
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
        headers.setAccept(MediaType.parseMediaTypes("application/json"));

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    endpoint,
                    new HttpEntity<>(payload, headers),
                    String.class
            );
            String body = response != null ? response.getBody() : null;
            if (body == null || body.isBlank()) {
                return null;
            }
            return extractTranslatedText(body);
        } catch (RestClientException ex) {
            log.warn("Translation failed for endpoint {}: {}", endpoint, ex.getMessage());
            return null;
        }
    }

    private String extractTranslatedText(String body) {
        try {
            JsonNode root = objectMapper.readTree(body);
            JsonNode translated = root.get("translatedText");
            if (translated == null || translated.asText().isBlank()) {
                return null;
            }
            return translated.asText();
        } catch (Exception ex) {
            log.warn("Translation response parsing failed: {}", ex.getMessage());
            return null;
        }
    }
}
