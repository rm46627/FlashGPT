package pl.ug.edu.fiszkord.groups;

import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.ug.edu.fiszkord.users.User;
import pl.ug.edu.fiszkord.users.UserDTO;
import pl.ug.edu.fiszkord.users.UserRepository;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    private final UserRepository userRepository;

    public ResponseEntity<String> createGroup(GroupRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        var group = Group.builder()
                .name(request.getName())
                .code(request.getCode())
                .build();
        try {
            groupRepository.save(group);
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(409).build();
        }

        user.getMemberOfGroups().add(group);
        user.getAdminOfGroups().add(group);
        userRepository.save(user);

        return ResponseEntity.status(201).body("Group `" + group.getName() + "` created.");
    }

    @Transactional
    public ResponseEntity<String> joinGroup(GroupRequest request, Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Group group = null;
        try {
            group = groupRepository.findByCode(request.getCode()).orElseThrow();
        } catch (NoSuchElementException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

        Hibernate.initialize(group.getMembers());

        if(group.getMembers().stream().anyMatch(u -> u.getUsername().equals(user.getUsername()))){
            return ResponseEntity.status(409).body("User `" + user.getUsername() + "` is already a member of `" + group.getName() + "` group.");
        }

        user.getMemberOfGroups().add(group);
        userRepository.save(user);

        return ResponseEntity.status(202).body("User `" + user.getUsername() + "` added to `" + group.getName() + "` group.");
    }

    @Transactional
    public ResponseEntity<List<GroupDTO>> getUserGroups(Principal connectedUser) {
        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        List<Group> groups = groupRepository.findByMembers_Id(user.getId());
        List<GroupDTO> groupsDTO = new ArrayList<>();

        for (Group g : groups) {
            Hibernate.initialize(g.getMembers());
            List<UserDTO> members = g.getMembers().stream().map(u -> UserDTO.builder()
                    .email(u.getEmail())
                    .firstname(u.getFirstname())
                    .lastname(u.getLastname())
                    .id(u.getId())
                    .build())
                    .toList();

            Hibernate.initialize(g.getAdmins());
            List<UserDTO> admins = g.getAdmins().stream().map(u -> UserDTO.builder()
                            .email(u.getEmail())
                            .firstname(u.getFirstname())
                            .lastname(u.getLastname())
                            .id(u.getId())
                            .build())
                    .toList();
            groupsDTO.add(GroupDTO.builder()
                    .id(g.getId()).members(members)
                    .code(g.getCode()).name(g.getName())
                    .admins(admins).build());
        }

        return ResponseEntity.status(200).body(groupsDTO);
    }
}
