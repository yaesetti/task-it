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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.awt.Color;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

public class PeriodicaTest {

    private Categoria categoria;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFinal;
    private Duration recorrencia;
    private TaskPeriodica task;

    @BeforeEach
    void setUp() throws DataInvalidaException {
        categoria = new Categoria("Título categ", "Desc categ", Color.BLUE);
        dataInicio = LocalDateTime.now().plusDays(1);
        dataFinal = LocalDateTime.now().plusMonths(1);
        recorrencia = Duration.ofDays(7);
        task = new TaskPeriodica("Título task", "Desc task", categoria, dataInicio, null, recorrencia, dataFinal);
        
    }


    // Teste relacionado ao construtor //

    @Test
    void testConstrutorCompleto() throws DataInvalidaException {
        
        assertEquals(recorrencia, task.getRecorrencia());
        assertEquals(dataFinal, task.getDataFinal());
        assertEquals(dataInicio, task.getData());
    }


    // Teste relacionado à negatividade da recorrrência //

    @Test
    void testRecorrenciaNegativa() {
        
        Duration recorrencia_neg = Duration.ofDays(-1);
        assertThrows(DataInvalidaException.class, () -> 
            new TaskPeriodica("T", "D", categoria, dataInicio, null, recorrencia_neg, dataFinal));
    }


    // Teste relacionado ao reinício da task //

    @Test
    void testResetTask() throws DataInvalidaException {
        
        task.adicionarSubtarefa("Título sub");
        task.setFeitoTodasSubtarefas(true); 
        task.setFeito(true);
        
        LocalDateTime dataOriginal = task.getData();


        task.resetTask();

        assertFalse(task.getFeito());
        assertFalse(task.getSubtarefas().get(0).getFeito());
        
        LocalDateTime dataEsperada = dataOriginal.plus(recorrencia);
        assertEquals(dataEsperada, task.getData());
    }

}
