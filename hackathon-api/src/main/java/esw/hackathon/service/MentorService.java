package esw.hackathon.service;

import esw.hackathon.dto.ApiDtos.*;
import esw.hackathon.model.Mentor;
import esw.hackathon.repository.HackathonRepository;
import esw.hackathon.repository.MentorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentorService {

    private final MentorRepository repository;
    private final HackathonRepository hackathons;

    public List<MentorResponse> listar() {
        return repository.findAll(Sort.by("id"))
                .stream()
                .map(MentorService::response)
                .toList();
    }

    public MentorResponse buscar(Long id) {
        return response(entity(id));
    }

    @Transactional
    public MentorResponse criar(MentorRequest r, Long usuarioId) {
        var hackathon = hackathons.findById(r.hackathonId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Hackathon não encontrado"));

        var mentor = new Mentor();
        mentor.setId(usuarioId);
        mentor.setHackathon(hackathon);

        return response(repository.save(mentor));
    }

    private Mentor entity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Mentor não encontrado"));
    }

    public static MentorResponse response(Mentor mentor) {
        return new MentorResponse(
                mentor.getId(),
                mentor.getHackathon().getId()
        );
    }
}