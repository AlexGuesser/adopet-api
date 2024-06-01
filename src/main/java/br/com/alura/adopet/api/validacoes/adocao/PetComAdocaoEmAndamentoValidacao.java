package br.com.alura.adopet.api.validacoes.adocao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PetComAdocaoEmAndamentoValidacao implements SolicitacaoAdocaoValidacao {

    @Autowired
    private AdocaoRepository repository;

    public void validar(SolicitacaoAdocaoDto dto) {
        if (repository.existsByPetIdAndStatus(dto.idPet(), StatusAdocao.AGUARDANDO_AVALIACAO))
            throw new ValidacaoException("Pet já está aguardando avaliação para ser adotado!");
    }

}
