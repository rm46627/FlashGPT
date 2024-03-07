package pl.ug.edu.fiszkord.flashcards;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.ug.edu.fiszkord.subjects.Subject;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "_deck")
public class Deck {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "deck", fetch = FetchType.EAGER)
    private List<Flashcard> flashcards;

    @ManyToOne
    @JsonBackReference
    private Subject subject;

}
