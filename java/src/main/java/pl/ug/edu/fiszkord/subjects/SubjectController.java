package pl.ug.edu.fiszkord.subjects;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/subject")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping("/create-subject")
    public ResponseEntity<String> createSubject(
            @RequestBody SubjectRequest request,
            Principal connectedUser
    ) {
        return subjectService.createSubject(request, connectedUser);
    }

    @GetMapping("/get-subjects")
    public ResponseEntity<List<Subject>> getSubjects(
            @RequestParam String groupId,
            Principal connectedUser
    ) {
        return subjectService.getSubjects(groupId, connectedUser);
    }
}
