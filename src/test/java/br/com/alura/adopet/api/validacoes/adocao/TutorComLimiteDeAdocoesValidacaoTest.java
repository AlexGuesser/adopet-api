package br.com.alura.adopet.api.validacoes.adocao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
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
class TutorComLimiteDeAdocoesValidacaoTest {

    @Mock
    private AdocaoRepository repository;

    @InjectMocks
    private TutorComLimiteDeAdocoesValidacao validacao;

    @Test
    void validar_deveLancarValidacaoException_quandoNumeroDeAdocoesPorTutorForCincoOuMais() {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
                randomId(),
                randomId(),
                "Motivo qualquer"
        );
        given(repository.numeroDeAdocoesPorTutor(dto.idTutor())).willReturn(5L);

        assertThatThrownBy(
                () -> validacao.validar(dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Tutor chegou ao limite máximo de 5 adoções!");
    }

    @Test
    void validar_naoDeveLancarValidacaoException_quandoNumeroDeAdocoesPorTutorForMenosQueCinco() {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(
                randomId(),
                randomId(),
                "Motivo qualquer"
        );
        given(repository.numeroDeAdocoesPorTutor(dto.idTutor())).willReturn(4L);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(dto)
        );
    }
}