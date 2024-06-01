package br.com.alura.adopet.api.dto.adocao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SolicitacaoAdocaoDto(
        @NotNull
        Long idPet,

        @NotNull
        Long idTutor,

        @NotBlank(message = "O motivo da solicitação é obrigatório")
        String motivo
) {
}
