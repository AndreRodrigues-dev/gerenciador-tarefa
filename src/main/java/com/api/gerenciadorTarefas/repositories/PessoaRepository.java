package com.api.gerenciadorTarefas.repositories;

import com.api.gerenciadorTarefas.entities.PessoaEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaEntidade, Long> {
    @Query("SELECT p.nome, FUNCTION('ROUND', AVG(t.duracao), 2) as mediaHorasGastas FROM PessoaEntidade p join TarefaEntidade t on p.id = t.idPessoa " +
            "WHERE p.nome = :nome AND t.prazo >= :dataInicial AND t.prazo <= :dataFinal GROUP BY p.nome")
    List<Object> buscarPessoaGastos(@Param("nome") String nome, @Param("dataInicial") LocalDate dataInicial, @Param("dataFinal") LocalDate dataFinal);
}
