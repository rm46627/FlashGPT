package pl.ug.edu.fiszkord.flashcards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class FlashcardRequest {
    private String front;
    private String back;
    private Integer groupId;
    private Integer deckId;
}
