package pl.ug.edu.fiszkord.groups;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findByCode(String code);

    List<Group> findByMembers_Id(Integer id);



}