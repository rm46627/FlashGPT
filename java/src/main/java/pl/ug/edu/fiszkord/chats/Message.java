package pl.ug.edu.fiszkord.chats;

import jakarta.persistence.*;
import lombok.*;
import pl.ug.edu.fiszkord.subjects.Subject;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name="_message")
public class Message {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="subject", nullable=false)
    private Subject subject;

    private String content;
    private String sender;
    private java.sql.Timestamp date;

}
