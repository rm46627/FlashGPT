package pl.ug.edu.fiszkord.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ug.edu.fiszkord.subjects.Subject;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping
    public ResponseEntity<?> changePassword(
          @RequestBody ChangePasswordRequest request,
          Principal connectedUser
    ) {
        service.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser(
            @RequestParam(value = "id", required=false) String id,
            Principal connectedUser
    ) {
        if (id==null) {
            return service.getCurrentUser(connectedUser);
        }
        return service.getUser(id);
    }
}
