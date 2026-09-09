package esw.hackathon.service;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.model.Organizador;
import esw.hackathon.repository.OrganizadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrganizadorService {

    private final OrganizadorRepository repository;
    private final UsuarioService usuarios;

    public List<OrganizadorResponse> listar() {
        return repository.findAll(
                    org.springframework.data.domain.Sort.by("id"))
                .stream()
                .map(OrganizadorService::response)
                .toList();
    }

    public OrganizadorResponse buscar(Long id) {
        return response(entity(id));
    }

    @Transactional
    public OrganizadorResponse criar(OrganizadorRequest r) {
        var organizador = new Organizador();

        usuarios.atualizarDados(
            organizador,
            r.nome(),
            r.email(),
            r.senha()
        );

        return response(repository.save(organizador));
    }

    private Organizador entity(Long id) {
        return repository.findById(id)
            .orElseThrow(() ->
                new ResponseStatusException(
                    NOT_FOUND,
                    "Organizador não encontrado"
                )
            );
    }

    public static OrganizadorResponse response(Organizador organizador) {
        return new OrganizadorResponse(
            organizador.getId(),
            organizador.getNome(),
            organizador.getEmail()
        );
    }
}