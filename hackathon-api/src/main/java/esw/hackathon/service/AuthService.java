package esw.hackathon.service;

import esw.hackathon.dto.ApiDtos.LoginRequest;
import esw.hackathon.dto.ApiDtos.LoginResponse;
import esw.hackathon.repository.UsuarioRepository;
import esw.hackathon.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwt;

    public LoginResponse login(LoginRequest request) {
        var email = request.email()
            .strip()
            .toLowerCase(Locale.ROOT);

        var usuario = usuarios.findByEmail(email)
            .orElseThrow(() ->
                new ResponseStatusException(
                    UNAUTHORIZED,
                    "E-mail ou senha inválidos"
                )
            );

        if (!passwordEncoder.matches(
                request.senha(),
                usuario.getSenha())) {

            throw new ResponseStatusException(
                UNAUTHORIZED,
                "E-mail ou senha inválidos"
            );
        }

        return new LoginResponse(
            jwt.gerar(usuario)
        );
    }
}