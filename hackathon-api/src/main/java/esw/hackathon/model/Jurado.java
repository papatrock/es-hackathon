package esw.hackathon.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Jurado extends Usuario {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "hackathon_id", nullable = false)
    private Hackathon hackathon;
}