package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculadoraProbabilidadeAdocaoTest {

    private static final Abrigo ABRIGO = new Abrigo(
            "Abrigo",
            "email@email.com",
            "123456789"
    );

    @Test
    void calcular_deveRetornarProbabilidadeAlta_quandoPetTemIdadeBaixaEPesoBaixo() {
        //Idade 4 anos e 4KG - ALTA
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        Pet pet = new Pet(
                TipoPet.CACHORRO,
                "Rex",
                "Vira-lata",
                4,
                "Marrom",
                4.0f,
                ABRIGO
        );

        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        assertThat(probabilidade).isEqualTo(ProbabilidadeAdocao.ALTA);
    }

    @Test
    void calcular_deveRetornarProbabilidadeMedia_quandoPetTemIdadeAltaEPesoBaixo() {
        //Idade 15 anos e 4KG - MEDIA
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        Pet pet = new Pet(
                TipoPet.CACHORRO,
                "Rex",
                "Vira-lata",
                15,
                "Marrom",
                4.0f,
                ABRIGO
        );

        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        assertThat(probabilidade)
                .isEqualTo(ProbabilidadeAdocao.MEDIA);
    }

    @Test
    void calcular_deveRetornarProbabilidadeBaixa_quandoNotaMenorQue5() {
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        Pet pet = new Pet(
                TipoPet.CACHORRO,
                "Rex",
                "Vira-lata",
                16,
                "Marrom",
                16.0f,
                ABRIGO
        );

        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        assertThat(probabilidade).isEqualTo(ProbabilidadeAdocao.BAIXA);
    }

    @Test
    void calcular_deveRetornarProbabilidadeMedia_quandoNotaEntre5e8() {
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        Pet pet = new Pet(
                TipoPet.CACHORRO,
                "Rex",
                "Vira-lata",
                11,
                "Marrom",
                15.0f,
                ABRIGO
        );

        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        assertThat(probabilidade).isEqualTo(ProbabilidadeAdocao.MEDIA);
    }

    @Test
    void calcular_deveRetornarProbabilidadeAlta_quandoNotaMaiorQue8() {
        CalculadoraProbabilidadeAdocao calculadora = new CalculadoraProbabilidadeAdocao();
        Pet pet = new Pet(
                TipoPet.CACHORRO,
                "Rex",
                "Vira-lata",
                4,
                "Marrom",
                4.0f,
                ABRIGO
        );

        ProbabilidadeAdocao probabilidade = calculadora.calcular(pet);

        assertThat(probabilidade).isEqualTo(ProbabilidadeAdocao.ALTA);
    }

}