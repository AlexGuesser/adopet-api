package br.com.alura.adopet.api.validacoes.tutor.cadastramento;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoCadastramentoTutorDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TutorJaExisteComEmailValidacao implements SolicitacaoCadastramentoTutorValidacao {

    @Autowired
    private TutorRepository repository;

    @Override
    public void validar(SolicitacaoCadastramentoTutorDto dto) {
        if (repository.existsByEmail(dto.email())) {
            throw new ValidacaoException("Já existe um tutor cadastrado com esse email!");
        }
    }
}
