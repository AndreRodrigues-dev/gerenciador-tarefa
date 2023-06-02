package com.api.gerenciadorTarefas.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "registro_pessoa")
public class PessoaEntidade implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nome;
    @Column(nullable = false, name = "id_departamento")
    private Long idDepartamento;
    @Column(nullable = false, name = "lista_tarefas")
    @OneToMany
    @JsonIgnore
    private List<TarefaEntidade> listaTarefas;
}
