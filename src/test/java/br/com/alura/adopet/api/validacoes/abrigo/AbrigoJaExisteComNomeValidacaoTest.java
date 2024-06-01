package br.com.alura.adopet.api.validacoes.abrigo;

import br.com.alura.adopet.api.dto.abrigo.SolicitacaoCadastramentoAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AbrigoJaExisteComNomeValidacaoTest {

    @Mock
    private AbrigoRepository repository;

    @InjectMocks
    private AbrigoJaExisteComNomeValidacao validacao;

    @Test
    void validar_deveLancarValidacaoException_quandoAbrigoComNomeJaExistir() {
        SolicitacaoCadastramentoAbrigoDto dto = new SolicitacaoCadastramentoAbrigoDto(
                "Abrigo 1",
                "(11)1234-5678",
                "email@email.com"
        );
        given(repository.existsByNome(dto.nome())).willReturn(true);

        assertThatThrownBy(
                () -> validacao.validar(dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Já existe um abrigo cadastrado com esse nome!");
    }

    @Test
    void validar_naoDeveLancarValidacaoException_quandoAbrigoComNomeNaoExistir() {
        SolicitacaoCadastramentoAbrigoDto dto = new SolicitacaoCadastramentoAbrigoDto(
                "Abrigo 1",
                "(11)1234-5678",
                "email@email.com"
        );
        given(repository.existsByNome(dto.nome())).willReturn(false);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(dto)
        );
    }
}