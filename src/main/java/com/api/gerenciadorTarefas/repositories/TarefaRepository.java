package com.api.gerenciadorTarefas.repositories;

import com.api.gerenciadorTarefas.entities.TarefaEntidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TarefaRepository extends JpaRepository<TarefaEntidade, Long> {

    @Query("SELECT t FROM TarefaEntidade t WHERE t.idPessoa IS NULL ORDER BY t.prazo DESC limit 3")
    List<TarefaEntidade> buscarTarefaNaoAtribuida();

}
