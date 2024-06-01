package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoAtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.SolicitacaoCadastramentoTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.tutor.atualizacao.SolicitacaoAtualizacaoTutorValidacao;
import br.com.alura.adopet.api.validacoes.tutor.cadastramento.SolicitacaoCadastramentoTutorValidacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class TutorServiceTest {

    @Mock
    private TutorRepository tutorRepository;

    @Spy
    private List<SolicitacaoCadastramentoTutorValidacao> validacoesCadastramento = new ArrayList<>();

    @Spy
    private List<SolicitacaoAtualizacaoTutorValidacao> validacoesAtualizacao = new ArrayList<>();

    @Mock
    private SolicitacaoCadastramentoTutorValidacao validacaoCadastramento1;

    @Mock
    private SolicitacaoCadastramentoTutorValidacao validacaoCadastramento2;

    @Mock
    private SolicitacaoAtualizacaoTutorValidacao validacaoAtualizacao1;

    @Mock
    private SolicitacaoAtualizacaoTutorValidacao validacaoAtualizacao2;

    @InjectMocks
    private TutorService tutorService;

    @Test
    void cadastrar_deveSalvarTutorEChamarValidacoes() {
        // given
        SolicitacaoCadastramentoTutorDto dto = new SolicitacaoCadastramentoTutorDto(
                "Nome do Tutor",
                "12345678",
                "email@email.com"
        );

        // when
        tutorService.cadastrar(dto);

        // then
        ArgumentCaptor<Tutor> tutorCaptor = ArgumentCaptor.forClass(Tutor.class);
        then(tutorRepository).should().save(tutorCaptor.capture());
        Tutor tutorSalvo = tutorCaptor.getValue();
        assertThat(tutorSalvo.getNome()).isEqualTo(dto.nome());
        assertThat(tutorSalvo.getTelefone()).isEqualTo(dto.telefone());
        assertThat(tutorSalvo.getEmail()).isEqualTo(dto.email());
    }

    @Test
    void cadastrar_deve_chamarTodasValidacoesDeCadastramento() {
        // given
        SolicitacaoCadastramentoTutorDto dto = new SolicitacaoCadastramentoTutorDto(
                "Nome do Tutor",
                "12345678",
                "email@email.com"
        );
        validacoesCadastramento.add(validacaoCadastramento1);
        validacoesCadastramento.add(validacaoCadastramento2);

        // when
        tutorService.cadastrar(dto);

        //then
        then(validacaoCadastramento1).should().validar(dto);
        then(validacaoCadastramento2).should().validar(dto);
    }

    @Test
    void atualizar_deveAtualizarTutorEChamarValidacoes() {
        // given
        long id = 1L;
        Tutor tutor = new Tutor(
                "Nome do Tutor",
                "12345678 - antigo",
                "email@email.com - antigo"
        );
        given(tutorRepository.getReferenceById(id)).willReturn(tutor);
        SolicitacaoAtualizacaoTutorDto dto = new SolicitacaoAtualizacaoTutorDto(
                "12345678",
                "email@email.com"
        );
        validacoesAtualizacao.add(validacaoAtualizacao1);
        validacoesAtualizacao.add(validacaoAtualizacao2);

        // when
        tutorService.atualizar(id, dto);

        // then
        then(validacaoAtualizacao1).should().validar(id, dto);
        then(validacaoAtualizacao2).should().validar(id, dto);
        assertThat(tutor.getTelefone()).isEqualTo(dto.telefone());
        assertThat(tutor.getEmail()).isEqualTo(dto.email());
    }

}