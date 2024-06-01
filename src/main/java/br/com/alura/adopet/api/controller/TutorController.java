package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.tutor.SolicitacaoAtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.tutor.SolicitacaoCadastramentoTutorDto;
import br.com.alura.adopet.api.service.TutorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tutores")
public class TutorController extends ErrorHandlerController {

    @Autowired
    private TutorService tutorService;

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid SolicitacaoCadastramentoTutorDto dto) {
        tutorService.cadastrar(dto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<String> atualizar(
            @PathVariable("id") Long id,
            @RequestBody @Valid SolicitacaoAtualizacaoTutorDto dto
    ) {
        tutorService.atualizar(id, dto);
        return ResponseEntity.ok().build();
    }

}
