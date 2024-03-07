package pl.ug.edu.fiszkord.flashcards;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.edu.fiszkord.groups.*;
import pl.ug.edu.fiszkord.subjects.Subject;
import pl.ug.edu.fiszkord.subjects.SubjectRepository;
import pl.ug.edu.fiszkord.users.User;
import pl.ug.edu.fiszkord.users.UserRepository;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;
    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;

    @Transactional
    public ResponseEntity<String> createDeck(DeckRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if user can create deck in group
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

        // find subject and create deck
        Subject subject = null;
        try {
            subject = subjectRepository.findById(request.getSubjectId()).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

        var deck = Deck.builder()
                .name(request.getName())
                .subject(subject)
                .build();
        try {
            deckRepository.save(deck);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.status(201).body("deck `" + deck.getName() + "` created");
    }

    public ResponseEntity<List<Deck>> getDecks(Integer subjectId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Subject subject = null;
        try {
            subject = subjectRepository.findById(subjectId).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).build();
        }

        List<Deck> decks;
        try {
            decks = deckRepository.findAllDecksBySubjectId(subject.getId()).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).build();
        }
        return new ResponseEntity<>(decks, HttpStatus.OK);
    }


}
