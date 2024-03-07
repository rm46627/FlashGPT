package pl.ug.edu.fiszkord.users;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class UserDTO implements Serializable {
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
}
