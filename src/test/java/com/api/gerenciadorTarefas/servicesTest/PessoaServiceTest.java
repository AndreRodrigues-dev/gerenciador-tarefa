package com.api.gerenciadorTarefas.servicesTest;
import com.api.gerenciadorTarefas.repositories.PessoaRepository;
import com.api.gerenciadorTarefas.dtos.PessoaDto;
import com.api.gerenciadorTarefas.entities.PessoaEntidade;
import com.api.gerenciadorTarefas.entities.TarefaEntidade;
import com.api.gerenciadorTarefas.services.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PessoaServiceTest {

    @Mock
    private PessoaRepository pessoaRepository;

    private PessoaService pessoaService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        pessoaService = new PessoaService(pessoaRepository);
    }

    @Test
    public void testAdicionarPessoa() {
        PessoaEntidade pessoaEntidade = new PessoaEntidade();
        when(pessoaRepository.save(pessoaEntidade)).thenReturn(pessoaEntidade);

        PessoaEntidade result = pessoaService.adicionarPessoa(pessoaEntidade);

        assertEquals(pessoaEntidade, result);
        verify(pessoaRepository, times(1)).save(pessoaEntidade);
    }

    @Test
    public void testAlterarPessoa() {
        Long id = 1L;
        PessoaDto pessoaDto = new PessoaDto();
        PessoaEntidade pessoaEntidade = new PessoaEntidade();
        pessoaEntidade.setId(id);

        Optional<PessoaEntidade> pessoaEntidadeOptional = Optional.of(pessoaEntidade);
        when(pessoaRepository.findById(id)).thenReturn(pessoaEntidadeOptional);
        when(pessoaRepository.save(pessoaEntidade)).thenReturn(pessoaEntidade);

        PessoaEntidade result = pessoaService.alterarPessoa(id, pessoaDto);

        assertEquals(pessoaEntidade, result);
        verify(pessoaRepository, times(1)).findById(id);
        verify(pessoaRepository, times(1)).save(pessoaEntidade);
    }

    @Test
    public void testBuscarPessoaPorId() {
        Long id = 1L;
        PessoaEntidade pessoaEntidade = new PessoaEntidade();
        Optional<PessoaEntidade> pessoaEntidadeOptional = Optional.of(pessoaEntidade);
        when(pessoaRepository.findById(id)).thenReturn(pessoaEntidadeOptional);

        Optional<PessoaEntidade> result = pessoaService.buscarPessoaPorId(id);

        assertEquals(pessoaEntidadeOptional, result);
        verify(pessoaRepository, times(1)).findById(id);
    }

    @Test
    public void testListarPessoas() {
        List<PessoaEntidade> pessoas = new ArrayList<>();
        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<PessoaEntidade> result = pessoaService.listarPessoas();

        assertEquals(pessoas, result);
        verify(pessoaRepository, times(1)).findAll();
    }

    @Test
    public void testDeletarPessoa() {
        Long id = 1L;
        doNothing().when(pessoaRepository).deleteById(id);

        pessoaService.deletarPessoa(id);

        verify(pessoaRepository, times(1)).deleteById(id);
    }

    @Test
    public void testBuscarPessoasTotalHoras() {
        PessoaEntidade pessoa1 = new PessoaEntidade();
        pessoa1.setNome("Pessoa 1");
        pessoa1.setListaTarefas(new ArrayList<>());

        TarefaEntidade tarefa1 = new TarefaEntidade();
        tarefa1.setDuracao(2);
        pessoa1.getListaTarefas().add(tarefa1);

        PessoaEntidade pessoa2 = new PessoaEntidade();
        pessoa2.setNome("Pessoa 2");
        pessoa2.setListaTarefas(new ArrayList<>());

        TarefaEntidade tarefa2 = new TarefaEntidade();
        tarefa2.setDuracao(3);
        pessoa2.getListaTarefas().add(tarefa2);

        List<PessoaEntidade> pessoas = new ArrayList<>();
        pessoas.add(pessoa1);
        pessoas.add(pessoa2);

        when(pessoaRepository.findAll()).thenReturn(pessoas);

        List<PessoaDto> result = pessoaService.buscarPessoasTotalHoras();

        assertEquals(2, result.size());

        PessoaDto pessoaDto1 = result.get(0);
        assertEquals("Pessoa 1", pessoaDto1.getNome());
        assertEquals(2, pessoaDto1.getTotalHorasGastas());

        PessoaDto pessoaDto2 = result.get(1);
        assertEquals("Pessoa 2", pessoaDto2.getNome());
        assertEquals(3, pessoaDto2.getTotalHorasGastas());

        verify(pessoaRepository, times(1)).findAll();
    }


    @Test
    public void testBuscarPessoasGastos() {
        String nome = "Pessoa";
        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = LocalDate.now();

        List<Object> gastos = new ArrayList<>();
        when(pessoaRepository.buscarPessoaGastos(nome, dataInicial, dataFinal)).thenReturn(gastos);

        List<Object> result = pessoaService.buscarPessoasGastos(nome, dataInicial, dataFinal);

        assertEquals(gastos, result);
        verify(pessoaRepository, times(1)).buscarPessoaGastos(nome, dataInicial, dataFinal);
    }
}

