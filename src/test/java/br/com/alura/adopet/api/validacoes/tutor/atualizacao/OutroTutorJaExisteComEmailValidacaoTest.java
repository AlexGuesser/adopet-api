package br.com.alura.adopet.api.validacoes.tutor.atualizacao;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoAtualizacaoTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static br.com.alura.adopet.api.util.TestUtils.randomId;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutroTutorJaExisteComEmailValidacaoTest {

    @Mock
    private TutorRepository repository;

    @InjectMocks
    private OutroTutorJaExisteComEmailValidacao validacao;

    @Test
    void validar_deveLancarValidacaoException_quandoOutroTutorPossuiMesmoEmail() {
        long tutorId = randomId();
        SolicitacaoAtualizacaoTutorDto dto = new SolicitacaoAtualizacaoTutorDto(
                "1234567890",
                "test@test.com"
        );
        when(repository.existeOutroTutorComEsseEmail(tutorId, dto.email())).thenReturn(true);

        assertThatThrownBy(
                () -> validacao.validar(tutorId, dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Outro tutor já possuí esse email cadastrado!");
    }

    @Test
    void validar_naoDeveLancarExcecao_quandoNaoExisteOutroTutorComMesmoEmail() {
        long tutorId = randomId();
        SolicitacaoAtualizacaoTutorDto dto = new SolicitacaoAtualizacaoTutorDto(
                "1234567890",
                "test@test.com"
        );
        when(repository.existeOutroTutorComEsseEmail(tutorId, dto.email())).thenReturn(false);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(tutorId, dto)
        );
    }
}