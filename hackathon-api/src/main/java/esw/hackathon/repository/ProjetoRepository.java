package esw.hackathon.repository;
import java.util.Optional;
import esw.hackathon.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {

    boolean existsByEquipeId(Long equipeId);

    Optional<Projeto> findByEquipeId(Long equipeId);

}