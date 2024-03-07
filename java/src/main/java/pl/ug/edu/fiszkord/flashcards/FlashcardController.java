package pl.ug.edu.fiszkord.flashcards;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class FlashcardController {

    private final FlashcardService flashcardService;

    @PostMapping("/create-flashcard")
    public ResponseEntity<String> addFlashcard(
            @RequestBody FlashcardRequest request,
            Principal connectedUser
    ) {
        return flashcardService.addFlashcard(request, connectedUser);
    }

    @GetMapping("/deck-flashcards")
    public ResponseEntity<List<Flashcard>> getFlashcards(
            @RequestParam Integer groupId,
            @RequestParam Integer deckId,
            Principal connectedUser
    ) {
        return flashcardService.getFlashcards(deckId, groupId, connectedUser);
    }
}
