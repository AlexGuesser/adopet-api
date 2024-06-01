package br.com.alura.adopet.api.validacoes.adocao;

import br.com.alura.adopet.api.dto.adocao.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class TutorComAdocaoEmAndamentoValidacao implements SolicitacaoAdocaoValidacao {

    @Autowired
    private AdocaoRepository repository;


    public void validar(SolicitacaoAdocaoDto dto) {
        if (repository.existsByTutorIdAndStatus(dto.idTutor(), StatusAdocao.AGUARDANDO_AVALIACAO)) {
            throw new ValidacaoException("Tutor já possuí uma adoção em andamento!");
        }
    }

}
