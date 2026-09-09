package esw.hackathon.service;

import esw.hackathon.dto.ApiDtos.ClassificacaoResponse;
import esw.hackathon.model.Avaliacao;
import esw.hackathon.repository.AvaliacaoRepository;
import esw.hackathon.repository.HackathonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassificacaoService {

    private final AvaliacaoRepository avaliacoes;
    private final HackathonRepository hackathons;

    public List<ClassificacaoResponse> calcular(Long hackathonId) {
        if (!hackathons.existsById(hackathonId)) {
            throw new ResponseStatusException(
                NOT_FOUND,
                "Hackathon não encontrado"
            );
        }

        var avaliacoesPorProjeto = avaliacoes
                                    .findByJuradoHackathonId(hackathonId)
                                    .stream()
                                    .collect(Collectors.groupingBy(
                                        Avaliacao::getProjetoId
                                    ));

        var resultados = avaliacoesPorProjeto.entrySet()
                        .stream()
                        .map(entry -> {
                            var projetoId = entry.getKey();
                            var lista = entry.getValue();
            
                            var media = lista.stream()
                                .mapToDouble(Avaliacao::getNota)
                                .average()
                                .orElse(0.0);
            
                            return new ResultadoProjeto(
                                projetoId,
                                media,
                                (long) lista.size()
                            );
                        })
                        .sorted(
                            Comparator
                                .comparingDouble(ResultadoProjeto::notaFinal)
                                .reversed()
                                .thenComparing(ResultadoProjeto::projetoId)
                        )
                        .toList();

        var classificacao =
            new ArrayList<ClassificacaoResponse>();

        for (int i = 0; i < resultados.size(); ++i) {
            var resultado = resultados.get(i);

            classificacao.add(
                new ClassificacaoResponse(
                    i + 1,
                    resultado.projetoId(),
                    resultado.notaFinal(),
                    resultado.quantidadeAvaliacoes()
                )
            );
        }

        return classificacao;
    }

    private record ResultadoProjeto(
        Long projetoId,
        Double notaFinal,
        Long quantidadeAvaliacoes
    ) {}
}