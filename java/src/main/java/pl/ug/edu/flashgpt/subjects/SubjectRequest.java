package pl.ug.edu.flashgpt.subjects;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class SubjectRequest {
    private Integer groupId;
    private String name;
}
