package pl.ug.edu.fiszkord.subjects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.ug.edu.fiszkord.groups.Group;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "_subject")
public class Subject {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String name;

    @ManyToOne
    @JsonBackReference
    private Group group;


}
