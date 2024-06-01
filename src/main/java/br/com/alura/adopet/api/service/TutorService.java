package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoAtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.SolicitacaoCadastramentoTutorDto;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.TutorRepository;
import br.com.alura.adopet.api.validacoes.tutor.atualizacao.SolicitacaoAtualizacaoTutorValidacao;
import br.com.alura.adopet.api.validacoes.tutor.cadastramento.SolicitacaoCadastramentoTutorValidacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TutorService {

    @Autowired
    private TutorRepository repository;

    @Autowired
    List<SolicitacaoCadastramentoTutorValidacao> validacoesCadastramento;

    @Autowired
    List<SolicitacaoAtualizacaoTutorValidacao> validacoesAtualizacao;

    @Transactional
    public void cadastrar(SolicitacaoCadastramentoTutorDto dto) {
        validacoesCadastramento.forEach(v -> v.validar(dto));

        Tutor tutor = new Tutor(
                dto.nome(),
                dto.telefone(),
                dto.email()
        );

        repository.save(tutor);
    }


    @Transactional
    public void atualizar(Long id, SolicitacaoAtualizacaoTutorDto dto) {
        validacoesAtualizacao.forEach(v -> v.validar(id, dto));

        Tutor tutor = repository.getReferenceById(id);
        tutor.setTelefone(dto.telefone());
        tutor.setEmail(dto.email());
    }
}
