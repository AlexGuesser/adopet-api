package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoAtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.SolicitacaoCadastramentoTutorDto;
import br.com.alura.adopet.api.service.TutorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TutorControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TutorService tutorService;

    @Autowired
    private JacksonTester<SolicitacaoCadastramentoTutorDto> jsonCadastramentoDto;

    @Autowired
    private JacksonTester<SolicitacaoAtualizacaoTutorDto> jsonAtualizacaoDto;

    @Test
    void cadastrar_deveDevolverCodigo200_quandoDtoEValido() throws Exception {
        SolicitacaoCadastramentoTutorDto dto = new SolicitacaoCadastramentoTutorDto(
                "John Doe",
                "(11)1234-5678",
                "john.doe@example.com"
        );

        MockHttpServletResponse response = mvc.perform(
                post("/tutores")
                        .content(jsonCadastramentoDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void atualizar_deveDevolverCodigo200_quandoDtoEValido() throws Exception {
        SolicitacaoAtualizacaoTutorDto dto = new SolicitacaoAtualizacaoTutorDto(
                "(11)1234-5679",
                "john.doe_updated@example.com"
        );

        MockHttpServletResponse response = mvc.perform(
                put("/tutores/1")
                        .content(jsonAtualizacaoDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void cadastrar_deveDevolverCodigo400_quandoDtoEInvalido() throws Exception {
        SolicitacaoCadastramentoTutorDto dto = new SolicitacaoCadastramentoTutorDto("", "", "");

        MockHttpServletResponse response = mvc.perform(
                post("/tutores")
                        .content(jsonCadastramentoDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    void atualizar_deveDevolverCodigo400_quandoDtoEInvalido() throws Exception {
        SolicitacaoAtualizacaoTutorDto dto = new SolicitacaoAtualizacaoTutorDto("", "");

        MockHttpServletResponse response = mvc.perform(
                put("/tutores/1")
                        .content(jsonAtualizacaoDto.write(dto).getJson())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }
}