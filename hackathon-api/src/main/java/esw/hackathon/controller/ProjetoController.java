package esw.hackathon.controller;
import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.ProjetoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name="Projeto")
public class ProjetoController {
    private final ProjetoService service;

    @GetMapping("/api/projetos")
    public List<ProjetoResponse> listar() {
        return service.listar();
    }

    @GetMapping("/api/projetos/{id}")
    public ProjetoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping("/api/equipes/{equipeId}/projetos")
    public ResponseEntity<ProjetoResponse> criar(@PathVariable Long equipeId, @Valid @RequestBody ProjetoRequest request) {
        var response = service.criar(equipeId, request);
        return ResponseEntity.created(URI.create("/api/projetos/" + response.id())).body(response);
    }

    @GetMapping("/api/equipes/{equipeId}/projetos")
    public ProjetoResponse porEquipe(@PathVariable Long equipeId) {
        return service.buscarPorEquipe(equipeId);
    }

    @DeleteMapping("/api/projetos/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

    @PutMapping("/api/projetos/{id}")
    public ProjetoResponse atualizar(@PathVariable Long id, @Valid @RequestBody ProjetoRequest request) {
        return service.atualizar(id, request);
    }

}
