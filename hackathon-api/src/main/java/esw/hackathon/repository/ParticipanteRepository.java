package esw.hackathon.repository;
import esw.hackathon.model.Participante;
import org.springframework.data.jpa.repository.*;

public interface ParticipanteRepository extends JpaRepository<Participante, Long> {

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByEmail(String email);
}
