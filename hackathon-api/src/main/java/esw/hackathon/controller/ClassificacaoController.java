package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.ClassificacaoResponse;
import esw.hackathon.service.ClassificacaoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hackathons/{hackathonId}/classificacao")
@RequiredArgsConstructor
@Tag(name="Classificação")
public class ClassificacaoController {

    private final ClassificacaoService service;

    @GetMapping
    public List<ClassificacaoResponse> listar(@PathVariable Long hackathonId) {
        return service.calcular(hackathonId);
    }
}