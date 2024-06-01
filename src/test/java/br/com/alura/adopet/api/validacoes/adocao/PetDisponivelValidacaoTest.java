package br.com.alura.adopet.api.validacoes.adocao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PetDisponivelValidacaoTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @InjectMocks
    private final PetDisponivelValidacao validacao = new PetDisponivelValidacao();

    @Test
    void validar_deveJogarValidacaoException_quandoPetJaEstaAdotado() {
        given(
                petRepository.getReferenceById(dto.idPet())
        ).willReturn(pet);
        given(
                pet.getAdotado()
        ).willReturn(true);

        assertThatThrownBy(
                () -> validacao.validar(dto)
        ).isInstanceOf(ValidacaoException.class)
                .hasMessage("Pet já foi adotado!");
    }

    @Test
    void validar_naoDeveJogarValidacaoException_quandoPetNaoEstaAdotado() {
        given(
                petRepository.getReferenceById(dto.idPet())
        ).willReturn(pet);
        given(
                pet.getAdotado()
        ).willReturn(false);

        assertThatNoException().isThrownBy(
                () -> validacao.validar(dto)
        );
    }

}