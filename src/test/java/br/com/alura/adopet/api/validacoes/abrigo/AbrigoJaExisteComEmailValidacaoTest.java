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
class AbrigoJaExisteComEmailValidacaoTest {

    @Mock
    private AbrigoRepository repository;

    @InjectMocks
    private AbrigoJaExisteComEmailValidacao validacao;

    @Test
    void validar_deveLancarValidacaoException_quandoAbrigoComEmailJaExistir() {
        SolicitacaoCadastramentoAbrigoDto dto = new SolicitacaoCadastramentoAbrigoDto(
                "Abrigo 1",
                "(11)1234-5678",
                "email@email.com"
        );
        given(repository.existsByEmail(dto.email())).willReturn(true);

        assertThatThrownBy(
                () -> validacao.validar(dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Já existe um abrigo cadastrado com esse email!");
    }

    @Test
    void validar_naoDeveLancarValidacaoException_quandoAbrigoComEmailNaoExistir() {
        SolicitacaoCadastramentoAbrigoDto dto = new SolicitacaoCadastramentoAbrigoDto(
                "Abrigo 1",
                "(11)1234-5678",
                "email@email.com"
        );
        given(repository.existsByEmail(dto.email())).willReturn(false);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(dto)
        );
    }

}