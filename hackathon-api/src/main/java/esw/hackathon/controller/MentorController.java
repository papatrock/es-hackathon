package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.MentorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mentores")
@RequiredArgsConstructor
@Tag(name="Mentor")
public class MentorController {

    private final MentorService service;

    @GetMapping
    public List<MentorResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public MentorResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping("/{usuarioId}")
    public ResponseEntity<MentorResponse> criar(
            @PathVariable Long usuarioId,
            @Valid @RequestBody MentorRequest request) {

        var response = service.criar(request, usuarioId);

        return ResponseEntity
                .created(URI.create("/api/mentores/" + response.id()))
                .body(response);
    }
}