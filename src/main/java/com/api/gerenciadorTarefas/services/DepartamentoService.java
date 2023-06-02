package com.api.gerenciadorTarefas.services;

import com.api.gerenciadorTarefas.repositories.DepartamentoRepository;
import com.api.gerenciadorTarefas.dtos.DepartamentoDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DepartamentoService {

    private DepartamentoRepository departamentoRepository;
    private PessoaService pessoaService;
    private TarefaService tarefaService;

    public List<DepartamentoDto> listarDepartamentosPessoasTarefas() {
        List<Object[]> listaDepartamento = departamentoRepository.listarDepartamentosPessoasTarefas();
        List<DepartamentoDto> listaDepartamentoDTO = new ArrayList<>();

        for (Object[] departamento : listaDepartamento) {
            DepartamentoDto departamentoDto = new DepartamentoDto();
            departamentoDto.setTitulo((String) departamento[0]);
            departamentoDto.setQuantidadeTarefas((Long) departamento[1]);
            departamentoDto.setQuantidadePessoas((Long) departamento[2]);
            listaDepartamentoDTO.add(departamentoDto);
        }

        return listaDepartamentoDTO;
    }
}
