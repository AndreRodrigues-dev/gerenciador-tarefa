package com.api.gerenciadorTarefas.servicesTest;

import com.api.gerenciadorTarefas.dtos.TarefaDto;
import com.api.gerenciadorTarefas.entities.PessoaEntidade;
import com.api.gerenciadorTarefas.entities.TarefaEntidade;
import com.api.gerenciadorTarefas.repositories.TarefaRepository;
import com.api.gerenciadorTarefas.services.PessoaService;
import com.api.gerenciadorTarefas.services.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class TarefaServiceTest {

    @Mock
    private TarefaRepository tarefaRepository;

    @Mock
    private PessoaService pessoaService;

    @InjectMocks
    private TarefaService tarefaService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAdicionarTarefa() {
        TarefaDto tarefaDto = new TarefaDto();

        TarefaEntidade tarefaEntidade = new TarefaEntidade();

        when(tarefaRepository.save(any(TarefaEntidade.class))).thenReturn(tarefaEntidade);

        TarefaEntidade result = tarefaService.adicionarTarefa(tarefaDto);

        assertEquals(tarefaEntidade, result);
        verify(tarefaRepository, times(1)).save(any(TarefaEntidade.class));
    }

    @Test
    public void testBuscarTarefaPorId() {
        Long id = 1L;
        TarefaEntidade tarefaEntidade = new TarefaEntidade();

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaEntidade));

        Optional<TarefaEntidade> result = tarefaService.buscarTarefaPorId(id);

        assertTrue(result.isPresent());
        assertEquals(tarefaEntidade, result.get());
        verify(tarefaRepository, times(1)).findById(id);
    }

    @Test
    public void testFinalizarTarefa() {
        Long id = 1L;
        TarefaEntidade tarefaEntidade = new TarefaEntidade();

        when(tarefaRepository.findById(id)).thenReturn(Optional.of(tarefaEntidade));
        when(tarefaRepository.save(any(TarefaEntidade.class))).thenReturn(tarefaEntidade);

        TarefaEntidade result = tarefaService.finalizarTarefa(id);

        assertTrue(result.getFinalizado());
        verify(tarefaRepository, times(1)).findById(id);
        verify(tarefaRepository, times(1)).save(any(TarefaEntidade.class));
    }

    @Test
    public void testBuscarTarefaNaoAtribuida() {
        List<TarefaEntidade> tarefaEntidades = new ArrayList<>();

        when(tarefaRepository.buscarTarefaNaoAtribuida()).thenReturn(tarefaEntidades);

        List<TarefaDto> result = tarefaService.buscarTarefaNaoAtribuida();

        assertEquals(tarefaEntidades.size(), result.size());
        verify(tarefaRepository, times(1)).buscarTarefaNaoAtribuida();
    }
    @Test
    public void testAlocarPessoa() {
        Long id = 1L;
        TarefaEntidade tarefaEntidade = new TarefaEntidade();
        tarefaEntidade.setIdDepartamento(1L);

        PessoaEntidade pessoaEntidade = new PessoaEntidade();
        pessoaEntidade.setId(1L);
        pessoaEntidade.setIdDepartamento(tarefaEntidade.getIdDepartamento());
        pessoaEntidade.setListaTarefas(new ArrayList<>());
        List<PessoaEntidade> pessoas = new ArrayList<>();
        pessoas.add(pessoaEntidade);

        when(pessoaService.listarPessoas()).thenReturn(pessoas);
        when(tarefaRepository.save(any(TarefaEntidade.class))).thenReturn(tarefaEntidade);

        TarefaEntidade result = tarefaService.alocarPessoa(id, Optional.of(tarefaEntidade));

        assertEquals(pessoaEntidade.getId(), result.getIdPessoa());
        assertTrue(pessoaEntidade.getListaTarefas().contains(result));
        verify(pessoaService, times(1)).listarPessoas();
        verify(tarefaRepository, times(1)).save(any(TarefaEntidade.class));
    }

    @Test
    public void testListarTarefas() {
        List<TarefaEntidade> tarefaEntidades = new ArrayList<>();

        when(tarefaRepository.findAll()).thenReturn(tarefaEntidades);

        List<TarefaEntidade> result = tarefaService.listarTarefas();

        assertEquals(tarefaEntidades.size(), result.size());
        verify(tarefaRepository, times(1)).findAll();
    }
}

