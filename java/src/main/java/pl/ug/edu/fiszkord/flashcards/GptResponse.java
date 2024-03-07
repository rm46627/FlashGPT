package pl.ug.edu.fiszkord.flashcards;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class GptResponse {

    private List<Choice> choices;
    @Getter
    @Setter
    @AllArgsConstructor
    @Builder
    @NoArgsConstructor
    public static class Choice {
        private int index;
        private Message message;
    }
}