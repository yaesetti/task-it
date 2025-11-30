package task_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.tasks.*;
import com.usuarios.*;
import com.excecoes.DataInvalidaException;
import com.excecoes.UsuarioInvalidoException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class TaskTest {

    private TaskPadrao task;
    private Categoria categoria;
    private LocalDateTime dataFutura;
    private Subtarefa sub;

    @BeforeEach
    void setUp() throws DataInvalidaException {
        categoria = new Categoria("Título categ", "Desc categ", Color.BLACK);
        dataFutura = LocalDateTime.now().plusDays(5); 
        task = new TaskPadrao("Task 1", "Desc", categoria, dataFutura, null);
        sub = new Subtarefa("Titulo sub", "Descricao sub");

    }

    
    // Testes relacionados a datas e suas exceções //

    @Test
    void testSetDataValida() throws DataInvalidaException {
        LocalDateTime novaData = LocalDateTime.now().plusDays(10);
        task.setData(novaData);
        assertEquals(novaData, task.getData());
    }

    @Test
    void testCalcularTempoRestante() {
        Duration restante = task.calcularTempoRestante();
       
        assertFalse(restante.isNegative());
        assertFalse(restante.isZero());
        assertTrue(restante.compareTo(Duration.ofDays(5)) < 0);
    }

    @Test
    void testConstrutorDataPassado() {
        LocalDateTime dataPassada = LocalDateTime.now().minusDays(1);
        assertThrows(DataInvalidaException.class, () -> {
            new TaskPadrao("Titulo", "Desc", categoria, dataPassada, null);
        });
    }


    // Testes relacionados às subtarefas //

    @Test
    void testAdicionarSubtarefa() {
        task.adicionarSubtarefa("Sub 1", "Desc 1");
        task.adicionarSubtarefa("Sub 2", "Desc 2"); 
        
        List<Subtarefa> subs = task.getSubtarefas();
        assertEquals(2, subs.size());
        assertEquals("Sub 1", subs.get(0).getTitulo());
    }

    @Test
    void testSetFeitoTodasSubtarefas() {
        task.adicionarSubtarefa("S1");
        task.adicionarSubtarefa("S2");

        task.setFeitoTodasSubtarefas(true);

        assertTrue(task.getSubtarefas().get(0).getFeito());
        assertTrue(task.getSubtarefas().get(1).getFeito());
    }
    
    @Test
    void testInicializacaoSubtarefa() {
    
        assertNotNull(sub.getId());
        assertEquals("Titulo sub", sub.getTitulo());
        assertFalse(sub.getFeito());
    }

    @Test
    void testTrocarFeito() {
        
        sub.trocarFeito();
        assertTrue(sub.getFeito());

        sub.trocarFeito();
        assertFalse(sub.getFeito());
    }

    @Test
    void testRemoverSubtarefas() {
        task.adicionarSubtarefa("Sub 1", "D1");
        assertFalse(task.getSubtarefas().isEmpty());

        UUID idSub = task.getSubtarefas().get(0).getId();
        boolean removeu = task.excluirSubtarefa(idSub);
        assertTrue(removeu);
    }


    // Testes relacionados às categorias //

    @Test
    void testCriacaoCategoria() {
        
        assertEquals("Título categ", categoria.getNome());
        assertEquals("Desc categ", categoria.getDescricao());
        assertEquals(Color.BLACK, categoria.getCor());
    }

    @Test
    void testSettersCategoria() {
        categoria.setNome("Novo Nome");
        categoria.setDescricao("Nova Desc");
        categoria.setCor(Color.GREEN);

        assertEquals("Novo Nome", categoria.getNome());
        assertEquals("Nova Desc", categoria.getDescricao());
        assertEquals(Color.GREEN, categoria.getCor());
    }


}
