package br.com.alura.adopet.api.validacoes.abrigo;

import br.com.alura.adopet.api.dto.abrigo.SolicitacaoCadastramentoAbrigoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbrigoJaExisteComTelefoneValidacao implements SolicitacaoCadastramentoAbrigoValidacao {

    @Autowired
    private AbrigoRepository repository;

    @Override
    public void validar(SolicitacaoCadastramentoAbrigoDto dto) {
        if (repository.existsByTelefone(dto.telefone())) {
            throw new ValidacaoException("Já existe um abrigo cadastrado com esse telefone!");
        }
    }
}
