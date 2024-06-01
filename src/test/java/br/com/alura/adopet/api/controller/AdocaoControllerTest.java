package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
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

import static br.com.alura.adopet.api.util.TestUtils.randomId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AdocaoService adocaoService;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDto> jsonDto;

    @Test
    void solicitar_deveDevolverCodigo400_quandoSolicitacaoDeAdocaoTemErros() throws Exception {
        String json = """
                {
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                post("/adocoes").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    void solicitar_deveDevolverCodigo200_quandoSolicitacaoDeAdocaoSemErros() throws Exception {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
                randomId(),
                randomId(),
                "Motivo qualquer"
        );

        MockHttpServletResponse response = mvc.perform(
                post("/adocoes").content(jsonDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void aprovar_deveDevolverCodigo400_quandoAprovacaoAdocaoDtoTemErros() throws Exception {
        String json = """
                {
                    "idAdocao": null
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    void aprovar_deveDevolverCodigo200_quandoAprovacaoAdocaoDtoNaoTemErros() throws Exception {
        String json = """
                {
                    "idAdocao": 1
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    void reprovar_deveDevolverCodigo400_quandoReprovacaoAdocaoDtoTemErros() throws Exception {
        String json = """
                {
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(400);
    }

    @Test
    void reprovar_deveDevolverCodigo200_quandoReprovacaoAdocaoDtoNaoTemErros() throws Exception {
        String json = """
                {
                    "idAdocao": 1,
                    "justificativa": "Justificativa qualquer"
                }
                """;

        MockHttpServletResponse response = mvc.perform(
                put("/adocoes/reprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(200);
    }

}