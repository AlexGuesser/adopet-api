package br.com.alura.adopet.api.dto;

import br.com.alura.adopet.api.model.Abrigo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DetalhesAbrigoDto(
        long id,

        @NotBlank
        String nome,

        @NotBlank
        String telefone,

        @NotBlank
        String email
) {

    public DetalhesAbrigoDto(Abrigo abrigo) {
        this(
                abrigo.getId(),
                abrigo.getNome(),
                abrigo.getTelefone(),
                abrigo.getEmail()
        );
    }

}
