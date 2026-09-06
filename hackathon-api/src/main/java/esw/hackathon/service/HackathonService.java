package esw.hackathon.service;
import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.model.Hackathon;
import esw.hackathon.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import static org.springframework.http.HttpStatus.*;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly=true)
public class HackathonService {
    private final HackathonRepository repository;
    private final EquipeRepository equipes;

    public List<HackathonResponse> listar() {
        return repository.findAll(org.springframework.data.domain.Sort.by("id"))
            .stream()
            .map(this::response)
            .toList();
    }

    public List<HackathonResponse> disponiveis() {
        return repository.findDisponiveis(LocalDate.now())
            .stream()
            .map(this::response)
            .toList();
    }

    public HackathonResponse buscar(Long id) {
        return response(repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Hackathon não encontrado")));
    }

    @Transactional
    public HackathonResponse criar(HackathonRequest request) {
        return salvar(new Hackathon(), request);
    }

    @Transactional
    public HackathonResponse atualizar(Long id, HackathonRequest request) {
        var h = locked(id);
        if (request.maxEquipes() < equipes.countByHackathonId(id))
            throw new ResponseStatusException(BAD_REQUEST, "Limite menor que a quantidade de equipes cadastradas");
        return salvar(h, request);
    }

    @Transactional
    public void excluir(Long id) {
        var h = locked(id);
        if (equipes.countByHackathonId(id) > 0)
            throw new ResponseStatusException(CONFLICT, "Exclua as equipes antes de excluir o hackathon");
        repository.delete(h);
    }

    private Hackathon locked(Long id) {
        return repository.findLockedById(id)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Hackathon não encontrado"));
    }

    private HackathonResponse salvar(Hackathon h, HackathonRequest r) {
        if (r.dataTermino().isBefore(r.dataInicio()))
            throw new ResponseStatusException(BAD_REQUEST, "Data de término deve ser igual ou posterior à data de início");
        h.setNome(r.nome().strip());
        h.setDataInicio(r.dataInicio());
        h.setDataTermino(r.dataTermino());
        h.setMaxEquipes(r.maxEquipes());
        return response(repository.save(h));
    }

    private HackathonResponse response(Hackathon h) {
        return new HackathonResponse(h.getId(), h.getNome(), h.getDataInicio(), h.getDataTermino(), h.getMaxEquipes());
    }
}
