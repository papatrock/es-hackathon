package esw.hackathon.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Participante extends Usuario {

    @ManyToMany(mappedBy = "participantes")
    private Set<Equipe> equipes = new LinkedHashSet<>();
}
