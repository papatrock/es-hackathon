package esw.hackathon.repository;

import esw.hackathon.model.Mentoria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MentoriaRepository extends JpaRepository<Mentoria, Long> {

    boolean existsByMentorIdAndEquipeId(Long mentorId, Long equipeId);

    List<Mentoria> findByEquipeIdOrderById(Long equipeId);

    List<Mentoria> findByMentorIdOrderById(Long mentorId);
}