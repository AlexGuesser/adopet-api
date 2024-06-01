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
class OutroTutorJaExisteComTelefoneValidacaoTest {

    @Mock
    private TutorRepository repository;

    @InjectMocks
    private OutroTutorJaExisteComTelefoneValidacao validacao;

    @Test
    void validar_deveLancarValidacaoException_quandoOutroTutorPossuiMesmoTelefone() {
        long tutorId = randomId();
        SolicitacaoAtualizacaoTutorDto dto = new SolicitacaoAtualizacaoTutorDto(
                "1234567890",
                "test@test.com"
        );
        when(repository.existeOutroTutorComEsseTelefone(tutorId, dto.telefone())).thenReturn(true);

        assertThatThrownBy(
                () -> validacao.validar(tutorId, dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Outro tutor já possuí esse telefone cadastrado!");
    }

    @Test
    void validar_naoDeveLancarExcecao_quandoNaoExisteOutroTutorComMesmoTelefone() {
        long tutorId = randomId();
        SolicitacaoAtualizacaoTutorDto dto = new SolicitacaoAtualizacaoTutorDto(
                "1234567890",
                "test@test.com"
        );
        when(repository.existeOutroTutorComEsseTelefone(tutorId, dto.telefone())).thenReturn(false);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(tutorId, dto)
        );
    }

}