package br.com.alura.adopet.api.validacoes.tutor.atualizacao;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoAtualizacaoTutorDto;

public interface SolicitacaoAtualizacaoTutorValidacao {

    void validar(long tutorId, SolicitacaoAtualizacaoTutorDto dto);

}
