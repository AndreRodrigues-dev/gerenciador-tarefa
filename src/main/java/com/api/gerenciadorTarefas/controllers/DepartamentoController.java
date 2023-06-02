package com.api.gerenciadorTarefas.controllers;

import com.api.gerenciadorTarefas.dtos.DepartamentoDto;
import com.api.gerenciadorTarefas.services.DepartamentoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/departamentos")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DepartamentoController {

    private DepartamentoService departamentoService;

    @GetMapping
    public List<DepartamentoDto> listarDepartamentosPessoasTarefas() {
        return departamentoService.listarDepartamentosPessoasTarefas();
    }
}
