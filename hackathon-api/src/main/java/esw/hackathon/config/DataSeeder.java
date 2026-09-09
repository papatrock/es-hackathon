package esw.hackathon.config;

import esw.hackathon.model.Organizador;
import esw.hackathon.repository.OrganizadorRepository;
import esw.hackathon.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final OrganizadorRepository organizadores;
    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.bootstrap.admin.nome}")
    private String nome;

    @Value("${app.bootstrap.admin.email}")
    private String email;

    @Value("${app.bootstrap.admin.password}")
    private String senha;

    @Override
    public void run(String... args) {
        criarOrganizadorInicial();
    }

    private void criarOrganizadorInicial() {
        if (usuarios.existsByEmail(email)) {
            return;
        }

        var organizador = new Organizador();
        organizador.setNome(nome);
        organizador.setEmail(email);
        organizador.setSenha(passwordEncoder.encode(senha));

        organizadores.save(organizador);
    }
}