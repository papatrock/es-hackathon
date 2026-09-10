package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.MentoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mentorias")
@RequiredArgsConstructor
@Tag(name="Mentoria")
public class MentoriaController {

    private final MentoriaService service;

    @PreAuthorize("hasAnyAuthority('ROLE_MENTOR', 'ROLE_ORGANIZADOR')")
    @GetMapping
    public List<MentoriaResponse> listar() {
        return service.listar();
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MENTOR', 'ROLE_ORGANIZADOR')")
    @GetMapping("/{id}")
    public MentoriaResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MENTOR', 'ROLE_ORGANIZADOR')")
    @GetMapping("/equipes/{equipeId}")
    public List<MentoriaResponse> listarPorEquipe(
            @PathVariable Long equipeId) {

        return service.listarPorEquipe(equipeId);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_MENTOR', 'ROLE_ORGANIZADOR')")
    @GetMapping("/mentores/{mentorId}")
    public List<MentoriaResponse> listarPorMentor(
            @PathVariable Long mentorId) {

        return service.listarPorMentor(mentorId);
    }


    @PreAuthorize("hasAuthority('ROLE_MENTOR')")
    @PostMapping
    public ResponseEntity<MentoriaResponse> criar(
            @Valid @RequestBody MentoriaRequest request) {

        var response = service.criar(request);

        return ResponseEntity
            .created(URI.create("/api/mentores/" + response.id()))
            .body(response);
    }

    @PreAuthorize("hasAuthority('ROLE_MENTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}