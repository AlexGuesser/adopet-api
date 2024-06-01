package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.DetalhesAbrigoDto;
import br.com.alura.adopet.api.dto.SolicitacaoCadastramentoPetoDto;
import br.com.alura.adopet.api.dto.abrigo.SolicitacaoCadastramentoAbrigoDto;
import br.com.alura.adopet.api.dto.pet.DetalhesPetDto;
import br.com.alura.adopet.api.service.AbrigoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/abrigos")
public class AbrigoController extends ErrorHandlerController {

    @Autowired
    private AbrigoService service;

    @GetMapping
    public ResponseEntity<List<DetalhesAbrigoDto>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<String> cadastrar(@RequestBody @Valid SolicitacaoCadastramentoAbrigoDto dto) {
        service.cadastrar(dto);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/pets")
    public ResponseEntity<List<DetalhesPetDto>> listarPets(@PathVariable long id) {
        return ResponseEntity.ok(
                service.listarPets(id)
        );
    }

    @PostMapping("/{id}/pets")
    @Transactional
    public ResponseEntity<DetalhesPetDto> cadastrarPet(
            @PathVariable long id,
            @RequestBody @Valid SolicitacaoCadastramentoPetoDto dto
    ) {
        return ResponseEntity.ok(
                service.cadastrarPet(id, dto)
        );
    }

}
