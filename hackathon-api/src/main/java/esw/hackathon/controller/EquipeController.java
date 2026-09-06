package esw.hackathon.controller;
import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.EquipeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="Equipe")
public class EquipeController {
    private final EquipeService service;

    @GetMapping("/api/equipes")
    public List<EquipeResponse> listar() {
        return service.listar();
    }

    @GetMapping("/api/equipes/{id}")
    public EquipeResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping("/api/hackathons/{hackathonId}/equipes")
    public ResponseEntity<EquipeResponse> criar(@PathVariable Long hackathonId, @Valid @RequestBody EquipeRequest request) {
        var response = service.criar(hackathonId, request);
        return ResponseEntity.created(URI.create("/api/equipes/" + response.id())).body(response);
    }

    @GetMapping("/api/hackathons/{hackathonId}/equipes")
    public List<EquipeResponse> porHackathon(@PathVariable Long hackathonId) {
        return service.listarPorHackathon(hackathonId);
    }

    @PostMapping("/api/equipes/{id}/participantes/{participanteId}")
    public EquipeResponse adicionar(
        @PathVariable Long id,
        @PathVariable Long participanteId) {
    return service.adicionar(id, participanteId);
    }

    @DeleteMapping("/api/equipes/{id}/participantes/{participanteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id, @PathVariable Long participanteId) {
        service.remover(id, participanteId);
    }

    @PutMapping("/api/equipes/{id}")
    public EquipeResponse atualizar(@PathVariable Long id, @Valid @RequestBody EquipeRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/api/equipes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

}
