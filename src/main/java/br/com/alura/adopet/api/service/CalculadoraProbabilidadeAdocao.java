package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.model.TipoPet;

public class CalculadoraProbabilidadeAdocao {

    public ProbabilidadeAdocao calcular(Pet pet) {
        int nota = calcularNota(pet);

        if (nota >= 8) {
            return ProbabilidadeAdocao.ALTA;
        }

        if (nota >= 5) {
            return ProbabilidadeAdocao.MEDIA;
        }
        return ProbabilidadeAdocao.BAIXA;
    }

    private int calcularNota(Pet pet) {

        int peso = pet.getPeso().intValue();
        int idade = pet.getIdade();
        TipoPet tipo = pet.getTipo();

        int nota = 10;

        if (TipoPet.CACHORRO.equals(tipo) && peso > 15) {
            nota -= 2;
        }

        if (TipoPet.GATO.equals(tipo) && peso > 2) {
            nota -= 2;
        }

        if (idade >= 15) {
            nota -= 5;
        } else if (idade >= 10) {
            nota -= 4;
        }

        return nota;

    }

}
