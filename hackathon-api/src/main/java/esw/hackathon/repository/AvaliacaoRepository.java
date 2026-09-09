package esw.hackathon.repository;

import esw.hackathon.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

    boolean existsByJuradoIdAndProjetoId(Long juradoId, Long projetoId);

    List<Avaliacao> findByProjetoIdOrderById(Long projetoId);

    List<Avaliacao> findByJuradoIdOrderById(Long juradoId);
}