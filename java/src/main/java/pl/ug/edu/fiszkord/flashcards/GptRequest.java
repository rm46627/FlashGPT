package pl.ug.edu.fiszkord.flashcards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class GptRequest {

    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;

    public GptRequest(String model, String prompt) {
        this.model = model;
        this.n = 1;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user", prompt));
    }
}

