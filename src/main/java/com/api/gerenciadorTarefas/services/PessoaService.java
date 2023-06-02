package com.api.gerenciadorTarefas.services;

import com.api.gerenciadorTarefas.repositories.PessoaRepository;
import com.api.gerenciadorTarefas.dtos.PessoaDto;
import com.api.gerenciadorTarefas.entities.PessoaEntidade;
import com.api.gerenciadorTarefas.entities.TarefaEntidade;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PessoaService {

    private PessoaRepository pessoaRepository;

    @Transactional
    public PessoaEntidade adicionarPessoa(PessoaEntidade pessoaEntidade) {
        return pessoaRepository.save(pessoaEntidade);
    }

    public PessoaEntidade alterarPessoa(Long id, PessoaDto pessoaDto) {
        Optional<PessoaEntidade> pessoaEntidadeOptional = pessoaRepository.findById(id);
        var pessoaEntidade = pessoaEntidadeOptional.get();
        pessoaEntidade.setNome(pessoaDto.getNome());
        pessoaEntidade.setIdDepartamento(pessoaDto.getIdDepartamento());
        return pessoaRepository.save(pessoaEntidade);
    }

    public Optional<PessoaEntidade> buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id);
    }

    public List<PessoaEntidade> listarPessoas() {
        return pessoaRepository.findAll();
    }

    @Transactional
    public void deletarPessoa(Long id) {
        pessoaRepository.deleteById(id);
    }

    public List<PessoaDto> buscarPessoasTotalHoras() {
        List<PessoaEntidade> pessoas = pessoaRepository.findAll();
        List<PessoaDto> pessoasDtos = new ArrayList<>();
        for(PessoaEntidade pessoa : pessoas) {
            PessoaDto pessoaDto = new PessoaDto();
            pessoaDto.setNome(pessoa.getNome());
            pessoaDto.setIdDepartamento(pessoa.getIdDepartamento());
            pessoaDto.setTotalHorasGastas(pessoa.getListaTarefas()
                    .stream()
                    .mapToDouble(TarefaEntidade::getDuracao)
                    .sum());
            pessoasDtos.add(pessoaDto);
        }
        return pessoasDtos;
    }

    public List<Object> buscarPessoasGastos(String nome, LocalDate dataInicial, LocalDate dataFinal){
        return pessoaRepository.buscarPessoaGastos(nome,dataInicial,dataFinal);
    }
}