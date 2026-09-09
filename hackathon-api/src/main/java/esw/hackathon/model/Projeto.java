package esw.hackathon.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Projeto {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 255)
    private String descricao;

    @Column(nullable = false, length = 150)
    private String areaTematica;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "equipe_id", nullable = false) 
    private Equipe equipe;
}
