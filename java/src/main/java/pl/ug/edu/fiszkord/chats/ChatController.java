package pl.ug.edu.fiszkord.chats;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService service;

    @MessageMapping("/{subjectId}")
    @SendTo("/topic/{subjectId}")
    public Message sendMessage(
            @DestinationVariable String subjectId,
            @Payload Message message
    ) {
        return service.save(message, subjectId);
    }
}