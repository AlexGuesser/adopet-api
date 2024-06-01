package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.adocao.AprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.ReprovacaoAdocaoDto;
import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.model.*;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.adocao.SolicitacaoAdocaoValidacao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static br.com.alura.adopet.api.util.TestUtils.randomId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AdocaoServiceTest {

    @Mock
    private AdocaoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private TutorRepository tutorRepository;

    @Mock
    private EmailService emailService;

    @Spy
    private List<SolicitacaoAdocaoValidacao> validacoes = new ArrayList<>();

    @Mock
    private SolicitacaoAdocaoValidacao validacao1;

    @Mock
    private SolicitacaoAdocaoValidacao validacao2;

    private Abrigo abrigo = new Abrigo(
            "Nome do Abrigo",
            "email@email.com",
            "12345678"
    );

    private Pet pet = new Pet(
            TipoPet.CACHORRO,
            "Max",
            "Caramelo",
            3,
            "Caramelo",
            25.5f,
            abrigo
    );

    private Tutor tutor = new Tutor(
            "Nome",
            "12345678",
            "email@email.com"
    );

    private Adocao adocao = new Adocao(
            tutor,
            pet,
            "motivo"
    );


    @Captor
    private ArgumentCaptor<Adocao> adocaoCaptor;

    SolicitacaoAdocaoDto solicitacaoAdocaoDto = new SolicitacaoAdocaoDto(
            randomId(),
            randomId(),
            "motivo"
    );

    AprovacaoAdocaoDto aprovacaoAdocaoDto = new AprovacaoAdocaoDto(
            randomId()
    );

    ReprovacaoAdocaoDto reprovacaoAdocaoDto = new ReprovacaoAdocaoDto(
            randomId(),
            "Justificativa"
    );

    @InjectMocks
    private AdocaoService adocaoService;


    @Test
    void solicitar_deveSalvarAdocao() {
        // given
        given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor())).willReturn(tutor);

        // when
        adocaoService.soliticar(solicitacaoAdocaoDto);

        // then
        then(repository).should().save(adocaoCaptor.capture());
        Adocao adocaoSalva = adocaoCaptor.getValue();
        assertThat(adocaoSalva.getTutor()).isEqualTo(tutor);
        assertThat(adocaoSalva.getPet()).isEqualTo(pet);
        assertThat(adocaoSalva.getMotivo()).isEqualTo(solicitacaoAdocaoDto.motivo());
    }

    @Test
    void solicitar_deveChamarTodasAsValidacoes() {
        // given
        given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor())).willReturn(tutor);
        validacoes.add(validacao1);
        validacoes.add(validacao2);

        // when
        adocaoService.soliticar(solicitacaoAdocaoDto);

        // then
        then(validacao1).should().validar(solicitacaoAdocaoDto);
        then(validacao2).should().validar(solicitacaoAdocaoDto);
    }

    @Test
    void solicitar_deveEnviarParaOAbrigo() {
        // given
        given(petRepository.getReferenceById(solicitacaoAdocaoDto.idPet())).willReturn(pet);
        given(tutorRepository.getReferenceById(solicitacaoAdocaoDto.idTutor())).willReturn(tutor);

        // when
        adocaoService.soliticar(solicitacaoAdocaoDto);

        // then
        then(emailService).should().enviar(
                abrigo.getEmail(),
                "Solicitação de adoção",
                "Olá " + abrigo.getNome()
                        + "!\n\nUma solicitação de adoção foi registrada hoje para o pet: " + pet.getNome()
                        + ". \nFavor avaliar para aprovação ou reprovação."
        );
    }

    @Test
    void aprovar_deveAprovarAdocao() {
        given(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        assertThat(adocao.getStatus()).isNotEqualTo(StatusAdocao.APROVADO);
        // when
        adocaoService.aprovar(aprovacaoAdocaoDto);

        // then
        assertThat(adocao.getStatus()).isEqualTo(StatusAdocao.APROVADO);
    }

    @Test
    void aprovar_deveEnviarEmailParaOTutor() {
        given(repository.getReferenceById(aprovacaoAdocaoDto.idAdocao())).willReturn(adocao);

        adocaoService.aprovar(aprovacaoAdocaoDto);

        then(emailService).should().enviar(
                tutor.getEmail(),
                "Adoção aprovada",
                "Parabéns " + adocao.getTutor().getNome()
                        + "!\n\nSua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi aprovada.\nFavor entrar em contato com o abrigo " + adocao.getPet().getAbrigo().getNome() + " para agendar a busca do seu pet."
        );
    }

    @Test
    void reprovar_deveAlterarStatusDaAdocaoParaReprovada() {
        given(repository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).willReturn(adocao);
        assertThat(adocao.getStatus()).isNotEqualTo(StatusAdocao.REPROVADO);

        adocaoService.reprovar(reprovacaoAdocaoDto);

        assertThat(adocao.getStatus()).isEqualTo(StatusAdocao.REPROVADO);
    }

    @Test
    void reprovar_deveEnviarEmailParaOTutor() {
        given(repository.getReferenceById(reprovacaoAdocaoDto.idAdocao())).willReturn(adocao);

        adocaoService.reprovar(reprovacaoAdocaoDto);

        then(emailService).should().enviar(
                tutor.getEmail(),
                "Adoção reprovada",
                "Olá " + adocao.getTutor().getNome() + "!\n\nInfelizmente sua adoção do pet " + adocao.getPet().getNome() + ", solicitada em " + adocao.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + ", foi reprovada pelo abrigo " + adocao.getPet().getAbrigo().getNome() + " com a seguinte justificativa: " + adocao.getJustificativaStatus()
        );
    }

}