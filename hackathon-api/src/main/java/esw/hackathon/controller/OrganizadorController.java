package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.OrganizadorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/organizadores")
@RequiredArgsConstructor
public class OrganizadorController {

    private final OrganizadorService service;

    @GetMapping
    public List<OrganizadorResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public OrganizadorResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<OrganizadorResponse> criar(
            @Valid @RequestBody OrganizadorRequest request) {

        var response = service.criar(request);

        return ResponseEntity
            .created(URI.create("/api/organizadores/" + response.id()))
            .body(response);
    }
}