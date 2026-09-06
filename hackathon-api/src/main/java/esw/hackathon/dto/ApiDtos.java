package esw.hackathon.dto;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public final class ApiDtos {
    private ApiDtos() {}

    public record HackathonRequest(
        @NotBlank String nome,
        @NotNull LocalDate dataInicio,
        @NotNull LocalDate dataTermino,
        @NotNull @Positive Integer maxEquipes
    ) {}

    public record HackathonResponse(
        Long id,
        String nome,
        LocalDate dataInicio,
        LocalDate dataTermino,
        Integer maxEquipes
    ) {}

    public record ParticipanteRequest(
        @NotBlank String nome,
        @NotBlank @Email String email
    ) {}

    public record ParticipanteResponse(
        Long id,
        String nome,
        String email
    ) {}

    public record EquipeRequest(
        @NotBlank String nome,
        @NotEmpty Set<@NotNull @Positive Long> participanteIds
    ) {}

    public record EquipeResponse(
        Long id,
        String nome,
        Long hackathonId,
        List<ParticipanteResponse> participantes
    ) {}
}
