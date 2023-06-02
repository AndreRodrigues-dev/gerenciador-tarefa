package com.api.gerenciadorTarefas.services;

import com.api.gerenciadorTarefas.repositories.TarefaRepository;
import com.api.gerenciadorTarefas.dtos.TarefaDto;
import com.api.gerenciadorTarefas.entities.PessoaEntidade;
import com.api.gerenciadorTarefas.entities.TarefaEntidade;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class TarefaService {
    private TarefaRepository tarefaRepository;
    private PessoaService pessoaService;

    @Transactional
    public TarefaEntidade adicionarTarefa(TarefaDto tarefaDto) {
        var tarefaEntidade = new TarefaEntidade();
        BeanUtils.copyProperties(tarefaDto, tarefaEntidade);
        if(tarefaEntidade.getIdPessoa() != null) {
            var pessoaEntidade = pessoaService.buscarPessoaPorId(tarefaEntidade.getIdPessoa());
            pessoaEntidade.get().getListaTarefas().add(tarefaEntidade);
        }
        return tarefaRepository.save(tarefaEntidade);
    }

    public Optional<TarefaEntidade> buscarTarefaPorId(Long id) {
        return tarefaRepository.findById(id);
    }

    public TarefaEntidade finalizarTarefa(Long id) {
        Optional<TarefaEntidade> tarefaEntidadeOptional = tarefaRepository.findById(id);
        var tarefaEntidade = tarefaEntidadeOptional.get();
        tarefaEntidade.setFinalizado(true);
        return tarefaRepository.save(tarefaEntidade);
    }

    public List<TarefaDto> buscarTarefaNaoAtribuida() {
        List<TarefaDto> tarefaDTOs = new ArrayList<TarefaDto>();
        List<TarefaEntidade> tarefaEntidades = tarefaRepository.buscarTarefaNaoAtribuida();
        for (TarefaEntidade tarefaEntidade : tarefaEntidades) {
            TarefaDto tarefaDto = new TarefaDto();
            tarefaDto.setTitulo(tarefaEntidade.getTitulo());
            tarefaDto.setDescricao(tarefaEntidade.getDescricao());
            tarefaDto.setPrazo(tarefaEntidade.getPrazo());
            tarefaDto.setIdDepartamento(tarefaEntidade.getIdDepartamento());
            tarefaDto.setDuracao(tarefaEntidade.getDuracao());
            tarefaDto.setIdPessoa(tarefaEntidade.getIdPessoa());
            tarefaDto.setFinalizado(tarefaEntidade.getFinalizado());

            tarefaDTOs.add(tarefaDto);
        }
        return tarefaDTOs;
    }

    public TarefaEntidade alocarPessoa(Long id, Optional<TarefaEntidade> tarefaEntidadeOptional) {
        TarefaEntidade tarefaEntidade = tarefaEntidadeOptional.orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada"));
        List<PessoaEntidade> pessoas = pessoaService.listarPessoas();

        for (PessoaEntidade pessoa : pessoas) {
            if (pessoa.getIdDepartamento().equals(tarefaEntidade.getIdDepartamento())) {
                tarefaEntidade.setIdPessoa(pessoa.getId());
                pessoa.getListaTarefas().add(tarefaEntidade);
                return tarefaRepository.save(tarefaEntidade);
            }
        }
        throw new IllegalStateException("Nenhuma pessoa disponível para alocar a tarefa");
    }

    public List<TarefaEntidade> listarTarefas() {
        return tarefaRepository.findAll();
    }
}
