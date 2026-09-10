package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.LoginRequest;
import esw.hackathon.dto.ApiDtos.LoginResponse;
import esw.hackathon.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name="Autênticação")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return service.login(request);
    }
}