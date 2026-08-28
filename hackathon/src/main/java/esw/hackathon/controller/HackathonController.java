package esw.hackathon.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/hackathons")
@Tag(name = "1. Hackathons", description = "Endpoints para gerenciamento das configurações do evento")
public class HackathonController {
    //Exemplo
    @GetMapping("/teste")
    public String ping() {
        return "teste";
    }
}
