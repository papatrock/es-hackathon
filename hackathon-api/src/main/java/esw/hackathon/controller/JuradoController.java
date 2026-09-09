package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.JuradoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/jurados")
@RequiredArgsConstructor
@Tag(name="Jurado")
public class JuradoController {

    private final JuradoService service;

    @GetMapping
    public List<JuradoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public JuradoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<JuradoResponse> criar(
            @Valid @RequestBody JuradoRequest request) {

        var response = service.criar(request);

        return ResponseEntity
            .created(URI.create("/api/jurados/" + response.id()))
            .body(response);
    }
}