package esw.hackathon.config;

import esw.hackathon.model.Hackathon;
import esw.hackathon.model.Participante;
import esw.hackathon.repository.HackathonRepository;
import esw.hackathon.repository.ParticipanteRepository;
import esw.hackathon.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Profile("dev")
@RequiredArgsConstructor
public class DevDataSeeder implements CommandLineRunner {

    private final HackathonRepository hackathons;
    private final ParticipanteRepository participantes;
    private final UsuarioRepository usuarios;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        criarHackathonExemplo();
        criarParticipanteExemplo(
            "Ana Exemplo",
            "ana@example.com",
            "senha1234"
        );
        criarParticipanteExemplo(
            "Bruno Exemplo",
            "bruno@example.com",
            "senha1234"
        );
    }

    private void criarHackathonExemplo() {
        if (hackathons.count() > 0) {
            return;
        }

        var hackathon = new Hackathon();
        hackathon.setNome("Hackathon de exemplo");
        hackathon.setDataInicio(LocalDate.now().plusDays(7));
        hackathon.setDataTermino(LocalDate.now().plusDays(9));
        hackathon.setMaxEquipes(3);

        hackathons.save(hackathon);
    }

    private void criarParticipanteExemplo(
            String nome,
            String email,
            String senha) {

        if (usuarios.existsByEmail(email)) {
            return;
        }

        var participante = new Participante();
        participante.setNome(nome);
        participante.setEmail(email);
        participante.setSenha(passwordEncoder.encode(senha));

        participantes.save(participante);
    }
}