package esw.hackathon.config;

import esw.hackathon.model.Organizador;
import esw.hackathon.repository.OrganizadorRepository;
import esw.hackathon.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final OrganizadorRepository organizadores;
    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        criarOrganizadorInicial();
    }

    private void criarOrganizadorInicial() {
        var email = "admin@hackathon.com";

        if (usuarios.existsByEmail(email)) {
            return;
        }

        var organizador = new Organizador();

        organizador.setNome("Administrador");
        organizador.setEmail(email);
        organizador.setSenha(
            passwordEncoder.encode("admin123")
        );

        organizadores.save(organizador);
    }
}