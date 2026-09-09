package esw.hackathon.service;

import esw.hackathon.model.Usuario;
import esw.hackathon.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Locale;

import static org.springframework.http.HttpStatus.CONFLICT;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void atualizarDados(Usuario usuario, String nome, String email, String senha) {
        var emailNormalizado = email.strip().toLowerCase(Locale.ROOT);

        if (usuario.getId() == null
                ? repository.existsByEmail(emailNormalizado)
                : repository.existsByEmailAndIdNot(emailNormalizado, usuario.getId())) {
            throw new ResponseStatusException(CONFLICT, "E-mail já cadastrado");
        }

        usuario.setNome(nome.strip());
        usuario.setEmail(emailNormalizado);
        usuario.setSenha(passwordEncoder.encode(senha));
    }
}