package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.DetalhesAbrigoDto;
import br.com.alura.adopet.api.dto.SolicitacaoCadastramentoPetoDto;
import br.com.alura.adopet.api.dto.abrigo.SolicitacaoCadastramentoAbrigoDto;
import br.com.alura.adopet.api.dto.pet.DetalhesPetDto;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import br.com.alura.adopet.api.validacoes.abrigo.SolicitacaoCadastramentoAbrigoValidacao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static br.com.alura.adopet.api.util.TestUtils.randomId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @Mock
    private AbrigoRepository repository;

    @Mock
    private PetRepository petRepository;

    @Spy
    private List<SolicitacaoCadastramentoAbrigoValidacao> validacoes = new ArrayList<>();

    @Mock
    private SolicitacaoCadastramentoAbrigoValidacao validacao1;

    @Mock
    private SolicitacaoCadastramentoAbrigoValidacao validacao2;

    @Mock
    private Abrigo abrigo;
    @InjectMocks
    private AbrigoService abrigoService;

    @Test
    void cadastrar_deveSalvarAbrigoEChamarValidacoes() {
        // given
        SolicitacaoCadastramentoAbrigoDto dto = new SolicitacaoCadastramentoAbrigoDto(
                "Nome",
                "email@email.com",
                "12345678"
        );
        validacoes.add(validacao1);
        validacoes.add(validacao2);

        // when
        abrigoService.cadastrar(dto);

        // then
        then(validacao1).should().validar(dto);
        then(validacao2).should().validar(dto);
        ArgumentCaptor<Abrigo> abrigoCaptor = ArgumentCaptor.forClass(Abrigo.class);
        then(repository).should().save(abrigoCaptor.capture());
        Abrigo abrigo = abrigoCaptor.getValue();
        assertThat(abrigo.getNome()).isEqualTo(dto.nome());
        assertThat(abrigo.getTelefone()).isEqualTo(dto.telefone());
        assertThat(abrigo.getEmail()).isEqualTo(dto.email());
    }

    @Test
    void listarPets_deveRetornarListaDePets() {
        // given
        long idAbrigo = randomId();
        Abrigo abrigo = new Abrigo("Nome", "email@email.com", "12345678");
        Pet max = new Pet(
                TipoPet.CACHORRO,
                "Max",
                "Caramelo",
                3,
                "Caramelo",
                25.5f,
                abrigo
        );
        max.setId(randomId());
        Pet lilas = new Pet(
                TipoPet.GATO,
                "Lilás",
                "Siamês",
                4,
                "Acizentado",
                8.0f,
                abrigo

        );
        lilas.setId(randomId());
        List<Pet> pets = List.of(max, lilas);
        abrigo.getPets().addAll(pets);
        given(repository.getReferenceById(idAbrigo)).willReturn(abrigo);

        // when
        List<DetalhesPetDto> detalhesPetDtos = abrigoService.listarPets(idAbrigo);

        // then
        assertThat(detalhesPetDtos)
                .containsExactlyInAnyOrder(
                        new DetalhesPetDto(max),
                        new DetalhesPetDto(lilas)
                );
    }

    @Test
    void cadastrarPet_deveSalvarPet() {
        // given
        long abrigoId = 1L;
        SolicitacaoCadastramentoPetoDto dto = new SolicitacaoCadastramentoPetoDto(
                "CACHORRO",
                "Max",
                "Caramelo",
                3,
                "Caramelo",
                25.5f
        );
        given(repository.getReferenceById(abrigoId)).willReturn(abrigo);
        doAnswer(invocation -> {
            ReflectionTestUtils.setField((Pet) invocation.getArgument(0), "id", randomId());
            return null;
        }).when(petRepository).save(any());

        // when
        DetalhesPetDto result = abrigoService.cadastrarPet(abrigoId, dto);

        // then
        ArgumentCaptor<Pet> petCaptor = ArgumentCaptor.forClass(Pet.class);
        then(petRepository).should().save(petCaptor.capture());
        Pet pet = petCaptor.getValue();
        assertThat(pet.getTipo())
                .isEqualTo(TipoPet.valueOf(dto.tipo().trim().toUpperCase()));
        assertThat(pet.getNome())
                .isEqualTo(dto.nome());
        assertThat(pet.getRaca())
                .isEqualTo(dto.raca());
        assertThat(pet.getIdade())
                .isEqualTo(dto.idade());
        assertThat(pet.getCor())
                .isEqualTo(dto.cor());
        assertThat(pet.getPeso())
                .isEqualTo(dto.peso());
        assertThat(pet.getAbrigo())
                .isEqualTo(abrigo);
        assertThat(result).isNotNull();
        assertThat(result.id()).isPositive();
        assertThat(result.tipoPet())
                .isEqualTo(dto.tipo().trim().toUpperCase());
        assertThat(result.nome())
                .isEqualTo(dto.nome());
        assertThat(result.raca())
                .isEqualTo(dto.raca());
        assertThat(result.idade())
                .isEqualTo(dto.idade());
        assertThat(result.cor())
                .isEqualTo(dto.cor());
        assertThat(result.peso())
                .isEqualTo(dto.peso());
    }

    @Test
    void listarTodos_deveRetornarTodosAbrigos() {
        Abrigo abrigo1 = new Abrigo("Abrigo1", "email1@email.com", "123456789");
        ReflectionTestUtils.setField(abrigo1, "id", randomId());
        Abrigo abrigo2 = new Abrigo("Abrigo2", "email2@email.com", "123456789");
        ReflectionTestUtils.setField(abrigo2, "id", randomId());
        List<Abrigo> abrigos = Arrays.asList(abrigo1, abrigo2);

        when(repository.findAll()).thenReturn(abrigos);

        List<DetalhesAbrigoDto> result = abrigoService.listarTodos();

        assertThat(result)
                .containsExactlyInAnyOrder(
                        new DetalhesAbrigoDto(abrigo1),
                        new DetalhesAbrigoDto(abrigo2)
                );
    }

    @Test
    void listarTodos_deveRetornarListaVazia_quandoNenhumAbrigoEhRetornadoDoBanco() {
        when(repository.findAll()).thenReturn(List.of());

        List<DetalhesAbrigoDto> result = abrigoService.listarTodos();

        assertThat(result).isEmpty();
    }
}