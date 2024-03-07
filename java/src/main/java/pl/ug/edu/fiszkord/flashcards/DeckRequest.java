package pl.ug.edu.fiszkord.flashcards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class DeckRequest {
    private String name;
    private Integer groupId;
    private Integer subjectId;
}
