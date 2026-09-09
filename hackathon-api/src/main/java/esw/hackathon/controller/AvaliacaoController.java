package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.AvaliacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/avaliacoes")
@RequiredArgsConstructor
@Tag(name="Avaliações")
public class AvaliacaoController {

    private final AvaliacaoService service;

    @PreAuthorize("hasAuthority('ROLE_ORGANIZADOR')")
    @GetMapping
    public List<AvaliacaoResponse> listar() {
        return service.listar();
    }

    @PreAuthorize("hasAuthority('ROLE_ORGANIZADOR')")
    @GetMapping("/{id}")
    public AvaliacaoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ORGANIZADOR')")
    @GetMapping("/projeto/{projetoId}")
    public List<AvaliacaoResponse> listarPorProjeto(
            @PathVariable Long projetoId) {

        return service.listarPorProjeto(projetoId);
    }

    @PreAuthorize("hasAuthority('ROLE_ORGANIZADOR')")
    @GetMapping("/jurado/{juradoId}")
    public List<AvaliacaoResponse> listarPorJurado(
            @PathVariable Long juradoId) {

        return service.listarPorJurado(juradoId);
    }


    @PreAuthorize("hasAuthority('ROLE_JURADO')")
    @PostMapping
    public ResponseEntity<AvaliacaoResponse> criar(
            @Valid @RequestBody AvaliacaoRequest request) {

        var response = service.criar(request);

        return ResponseEntity
            .created(URI.create("/api/avaliacoes/" + response.id()))
            .body(response);
    }
}