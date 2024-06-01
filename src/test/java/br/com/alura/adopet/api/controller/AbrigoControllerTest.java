package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoCadastramentoPetoDto;
import br.com.alura.adopet.api.dto.abrigo.SolicitacaoCadastramentoAbrigoDto;
import br.com.alura.adopet.api.service.AbrigoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AbrigoService abrigoService;

    @Autowired
    private JacksonTester<SolicitacaoCadastramentoAbrigoDto> jsonAbrigoDto;

    @Autowired
    private JacksonTester<SolicitacaoCadastramentoPetoDto> jsonPetDto;

    @Test
    void listar_deveDevolverCodigo200() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/abrigos").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void cadastrar_deveDevolverCodigo200_quandoDtoEValido() throws Exception {
        SolicitacaoCadastramentoAbrigoDto dto = new SolicitacaoCadastramentoAbrigoDto(
                "Nome do Abrigo",
                "(11)1234-5679",
                "john.doe_updated@example.com"
        );

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(jsonAbrigoDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void listarPets_deveDevolverCodigo200() throws Exception {
        MockHttpServletResponse response = mvc.perform(
                get("/abrigos/1/pets").contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void cadastrarPet_deveDevolverCodigo200_quandoDtoEValido() throws Exception {
        SolicitacaoCadastramentoPetoDto dto = new SolicitacaoCadastramentoPetoDto(
                "Nome do Pet",
                "Cachorro",
                "Caramelo",
                3,
                "Caramelo",
                25.5f
        );

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/1/pets")
                        .content(jsonPetDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void cadastrar_deveDevolverCodigo400_quandoDtoEInvalido() throws Exception {
        SolicitacaoCadastramentoAbrigoDto dto = new SolicitacaoCadastramentoAbrigoDto("", "", "");

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos")
                        .content(jsonAbrigoDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    void cadastrarPet_deveDevolverCodigo400_quandoDtoEInvalido() throws Exception {
        SolicitacaoCadastramentoPetoDto dto = new SolicitacaoCadastramentoPetoDto("", "", "", 0, "", 0.0f);

        MockHttpServletResponse response = mvc.perform(
                post("/abrigos/1/pets")
                        .content(jsonPetDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }
}