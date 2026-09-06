package esw.hackathon.repository;
import esw.hackathon.model.Hackathon;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

public interface HackathonRepository extends JpaRepository<Hackathon, Long> {

    @Query("""
        SELECT h
        FROM Hackathon h
        WHERE h.id = :id
    """)
    java.util.Optional<Hackathon> findLockedById(@Param("id") Long id);

    @Query("""
        SELECT h
        FROM Hackathon h
        WHERE h.dataTermino >= :hoje
        AND (
            SELECT count(e)
            FROM Equipe e
            WHERE e.hackathon = h
            ) < h.maxEquipes
        order by h.dataInicio, h.id
    """)
    java.util.List<Hackathon> findDisponiveis(@Param("hoje") java.time.LocalDate hoje);
    
    }
