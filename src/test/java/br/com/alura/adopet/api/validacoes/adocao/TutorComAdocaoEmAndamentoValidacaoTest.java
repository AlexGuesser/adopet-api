package br.com.alura.adopet.api.validacoes.adocao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.alura.adopet.api.util.TestUtils.randomId;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TutorComAdocaoEmAndamentoValidacaoTest {

    @Mock
    private AdocaoRepository repository;

    @InjectMocks
    private TutorComAdocaoEmAndamentoValidacao validacao;

    @Test
    void validar_deveLancarValidacaoException_quandoTutorTemAdocaoEmAndamento() {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
                randomId(),
                randomId(),
                "Motivo qualquer"
        );
        given(repository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(true);

        assertThatThrownBy(
                () -> validacao.validar(dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Tutor já possuí uma adoção em andamento!");
    }

    @Test
    void validar_naoDeveLancarValidacaoException_quandoTutorNaoTemAdocaoEmAndamento() {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
                randomId(),
                randomId(),
                "Motivo qualquer"
        );
        given(repository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)).willReturn(false);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(dto)
        );
    }
}