package pl.ug.edu.fiszkord.flashcards;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.edu.fiszkord.groups.Group;
import pl.ug.edu.fiszkord.groups.GroupRepository;
import pl.ug.edu.fiszkord.users.User;
import pl.ug.edu.fiszkord.users.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FlashcardService {

    private final FlashcardRepository flashcardRepository;
    private final DeckRepository deckRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public ResponseEntity<String> addFlashcard(FlashcardRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if user is a member of the group
        Group group = null;
        try {
            group = groupRepository.findById(request.getGroupId()).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
        Hibernate.initialize(group.getMembers());
        if(group.getMembers().stream().noneMatch(u -> u.getUsername().equals(user.getUsername()))){
            return ResponseEntity.status(409).body("User `" + user.getUsername() + "` is not a member of `" + group.getName() + "` group.");
        }

        Deck deck = null;
        try {
            deck = deckRepository.findById(request.getDeckId()).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

        var flashcard = Flashcard.builder()
                .front(request.getFront())
                .back(request.getBack())
                .deck(deck)
                .build();
        try {
            flashcardRepository.save(flashcard);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.status(202).body("Flashcard added to `" + deck.getName() +"` deck.");
    }

    @Transactional
    public ResponseEntity<List<Flashcard>> getFlashcards(Integer deckId, Integer groupId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if user is a member of the group
        Group group = null;
        try {
            group = groupRepository.findById(groupId).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).body(null);
        }
        Hibernate.initialize(group.getMembers());
        if(group.getMembers().stream().noneMatch(u -> u.getUsername().equals(user.getUsername()))){
            return ResponseEntity.status(409).body(null);
        }

        Deck deck = null;
        try {
            deck = deckRepository.findById(deckId).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).build();
        }

        List<Flashcard> flashcards;
        try {
            flashcards = flashcardRepository.findAllFlashcardsByDeckId(deckId).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).build();
        }
        return new ResponseEntity<>(flashcards, HttpStatus.OK);
    }
}
