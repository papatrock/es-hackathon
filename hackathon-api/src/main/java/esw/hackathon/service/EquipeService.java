package esw.hackathon.service;
import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.model.*;
import esw.hackathon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class EquipeService {
    private final EquipeRepository repository;
    private final HackathonRepository hackathons;
    private final ParticipanteRepository participantes;

    public List<EquipeResponse> listar() {
        return repository.findAll(org.springframework.data.domain.Sort.by("id"))
            .stream()
            .map(this::response)
            .toList();
    }

    public List<EquipeResponse> listarPorHackathon(Long id) {
        if (!hackathons.existsById(id))
            throw new ResponseStatusException(NOT_FOUND, "Hackathon não encontrado");
        return repository.findByHackathonIdOrderById(id)
            .stream()
            .map(this::response)
            .toList();
    }

    public EquipeResponse buscar(Long id) {
        return response(repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Equipe não encontrada")));
    }

    @Transactional
    public EquipeResponse criar(Long hackathonId, EquipeRequest r) {
        var h = hackathons.findLockedById(hackathonId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Hackathon não encontrado"));
        if (repository.countByHackathonId(hackathonId) >= h.getMaxEquipes())
            throw new ResponseStatusException(BAD_REQUEST, "Limite de equipes atingido");
        var e = new Equipe();
        e.setHackathon(h);
        return salvar(e, r);
    }

    @Transactional
    public EquipeResponse atualizar(Long id, EquipeRequest r) {
        return salvar(locked(id), r);
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(locked(id));
    }

    @Transactional
    public EquipeResponse adicionar(Long id, Long participanteId) {
        var e = locked(id);
        var p = participantes.findById(participanteId)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Participante não encontrado"));
        if (e.getParticipantes()
            .stream()
            .anyMatch(item -> item.getId().equals(participanteId)))
            throw new ResponseStatusException(BAD_REQUEST, "Participante já pertence à equipe");
        e.getParticipantes().add(p);
        return response(e);
    }

    @Transactional
    public void remover(Long id, Long participanteId) {
        var e = locked(id);
        var p = e.getParticipantes()
            .stream()
            .filter(item -> item.getId().equals(participanteId))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Participante não pertence à equipe"));
        if (e.getParticipantes().size() == 1)
            throw new ResponseStatusException(BAD_REQUEST, "Equipe deve ter pelo menos um participante");
        e.getParticipantes().remove(p);
    }

    private Equipe locked(Long id) {
        return repository.findLockedById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Equipe não encontrada"));
    }

    private EquipeResponse salvar(Equipe e, EquipeRequest r) {
        var membros = participantes.findAllById(r.participanteIds());
        if (membros.size() != r.participanteIds().size())
            throw new ResponseStatusException(NOT_FOUND, "Um ou mais participantes não foram encontrados");
        e.setNome(r.nome().strip());
        e.getParticipantes().clear();
        e.getParticipantes().addAll(membros);
        return response(repository.save(e));
    }

    private EquipeResponse response(Equipe e) {
        return new EquipeResponse(e.getId(), e.getNome(), e.getHackathon().getId(), e.getParticipantes()
            .stream()
            .sorted(Comparator.comparing(Participante::getId))
            .map(ParticipanteService::response)
            .toList());
    }
}
