package pl.ug.edu.fiszkord.flashcards;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Integer> {

    @Query(value = "SELECT * FROM _flashcard WHERE deck_id = ?1", nativeQuery = true)
    Optional<List<Flashcard>> findAllFlashcardsByDeckId(Integer deckId);
}
