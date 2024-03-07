package pl.ug.edu.fiszkord.subjects;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import pl.ug.edu.fiszkord.groups.Group;
import pl.ug.edu.fiszkord.groups.GroupRepository;
import pl.ug.edu.fiszkord.users.User;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final GroupRepository groupRepository;

    public ResponseEntity<String> createSubject(SubjectRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Group group = null;
        try {
            group = groupRepository.findById(request.getGroupId()).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

        var subject = Subject.builder()
                .group(group)
                .name(request.getName())
                .build();

        if(group.getSubjects().stream().anyMatch(s -> s.getName().equals(subject.getName()))){
            return ResponseEntity.status(409).body("There already is subject of name `" + subject.getName() + "` in group `" + group.getName() + "`.");
        }

        try {
            subjectRepository.save(subject);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        }

        return ResponseEntity.status(202).body("Subject `" + subject.getName() + "` added to `" + subject.getGroup().getName() +"` group.");
    }

    public ResponseEntity<List<Subject>> getSubjects(String groupId, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Group group = null;
        try {
            group = groupRepository.findById(Integer.parseInt(groupId)).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).build();
        }

        List<Subject> subjects;
        try {
            subjects = subjectRepository.findAllSubjectsByGroupId(group.getId()).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).build();
        }
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
}
