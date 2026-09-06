package esw.hackathon.repository;
import esw.hackathon.model.Equipe;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface EquipeRepository extends JpaRepository<Equipe, Long> {
    long countByHackathonId(Long hackathonId);

    java.util.List<Equipe> findByHackathonIdOrderById(Long hackathonId);

    boolean existsByParticipantesId(Long participanteId);

    @Query("""
        SELECT e FROM Equipe e WHERE e.id = :id
    """)
    java.util.Optional<Equipe> findLockedById(@Param("id") Long id);
}
