package esw.hackathon.controller;
import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.ParticipanteService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/participantes")
@Tag(name="Participante")
public class ParticipanteController {

    private final ParticipanteService service;

    @GetMapping("")
    public List<ParticipanteResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ParticipanteResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public ResponseEntity<ParticipanteResponse> criar(@Valid @RequestBody ParticipanteRequest request) {
        var response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/participantes/" + response.id())).body(response);
    }

    @PutMapping("/{id}")
    public ParticipanteResponse atualizar(@PathVariable Long id, @Valid @RequestBody ParticipanteRequest request) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

}
