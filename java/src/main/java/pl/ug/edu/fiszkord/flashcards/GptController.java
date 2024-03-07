package pl.ug.edu.fiszkord.flashcards;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/gpt")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class GptController {

//    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.url}")
    private String apiUrl;

    @GetMapping("/flashcard-hint")
    public ResponseEntity<String> chat(@RequestParam String groupName, @RequestParam String userPrompt) {
        String prompt = "Podaj wyjaśnienie zagadnienia z kategorii `" + groupName
                + "'. Konieczne jest, by długość twojej odpowiedzi nie przekraczała 400 znaków." +
                " Nie opisuj co oznacza podana wcześniej kategoria," +
                " tylko weź ją pod uwagę wyjaśniając następujące zagadnienie: " + userPrompt;

        GptRequest request = new GptRequest(model, prompt);
        GptResponse response = restTemplate.postForObject(apiUrl, request, GptResponse.class);

        if (response == null || response.getChoices() == null || response.getChoices().isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        // return content of message
        return new ResponseEntity<>(response.getChoices().get(0).getMessage().getContent(), HttpStatus.OK);
    }
}