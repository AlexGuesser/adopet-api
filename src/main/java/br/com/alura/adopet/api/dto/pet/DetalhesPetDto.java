package br.com.alura.adopet.api.dto.pet;

import br.com.alura.adopet.api.model.Pet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DetalhesPetDto(

        long id,

        @NotNull
        String tipoPet,

        @NotBlank
        String nome,

        @NotBlank
        String raca,

        @NotNull
        Integer idade,

        @NotBlank
        String cor,

        @NotNull
        Float peso
) {

    public DetalhesPetDto(Pet pet) {
        this(
                pet.getId(),
                pet.getTipo().name(),
                pet.getNome(),
                pet.getRaca(),
                pet.getIdade(),
                pet.getCor(),
                pet.getPeso()
        );
    }

}
