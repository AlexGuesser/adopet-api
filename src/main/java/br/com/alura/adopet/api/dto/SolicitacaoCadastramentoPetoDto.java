package br.com.alura.adopet.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record SolicitacaoCadastramentoPetoDto(

        @NotBlank
        String tipo,

        @NotBlank
        String nome,

        @NotBlank
        String raca,

        @Positive
        Integer idade,

        @NotBlank
        String cor,

        @Positive
        Float peso
) {
}
