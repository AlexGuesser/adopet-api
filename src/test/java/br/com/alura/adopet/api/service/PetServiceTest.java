package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.pet.DetalhesPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static br.com.alura.adopet.api.util.TestUtils.randomId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

    @Mock
    private PetRepository petRepository;

    @InjectMocks
    private PetService petService;

    private Abrigo abrigo = new Abrigo(
            "Nome do Abrigo",
            "email@email.com",
            "12345678"
    );

    private Pet max = new Pet(
            TipoPet.CACHORRO,
            "Max",
            "Caramelo",
            3,
            "Caramelo",
            25.5f,
            abrigo
    );

    private Pet lilica = new Pet(
            TipoPet.CACHORRO,
            "Lilyca",
            "puddle",
            9,
            "Branca",
            10.0f,
            abrigo
    );

    @Test
    void listarTodosDisponiveis_deveRetornarTodosOsPetsDisponiveis() {
        ReflectionTestUtils.setField(max, "id", randomId());
        ReflectionTestUtils.setField(lilica, "id", randomId());
        when(petRepository.pegaTodosDisponiveis()).thenReturn(Arrays.asList(max, lilica));

        List<DetalhesPetDto> result = petService.listarTodosDisponiveis();

        assertThat(result)
                .containsExactlyInAnyOrder(
                        new DetalhesPetDto(max),
                        new DetalhesPetDto(lilica)
                );
    }

    @Test
    void listarTodosDisponiveis_deveRetornarListaVazia() {
        when(petRepository.pegaTodosDisponiveis()).thenReturn(Collections.emptyList());

        List<DetalhesPetDto> result = petService.listarTodosDisponiveis();

        assertThat(result).isEmpty();
    }
}