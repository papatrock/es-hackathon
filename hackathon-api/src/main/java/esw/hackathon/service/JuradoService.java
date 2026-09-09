package esw.hackathon.service;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.model.Jurado;
import esw.hackathon.repository.HackathonRepository;
import esw.hackathon.repository.JuradoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JuradoService {

    private final JuradoRepository repository;
    private final HackathonRepository hackathons;
    private final UsuarioService usuarios;

    public List<JuradoResponse> listar() {
        return repository.findAll(
                org.springframework.data.domain.Sort.by("id"))
            .stream()
            .map(JuradoService::response)
            .toList();
    }

    public JuradoResponse buscar(Long id) {
        return response(entity(id));
    }

    @Transactional
    public JuradoResponse criar(JuradoRequest r) {
        var hackathon = hackathons.findById(r.hackathonId())
            .orElseThrow(() ->
                new ResponseStatusException(
                    NOT_FOUND,
                    "Hackathon não encontrado"
                )
            );

        var jurado = new Jurado();

        usuarios.atualizarDados(
            jurado,
            r.nome(),
            r.email(),
            r.senha()
        );

        jurado.setHackathon(hackathon);

        return response(repository.save(jurado));
    }

    private Jurado entity(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    NOT_FOUND,
                    "Jurado não encontrado"
                )
            );
    }

    public static JuradoResponse response(Jurado jurado) {
        return new JuradoResponse(
            jurado.getId(),
            jurado.getNome(),
            jurado.getEmail(),
            jurado.getHackathon().getId()
        );
    }
}