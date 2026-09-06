package esw.hackathon.service;
import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.model.Participante;
import esw.hackathon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class ParticipanteService {
    private final ParticipanteRepository repository;
    private final EquipeRepository equipes;

    public List<ParticipanteResponse> listar() {
        return repository.findAll(org.springframework.data.domain.Sort.by("id"))
            .stream()
            .map(ParticipanteService::response)
            .toList();
    }

    public ParticipanteResponse buscar(Long id) {
        return response(entity(id));
    }

    @Transactional
    public ParticipanteResponse criar(ParticipanteRequest r) {
        return salvar(new Participante(), r);
    }

    @Transactional
    public ParticipanteResponse atualizar(Long id, ParticipanteRequest r) {
        return salvar(entity(id), r);
    }

    @Transactional
    public void excluir(Long id) {
        var p = entity(id);
        if (equipes.existsByParticipantesId(id))
            throw new ResponseStatusException(CONFLICT, "Remova o participante das equipes antes de excluí-lo");
        repository.delete(p);
    }

    private Participante entity(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Participante não encontrado"));
    }

    private ParticipanteResponse salvar(Participante p, ParticipanteRequest r) {
        var email = r.email().strip().toLowerCase(Locale.ROOT);
        if (p.getId() == null ? repository.existsByEmail(email) : repository.existsByEmailAndIdNot(email, p.getId()))
            throw new ResponseStatusException(CONFLICT, "E-mail já cadastrado");
        p.setNome(r.nome().strip());
        p.setEmail(email);
        return response(repository.save(p));
    }

    public static ParticipanteResponse response(Participante p) {
        return new ParticipanteResponse(p.getId(), p.getNome(), p.getEmail());
    }
}
