package pl.ug.edu.flashgpt.groups;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import pl.ug.edu.flashgpt.users.User;
import pl.ug.edu.flashgpt.users.UserDTO;

import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
public class GroupDTO implements Serializable {

    private Integer id;

    private List<UserDTO> admins;

    private List<UserDTO> members;

    private String name;

    private String code;
}
