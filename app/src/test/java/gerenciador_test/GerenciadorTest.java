package gerenciador_test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.gerenciadores.*;
import com.tasks.*;
import com.usuarios.*;
import com.serializacao.*;
import com.excecoes.*;

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

public class GerenciadorTest {
    
    private GerenciadorDeTasks gerTasks;
    private Categoria categoria;
    private UsuarioPadrao usuarioCriador;

    @BeforeEach
    void setUp() {
        gerTasks = new GerenciadorDeTasks();
        usuarioCriador = new UsuarioPadrao("Criador", "123", null);
        categoria = new Categoria("Geral", "Teste", Color.BLACK);
    }

    @Test
    void testCategorias() throws NomeDuplicadoException {
        gerTasks.criarCategoria("Trabalho", Color.RED);
        
        assertEquals(1, gerTasks.getCategorias().size());
        assertEquals("Trabalho", gerTasks.getCategorias().get(0).getNome());

        // Teste de duplicata
        assertThrows(NomeDuplicadoException.class, () -> {
            gerTasks.criarCategoria("Trabalho", "Outra desc", Color.BLUE);
        });

        boolean removeu = gerTasks.apagarCategoria("Trabalho");            
        
        assertTrue(removeu);
        assertTrue(gerTasks.getCategorias().isEmpty());
    }

    @Test
    void testCriarTaskPadrao() {
        LocalDateTime dataFutura = LocalDateTime.now().plusDays(1);
        
        Task task = gerTasks.criarTask("Titulo", "Desc", categoria, dataFutura, usuarioCriador);

        assertNotNull(task);
        assertEquals(1, gerTasks.getTasks().size());
        // Verifica se o usuário criador foi adicionado como dono
        assertTrue(task.getUsuariosDonos().contains(usuarioCriador));
        // Verifica se a task foi adicionada à lista de tasks do usuário
        // assertTrue(usuarioCriador.getTaskIds().contains(task.getId()));
    }

}
