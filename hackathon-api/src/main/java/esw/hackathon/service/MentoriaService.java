package esw.hackathon.service;

import esw.hackathon.dto.ApiDtos.MentoriaRequest;
import esw.hackathon.dto.ApiDtos.MentoriaResponse;
import esw.hackathon.model.Mentoria;
import esw.hackathon.repository.MentoriaRepository;
import esw.hackathon.repository.MentorRepository;
import esw.hackathon.repository.EquipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoriaService {

    private final MentoriaRepository repository;
    private final MentorRepository mentores;
    private final EquipeRepository equipes;

    public List<MentoriaResponse> listar() {
        return repository.findAll(Sort.by("id"))
                .stream()
                .map(MentoriaService::response)
                .toList();
    }

    public MentoriaResponse buscar(Long id) {
        return response(entity(id));
    }

    public List<MentoriaResponse> listarPorEquipe(Long equipeId) {
        return repository
                .findByEquipeIdOrderById(equipeId)
                .stream()
                .map(MentoriaService::response)
                .toList();
    }

    public List<MentoriaResponse> listarPorMentor(Long mentorId) {
        if (!mentores.existsById(mentorId)) {
            throw new ResponseStatusException(NOT_FOUND, "Mentor não encontrado");
        }

        return repository
                .findByMentorIdOrderById(mentorId)
                .stream()
                .map(MentoriaService::response)
                .toList();
    }

    @Transactional
    public MentoriaResponse criar(MentoriaRequest r) {
        var mentor = mentores.findById(r.mentorId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Mentor não encontrado"));

        var equipe = equipes.findById(r.equipeId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Equipe não encontrada"));

        var mentoria = new Mentoria();
        mentoria.setMentor(mentor);
        mentoria.setEquipe(equipe);
        mentoria.setComentarios(r.comentarios().strip());

        return response(repository.save(mentoria));
    }

    private Mentoria entity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Mentoria não encontrada"));
    }

    @Transactional
    public void excluir(Long id) {
        repository.delete(entity(id));
    }

    public static MentoriaResponse response(Mentoria m) {
        return new MentoriaResponse(
                m.getId(),
                m.getComentarios(),
                m.getEquipe().getId(),
                m.getMentor().getId()
        );
    }
}