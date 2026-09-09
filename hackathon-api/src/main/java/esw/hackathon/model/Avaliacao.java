package esw.hackathon.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double nota;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String feedback;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "jurado_id", nullable = false)
    private Jurado jurado;

    /* // Depois mudar pra esse quando tiver Projeto
    * @ManyToOne(fetch = FetchType.LAZY, optional = false)
    * @JoinColumn(name = "projeto_id", nullable = false)
    * private Projeto projeto;
    */
    @Column(name = "projeto_id", nullable = false)
    private Long projetoId;
}