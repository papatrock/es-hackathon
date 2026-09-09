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

    public record LoginRequest(
        @NotBlank @Email String email,
        @NotBlank String senha
    ) {}
    
    public record LoginResponse(
        String token
    ) {}

    public record ParticipanteRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha
    ) {}

    public record ParticipanteResponse(
        Long id,
        String nome,
        String email
    ) {}

    public record JuradoRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        @NotNull @Positive Long hackathonId
    ) {}
    
    public record JuradoResponse(
        Long id,
        String nome,
        String email,
        Long hackathonId
    ) {}

    public record OrganizadorRequest(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha
    ) {}

    public record OrganizadorResponse(
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

    public record AvaliacaoRequest(
        @NotNull @Positive Long projetoId,
        @NotNull @Positive Long juradoId,
        @NotNull @DecimalMin("0.0") @DecimalMax("10.0") Double nota,
        @NotBlank String feedback
    ) {}

    public record AvaliacaoResponse(
        Long id,
        Long projetoId,
        //Futuro: String projetoTitulo, //ou algo do tipo
        Long juradoId,
        String juradoNome,
        Double nota,
        String feedback
    ) {}

    public record ProjetoRequest(
        @NotBlank String titulo,
        @NotBlank String descricao,
        @NotBlank String areaTematica
    ) {}

    public record ProjetoResponse(
        Long id,
        String titulo,
        String descricao,
        String areaTematica,
        Long equipeId
    ) {}

    public record MentorRequest(
        @NotNull @Positive Long hackathonId
    ) {}

    public record MentorResponse(
        Long id,
        Long hackathonId
    ) {}

    public record MentoriaRequest(
        @NotBlank String comentarios,
        @NotNull @Positive Long mentorId,
        @NotNull @Positive Long equipeId
    ) {}

    public record MentoriaResponse(
        Long id,
        String comentarios,
        Long equipeId,
        Long mentorId
    ) {}

    public record ClassificacaoResponse(
        Integer posicao,
        Long projetoId,
        Double notaFinal,
        Long quantidadeAvaliacoes
    ) {}
}
