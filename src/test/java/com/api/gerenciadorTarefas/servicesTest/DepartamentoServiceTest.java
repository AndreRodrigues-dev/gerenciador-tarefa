package com.api.gerenciadorTarefas.servicesTest;
import com.api.gerenciadorTarefas.repositories.DepartamentoRepository;
import com.api.gerenciadorTarefas.dtos.DepartamentoDto;
import com.api.gerenciadorTarefas.services.DepartamentoService;
import com.api.gerenciadorTarefas.services.PessoaService;
import com.api.gerenciadorTarefas.services.TarefaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private TarefaService tarefaService;

    private DepartamentoService departamentoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        departamentoService = new DepartamentoService(departamentoRepository, pessoaService, tarefaService);
    }

    @Test
    public void testListarDepartamentosPessoasTarefas() {
        List<Object[]> listaDepartamento = new ArrayList<>();
        Object[] departamento1 = {"Departamento 1", 10L, 5L};
        Object[] departamento2 = {"Departamento 2", 8L, 3L};
        listaDepartamento.add(departamento1);
        listaDepartamento.add(departamento2);

        when(departamentoRepository.listarDepartamentosPessoasTarefas()).thenReturn(listaDepartamento);

        List<DepartamentoDto> listaDepartamentoDTO = departamentoService.listarDepartamentosPessoasTarefas();

        assertEquals(2, listaDepartamentoDTO.size());

        DepartamentoDto departamentoDto1 = listaDepartamentoDTO.get(0);
        assertEquals("Departamento 1", departamentoDto1.getTitulo());
        assertEquals(10L, departamentoDto1.getQuantidadeTarefas());
        assertEquals(5L, departamentoDto1.getQuantidadePessoas());

        DepartamentoDto departamentoDto2 = listaDepartamentoDTO.get(1);
        assertEquals("Departamento 2", departamentoDto2.getTitulo());
        assertEquals(8L, departamentoDto2.getQuantidadeTarefas());
        assertEquals(3L, departamentoDto2.getQuantidadePessoas());
    }
}

