package pl.ug.edu.fiszkord.chats;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {


    List<Message> findTop20BySubject_IdAndDateLessThanOrderByDateDesc(Integer subjectId, Timestamp date);
}

