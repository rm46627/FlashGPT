package pl.ug.edu.fiszkord.groups;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.ug.edu.fiszkord.auth.Response;
import pl.ug.edu.fiszkord.subjects.SubjectRequest;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/group")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService service;

    @PostMapping("/create")
    public ResponseEntity<String> createGroup(
            @RequestBody GroupRequest request,
            Principal connectedUser
    ) {
        return service.createGroup(request, connectedUser);
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinGroup(
            @RequestBody GroupRequest request,
            Principal connectedUser
    ) {
        return service.joinGroup(request, connectedUser);
    }

    @GetMapping("/user-groups")
    public ResponseEntity<List<GroupDTO>> getGroups(
            Principal connectedUser
    ) {
        return service.getUserGroups(connectedUser);
    }

}
