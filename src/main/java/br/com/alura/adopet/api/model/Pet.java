package br.com.alura.adopet.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.jetbrains.annotations.TestOnly;

import java.util.Objects;

@Entity
@Table(name = "pets")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoPet tipo;

    private String nome;

    private String raca;

    private Integer idade;

    private String cor;

    private Float peso;

    private boolean adotado;

    @ManyToOne(fetch = FetchType.LAZY)
    private Abrigo abrigo;

    @OneToOne(mappedBy = "pet", fetch = FetchType.LAZY)
    private Adocao adocao;

    protected Pet() {
    }

    public Pet(TipoPet tipo, String nome, String raca, Integer idade, String cor, Float peso, Abrigo abrigo) {
        this.tipo = tipo; //;
        this.nome = nome;
        this.raca = raca;
        this.idade = idade;
        this.cor = cor;
        this.peso = peso;
        this.adotado = false;
        this.abrigo = abrigo;
    }

    @TestOnly
    public Pet(long id, boolean adotado) {
        this.id = id;
        this.adotado = adotado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pet pet = (Pet) o;
        return Objects.equals(id, pet.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    @TestOnly
    public void setId(long id) {
        this.id = id;
    }

    public TipoPet getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getRaca() {
        return raca;
    }

    public Integer getIdade() {
        return idade;
    }

    public String getCor() {
        return cor;
    }

    public Float getPeso() {
        return peso;
    }

    public boolean getAdotado() {
        return adotado;
    }

    public Abrigo getAbrigo() {
        return abrigo;
    }

    public Adocao getAdocao() {
        return adocao;
    }

    public void setAbrigo(@NotNull Abrigo abrigo) {
        this.abrigo = abrigo;
    }

    public void setAdotado(boolean adotado) {
        this.adotado = adotado;
    }
}
