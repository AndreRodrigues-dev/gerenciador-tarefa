package com.api.gerenciadorTarefas.controllers;

import com.api.gerenciadorTarefas.services.PessoaService;
import com.api.gerenciadorTarefas.dtos.PessoaDto;
import com.api.gerenciadorTarefas.entities.PessoaEntidade;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pessoas")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaController {

    private PessoaService pessoaService;

    @PostMapping
    public ResponseEntity<Object> adicionarPessoa(@RequestBody PessoaDto pessoaDto) {
        var pessoaEntidade = new PessoaEntidade();
        BeanUtils.copyProperties(pessoaDto, pessoaEntidade);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.adicionarPessoa(pessoaEntidade));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> alterarPessoa(@PathVariable Long id, @RequestBody PessoaDto pessoaDto) {
        Optional<PessoaEntidade> pessoaEntidadeOptional = pessoaService.buscarPessoaPorId(id);
        if(!pessoaEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro da pessoa não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.alterarPessoa(id, pessoaDto));
    }

    @GetMapping("/listar")
    public ResponseEntity<List<PessoaEntidade>> listarPessoas() {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.listarPessoas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> buscarPessoaPorid(@PathVariable Long id) {
        Optional<PessoaEntidade> pessoaEntidadeOptional = pessoaService.buscarPessoaPorId(id);
        if(!pessoaEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro da pessoa não encontrado");
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaEntidadeOptional.get());
    }

    @GetMapping
    public ResponseEntity<List<PessoaDto>> buscarPessoasTotalHoras() {
        List<PessoaDto> pessoasDtos = pessoaService.buscarPessoasTotalHoras();
        return ResponseEntity.status(HttpStatus.OK).body(pessoasDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletarPessoa(@PathVariable Long id) {
        Optional<PessoaEntidade> pessoaEntidadeOptional = pessoaService.buscarPessoaPorId(id);
        if(!pessoaEntidadeOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cadastro da pessoa não encontrado");
        }
        pessoaService.deletarPessoa(id);
        return ResponseEntity.status(HttpStatus.OK).body("Cadastro da pessoa deletado");
    }

    @GetMapping("/gastos")
    public ResponseEntity<List<Object>> buscarPessoasGastos(@RequestBody PessoaDto pessoaDto) {
        return ResponseEntity.status(HttpStatus.OK).body(pessoaService.buscarPessoasGastos(pessoaDto.getNome(), pessoaDto.getDataInicial(),pessoaDto.getDataFinal()));
    }
}