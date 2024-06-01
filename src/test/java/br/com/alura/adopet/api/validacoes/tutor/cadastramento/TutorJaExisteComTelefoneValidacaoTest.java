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
class TutorJaExisteComTelefoneValidacaoTest {

    @Mock
    private TutorRepository repository;

    @InjectMocks
    private TutorJaExisteComTelefoneValidacao validacao;

    @Test
    void validar_deveLancarValidacaoException_quandoTutorJaPossuiTelefoneCadastrado() {
        SolicitacaoCadastramentoTutorDto dto = new SolicitacaoCadastramentoTutorDto(
                "nome",
                "1234567890",
                "email@email.com"
        );
        given(repository.existsByTelefone(dto.telefone())).willReturn(true);

        assertThatThrownBy(
                () -> validacao.validar(dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Já existe um tutor cadastrado com esse telefone!");
    }

    @Test
    void validar_naoDeveLancarExcecao_quandoTutorNaoPossuiTelefoneCadastrado() {
        SolicitacaoCadastramentoTutorDto dto = new SolicitacaoCadastramentoTutorDto(
                "nome",
                "1234567890",
                "email@email.com"
        );
        given(repository.existsByTelefone(dto.telefone())).willReturn(false);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(dto)
        );
    }


}