package esw.hackathon.repository;

import esw.hackathon.model.Jurado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JuradoRepository extends JpaRepository<Jurado, Long> {
    long countByHackathonId(Long hackathonId);
}