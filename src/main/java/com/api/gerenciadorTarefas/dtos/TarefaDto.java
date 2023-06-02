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
public class TarefaDto {
    private String titulo;
    private String descricao;
    private LocalDate prazo;
    private Long idDepartamento;
    private int duracao;
    private Long idPessoa;
    private boolean finalizado;

}
