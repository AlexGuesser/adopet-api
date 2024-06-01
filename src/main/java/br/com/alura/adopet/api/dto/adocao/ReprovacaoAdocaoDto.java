package br.com.alura.adopet.api.dto.adocao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReprovacaoAdocaoDto(
        @NotNull
        Long idAdocao,
        @NotBlank
        String justificativa
) {
}
