package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.pet.DetalhesPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.ObjectContent;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.alura.adopet.api.util.TestUtils.randomId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class PetControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PetService petService;

    @Autowired
    private JacksonTester<DetalhesPetDto> jsonDto;

    @Test
    void listarTodosDisponiveis_deveDevolverCodigo200_quandoExistemPetsDisponiveis() throws Exception {
        Abrigo abrigo = new Abrigo(
                "Nome do Abrigo",
                "email@email.com",
                "12345678"
        );
        Pet max = new Pet(
                TipoPet.CACHORRO,
                "Max",
                "Caramelo",
                3,
                "Caramelo",
                25.5f,
                abrigo
        );
        ReflectionTestUtils.setField(max, "id", randomId());
        when(petService.listarTodosDisponiveis()).thenReturn(List.of(
                new DetalhesPetDto(
                        max
                )
        ));

        MockHttpServletResponse response = mvc.perform(
                get("/pets")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void listarTodosDisponiveis_deveDevolverCodigo200_quandoNaoExistemPetsDisponiveis() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/pets")
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }
}