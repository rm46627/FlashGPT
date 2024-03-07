package pl.ug.edu.fiszkord.chats;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.ug.edu.fiszkord.subjects.Subject;
import pl.ug.edu.fiszkord.subjects.SubjectRepository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final SubjectRepository subjectRepository;

    public Message save(Message msg, String subjectId) {
        Timestamp ts = Timestamp.from(Instant.now());
        Optional<Subject> subject = subjectRepository.findById(Integer.parseInt(subjectId));
        if (subject.isEmpty()) {
            return msg;
        }
        Message newMsg = Message.builder().date(ts)
                .content(msg.getContent())
                .sender(msg.getSender())
                .subject(subject.get())
                .build();
        messageRepository.save(newMsg);
        return newMsg;
    }

    public List<Message> getSubjectMessages(String subjectId, Timestamp fromDate) {
        return messageRepository.findTop20BySubject_IdAndDateLessThanOrderByDateDesc(Integer.parseInt(subjectId), fromDate);
    }
}
