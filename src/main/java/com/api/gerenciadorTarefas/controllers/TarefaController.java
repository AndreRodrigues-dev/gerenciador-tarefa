package com.api.gerenciadorTarefas.controllers;

import com.api.gerenciadorTarefas.dtos.TarefaDto;
import com.api.gerenciadorTarefas.entities.TarefaEntidade;
import com.api.gerenciadorTarefas.services.PessoaService;
import com.api.gerenciadorTarefas.services.TarefaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/tarefas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TarefaController {
    private TarefaService tarefaService;

    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Object> adicionarTarefa(@RequestBody TarefaDto tarefaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tarefaService.adicionarTarefa(tarefaDto));
    }

    @PutMapping("/finalizar/{id}")
    public ResponseEntity<Object> finalizarTarefa(@PathVariable Long id) {
        Optional<TarefaEntidade> tarefaEntidadeOptional = tarefaService.buscarTarefaPorId(id);
        if(!tarefaEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.finalizarTarefa(id));
    }

    @PutMapping("/alocar/{id}")
    public ResponseEntity<Object> alocarPessoa(@PathVariable Long id) {
        Optional<TarefaEntidade> tarefaEntidadeOptional = tarefaService.buscarTarefaPorId(id);
        if(!tarefaEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.alocarPessoa(id, tarefaEntidadeOptional));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarTarefaPorid(@PathVariable Long id) {
        Optional<TarefaEntidade> tarefaEntidadeOptional = tarefaService.buscarTarefaPorId(id);
        if(!tarefaEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tarefa não encontrada");
        }
        return ResponseEntity.status(HttpStatus.OK).body(tarefaEntidadeOptional.get());
    }
    @GetMapping("/pendentes")
    public ResponseEntity<List<TarefaDto>> buscarTarefasNaoAtribuidas(){
        return ResponseEntity.status(HttpStatus.OK).body(tarefaService.buscarTarefaNaoAtribuida());
    }
}