package pl.ug.edu.fiszkord.flashcards;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/deck")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class DeckController {

    private final DeckService service;

    @PostMapping("/create")
    public ResponseEntity<String> createDeck(
            @RequestBody DeckRequest request,
            Principal connectedUser
    ) {
        return service.createDeck(request, connectedUser);
    }

    @GetMapping("/subject-decks")
    public ResponseEntity<List<Deck>> getDecks(
            @RequestParam Integer subjectId,
            Principal connectedUser
    ) {
        return service.getDecks(subjectId, connectedUser);
    }

}
