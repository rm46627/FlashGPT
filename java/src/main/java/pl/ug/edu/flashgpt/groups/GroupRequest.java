package pl.ug.edu.flashgpt.groups;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class GroupRequest {
    private String name;
    private String code;
}
