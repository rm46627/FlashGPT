package pl.ug.edu.fiszkord.chats;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.ug.edu.fiszkord.groups.GroupRequest;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService service;

    @GetMapping("/")
    public List<Message> getMessages(
            @RequestParam(value = "timestamp", required=false) String timestamp,
            @RequestParam("subjectId") String subjectId
    ) {
        if (timestamp==null) {
            return service.getSubjectMessages(subjectId, Timestamp.from(Instant.now()));
        }
        return service.getSubjectMessages(subjectId, Timestamp.valueOf(timestamp));
    }
}