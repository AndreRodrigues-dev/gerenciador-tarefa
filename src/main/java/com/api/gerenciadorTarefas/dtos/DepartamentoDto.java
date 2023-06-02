package com.api.gerenciadorTarefas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoDto {
    private String titulo;
    private Long quantidadePessoas;
    private Long quantidadeTarefas;
}
