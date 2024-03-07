package pl.ug.edu.fiszkord.users;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.ug.edu.fiszkord.groups.GroupRepository;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final GroupRepository groupRepository;
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password.");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Passwords are not the same.");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    //@Transactional
    public String getName(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return user.getFirstname()+" "+user.getLastname();
    }

    public ResponseEntity<UserDTO>  getCurrentUser(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return ResponseEntity.status(200).body(
                UserDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build());
    }

    public ResponseEntity<UserDTO> getUser(String userId) {
        var user = repository.findById(Integer.parseInt(userId));
        if (user.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        User foundUser = user.get();
        return ResponseEntity.status(200).body(
                UserDTO.builder()
                        .id(foundUser.getId())
                        .email(foundUser.getEmail())
                        .firstname(foundUser.getFirstname())
                        .lastname(foundUser.getLastname())
                        .build());
    }
}
