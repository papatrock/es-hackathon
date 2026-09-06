package esw.hackathon.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Hackathon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;
    
    @Column(nullable = false)
    private java.time.LocalDate dataInicio;

    @Column(nullable = false) 
    private java.time.LocalDate dataTermino;

    @Column(nullable = false)
    private Integer maxEquipes;
}
