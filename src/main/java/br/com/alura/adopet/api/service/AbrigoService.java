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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AbrigoService {

    @Autowired
    private AbrigoRepository repository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private List<SolicitacaoCadastramentoAbrigoValidacao> validacoes;

    public void cadastrar(SolicitacaoCadastramentoAbrigoDto abrigo) {
        validacoes.forEach(validacao -> validacao.validar(abrigo));

        repository.save(new Abrigo(
                abrigo.nome(),
                abrigo.email(),
                abrigo.telefone()
        ));
    }

    public List<DetalhesPetDto> listarPets(@PathVariable long id) {
        return repository.getReferenceById(id)
                .getPets()
                .stream()
                .map(DetalhesPetDto::new)
                .toList();
    }


    public DetalhesPetDto cadastrarPet(
            long abrigoId,
            SolicitacaoCadastramentoPetoDto dto
    ) {
        Abrigo abrigo = repository.getReferenceById(abrigoId);
        Pet pet = new Pet(
                TipoPet.valueOf(dto.tipo().trim().toUpperCase()),
                dto.nome(),
                dto.raca(),
                dto.idade(),
                dto.cor(),
                dto.peso(),
                abrigo
        );
        petRepository.save(pet);
        return new DetalhesPetDto(
                pet
        );
    }

    public List<DetalhesAbrigoDto> listarTodos() {
        return repository.findAll()
                .stream()
                .map(DetalhesAbrigoDto::new)
                .toList();
    }
}
