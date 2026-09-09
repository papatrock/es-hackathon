package esw.hackathon.controller;

import esw.hackathon.dto.ApiDtos.LoginRequest;
import esw.hackathon.dto.ApiDtos.LoginResponse;
import esw.hackathon.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public LoginResponse login(
            @Valid @RequestBody LoginRequest request) {

        return service.login(request);
    }
}