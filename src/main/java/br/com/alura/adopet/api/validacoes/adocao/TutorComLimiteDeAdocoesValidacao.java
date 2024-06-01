package br.com.alura.adopet.api.validacoes.adocao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TutorComLimiteDeAdocoesValidacao implements SolicitacaoAdocaoValidacao {

    @Autowired
    private AdocaoRepository repository;

    public void validar(SolicitacaoAdocaoDto dto) {
        if (repository.numeroDeAdocoesPorTutor(dto.idTutor()) >= 5) {
            throw new ValidacaoException("Tutor chegou ao limite máximo de 5 adoções!");
        }
    }

}
