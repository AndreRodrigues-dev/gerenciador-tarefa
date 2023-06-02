package com.api.gerenciadorTarefas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaDto {
    private String nome;
    private Long idDepartamento;
    private double totalHorasGastas;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
}
