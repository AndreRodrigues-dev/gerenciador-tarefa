package com.api.gerenciadorTarefas.repositories;

import com.api.gerenciadorTarefas.entities.DepartamentoEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<DepartamentoEntidade, Long> {
    @Query("select d.titulo, count(t.id), count(DISTINCT p.id) from DepartamentoEntidade d " +
            "left join TarefaEntidade t on d.id = t.idDepartamento " +
            "left join PessoaEntidade p on d.id = p.idDepartamento group by d.titulo")
    List<Object[]> listarDepartamentosPessoasTarefas();
}
