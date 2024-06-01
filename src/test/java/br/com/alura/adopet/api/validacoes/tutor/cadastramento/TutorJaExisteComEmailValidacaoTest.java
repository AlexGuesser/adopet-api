package br.com.alura.adopet.api.validacoes.tutor.cadastramento;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoCadastramentoTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TutorJaExisteComEmailValidacaoTest {

    @Mock
    private TutorRepository repository;

    @InjectMocks
    private TutorJaExisteComEmailValidacao validacao;

    @Test
    void validar_deveLancarValidacaoException_quandoTutorJaPossuiEmailCadastrado() {
        SolicitacaoCadastramentoTutorDto dto = new SolicitacaoCadastramentoTutorDto(
                "nome",
                "1234567890",
                "email@email.com"
        );
        given(repository.existsByEmail(dto.email())).willReturn(true);

        assertThatThrownBy(
                () -> validacao.validar(dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Já existe um tutor cadastrado com esse email!");
    }

    @Test
    void validar_naoDeveLancarExcecao_quandoTutorNaoPossuiEmailCadastrado() {
        SolicitacaoCadastramentoTutorDto dto = new SolicitacaoCadastramentoTutorDto(
                "nome",
                "1234567890",
                "email@email.com"
        );
        given(repository.existsByEmail(dto.email())).willReturn(false);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(dto)
        );
    }

}