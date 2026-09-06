package esw.hackathon.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Equipe {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id", nullable = false) private Hackathon hackathon;
    @ManyToMany
    @JoinTable(name = "equipe_participante", joinColumns = @JoinColumn(name = "equipe_id"), inverseJoinColumns = @JoinColumn(name = "participante_id"))
    private java.util.Set<Participante> participantes = new java.util.LinkedHashSet<>();
}
