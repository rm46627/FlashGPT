package pl.ug.edu.fiszkord.flashcards;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "_flashcard")
public class Flashcard {
    @Id
    @GeneratedValue
    private Integer id;

    private String front;
    @Column(columnDefinition = "TEXT")
    private String back;

    @ManyToOne
    @JsonBackReference
    private Deck deck;
}
