package br.com.alura.adopet.api.validacoes.tutor.atualizacao;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoAtualizacaoTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OutroTutorJaExisteComEmailValidacao implements SolicitacaoAtualizacaoTutorValidacao {

    @Autowired
    private TutorRepository repository;

    @Override
    public void validar(long tutorId, SolicitacaoAtualizacaoTutorDto dto) {
        if (repository.existeOutroTutorComEsseEmail(tutorId, dto.email())) {
            throw new ValidacaoException("Outro tutor já possuí esse email cadastrado!");
        }
    }
}
