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
public class ProjetoService {
    private final ProjetoRepository repository;
    private final EquipeRepository equipes;

    public List<ProjetoResponse> listar() {
        return repository.findAll(org.springframework.data.domain.Sort.by("id"))
            .stream()
            .map(this::response)
            .toList();
    }

    public ProjetoResponse buscarPorEquipe(Long id) {
        if (!equipes.existsById(id))
            throw new ResponseStatusException(NOT_FOUND, "Equipe não encontrada");
        return response(repository.findByEquipeId(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Projeto não encontrado para essa equipe")));
    }

    public ProjetoResponse buscar(Long id) {
        return response(entity(id));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(entity(id));
    }

    @Transactional
    public ProjetoResponse criar(Long equipeId, ProjetoRequest r) {
        var equipe = equipes.findLockedById(equipeId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Equipe não encontrada"));

        if (repository.existsByEquipeId(equipeId)) {
            throw new ResponseStatusException(CONFLICT, "Equipe já possui um projeto");
        }

        var projeto = new Projeto();
        projeto.setEquipe(equipe);
        projeto.setTitulo(r.titulo());
        projeto.setDescricao(r.descricao());
        projeto.setAreaTematica(r.areaTematica());

        return response(repository.save(projeto));
    }

    @Transactional
    public ProjetoResponse atualizar(Long id, ProjetoRequest r) {
        Projeto projeto = entity(id);
        projeto.setTitulo(r.titulo());
        projeto.setDescricao(r.descricao());
        projeto.setAreaTematica(r.areaTematica());
        return response(repository.save(projeto));
    }

    private Projeto entity(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    NOT_FOUND,
                    "Projeto não encontrado"
                )
            );
    }

    public ProjetoResponse response(Projeto p) {
        return new ProjetoResponse(
            p.getId(), 
            p.getTitulo(), 
            p.getDescricao(),
            p.getAreaTematica(),
            p.getEquipe().getId()
        );
    }
}
