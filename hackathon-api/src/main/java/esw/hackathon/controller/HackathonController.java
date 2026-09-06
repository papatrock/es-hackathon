package esw.hackathon.controller;
import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.service.HackathonService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController @RequiredArgsConstructor
@RequestMapping("/api/hackathons")
@Tag(name="Hackathon")
public class HackathonController {

    private final HackathonService service;

    @GetMapping("") public List<HackathonResponse> listar() {
        return service.listar(); 
    }

    @GetMapping("/{id}")
    public HackathonResponse buscar(@PathVariable Long id) {
        return service.buscar(id); 
    }

    @PostMapping public ResponseEntity<HackathonResponse> criar(@Valid @RequestBody HackathonRequest request)
    {
        var response = service.criar(request);
        return ResponseEntity.created(URI.create("/api/hackathons/" + response.id())).body(response);
    }
    
    @PutMapping("/{id}") public HackathonResponse atualizar(@PathVariable Long id, @Valid @RequestBody HackathonRequest request) {
        return service.atualizar(id, request); 
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir(@PathVariable Long id) {
        service.excluir(id);
    }

    @GetMapping("/disponiveis") public List<HackathonResponse> disponiveis() {
        return service.disponiveis();
    }
}
