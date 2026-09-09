package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.ClassificacaoResponse;
import esw.hackathon.service.ClassificacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hackathons/{hackathonId}/classificacao")
@RequiredArgsConstructor
public class ClassificacaoController {

    private final ClassificacaoService service;

    @GetMapping
    public List<ClassificacaoResponse> listar(@PathVariable Long hackathonId) {
        return service.calcular(hackathonId);
    }
}