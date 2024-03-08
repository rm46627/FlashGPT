package pl.ug.edu.flashgpt.flashcards;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Message {
    private String role;
    private String content;
}
