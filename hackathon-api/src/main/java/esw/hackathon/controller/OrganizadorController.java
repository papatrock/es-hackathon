package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.OrganizadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/organizadores")
@RequiredArgsConstructor
@Tag(name="Organizador")
public class OrganizadorController {

    private final OrganizadorService service;

    @PreAuthorize("hasAuthority('ROLE_ORGANIZADOR')")
    @GetMapping
    public List<OrganizadorResponse> listar() {
        return service.listar();
    }

    @PreAuthorize("hasAuthority('ROLE_ORGANIZADOR')")
    @GetMapping("/{id}")
    public OrganizadorResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ORGANIZADOR')")
    @PostMapping
    public ResponseEntity<OrganizadorResponse> criar(
            @Valid @RequestBody OrganizadorRequest request) {

        var response = service.criar(request);

        return ResponseEntity
            .created(URI.create("/api/organizadores/" + response.id()))
            .body(response);
    }
}