package com.api.gerenciadorTarefas.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registro_tarefa")
public class TarefaEntidade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String descricao;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate prazo;
    @Column(nullable = false, name = "id_departamento")
    private Long idDepartamento;
    @Column(nullable = false)
    private int duracao;
    private Long idPessoa;
    @Column(nullable = false)
    private Boolean finalizado;
}
