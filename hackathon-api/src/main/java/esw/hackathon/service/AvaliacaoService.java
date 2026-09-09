package esw.hackathon.service;

import esw.hackathon.dto.ApiDtos.AvaliacaoRequest;
import esw.hackathon.dto.ApiDtos.AvaliacaoResponse;
import esw.hackathon.model.Avaliacao;
import esw.hackathon.model.Projeto;
import esw.hackathon.repository.AvaliacaoRepository;
import esw.hackathon.repository.JuradoRepository;
import esw.hackathon.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AvaliacaoService {

    private final AvaliacaoRepository repository;
    private final JuradoRepository jurados;
    private final ProjetoRepository projetos;

    public List<AvaliacaoResponse> listar() {
        return repository.findAll(
                org.springframework.data.domain.Sort.by("id"))
            .stream()
            .map(AvaliacaoService::response)
            .toList();
    }

    public AvaliacaoResponse buscar(Long id) {
        return response(entity(id));
    }

    public List<AvaliacaoResponse> listarPorProjeto(
            Long projetoId) {

        return repository
            .findByProjetoIdOrderById(projetoId)
            .stream()
            .map(AvaliacaoService::response)
            .toList();
    }

    public List<AvaliacaoResponse> listarPorJurado(
            Long juradoId) {

        if (!jurados.existsById(juradoId)) {
            throw new ResponseStatusException(
                NOT_FOUND,
                "Jurado não encontrado"
            );
        }

        return repository
            .findByJuradoIdOrderById(juradoId)
            .stream()
            .map(AvaliacaoService::response)
            .toList();
    }

    @Transactional
    public AvaliacaoResponse criar(AvaliacaoRequest r) {
        var jurado = jurados.findById(r.juradoId())
                        .orElseThrow(() ->
                            new ResponseStatusException(
                                NOT_FOUND,
                                "Jurado não encontrado"
                            )
                        );

        if ( repository.existsByJuradoIdAndProjetoId(jurado.getId(), r.projetoId()) ) {
            throw new ResponseStatusException(
                CONFLICT,
                "Jurado já avaliou este projeto"
            );
        }
        Projeto projeto = projetos.findById(r.projetoId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Projeto não encontrado"));

        var avaliacao = new Avaliacao();  
        avaliacao.setProjeto(projeto);  
        avaliacao.setJurado(jurado);
        avaliacao.setNota(r.nota());
        avaliacao.setFeedback(r.feedback().strip());

        return response(repository.save(avaliacao));
    }

    private Avaliacao entity(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    NOT_FOUND,
                    "Avaliação não encontrada"
                )
            );
    }

    public static AvaliacaoResponse response(Avaliacao a) {
        return new AvaliacaoResponse(
            a.getId(),
            a.getProjeto().getId(),
            a.getJurado().getId(),
            a.getNota(),
            a.getFeedback()
        );
    }
}