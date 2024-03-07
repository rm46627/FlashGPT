package pl.ug.edu.fiszkord.groups;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class GroupRequest {
    private String name;
    private String code;
}
