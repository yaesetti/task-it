package com.gerenciadores;

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

public class GerenciadorDeTasksTest {
    
    private GerenciadorDeTasks gerenciador;
    private Categoria categoria;
    private UsuarioPadrao usuarioCriador;

    @BeforeEach
    void setUp() {
        gerenciador = new GerenciadorDeTasks();
        usuarioCriador = new UsuarioPadrao("Criador", "123", null);
        categoria = new Categoria("Geral", "Teste", Color.BLACK);
    }


    // Teste relacionado à criação e exclusão de uma categoria //

    @Test
    void testCategorias() throws NomeDuplicadoException {
        gerenciador.criarCategoria("Categoria 1", Color.RED);
        
        assertEquals(1, gerenciador.getCategorias().size());
        assertEquals("Categoria 1", gerenciador.getCategorias().get(0).getNome());

        assertThrows(NomeDuplicadoException.class, () -> {
            gerenciador.criarCategoria("Categoria 1", "Desc categ", Color.BLUE);
        });

        boolean removeu = gerenciador.apagarCategoria("Categoria 1");            
        
        assertTrue(removeu);
        assertTrue(gerenciador.getCategorias().isEmpty());
    }


    // Teste relacionado à criação de uma TaskPadrão a partir de um usuário admin //

    @Test
    void testCriarTaskPadrao() {
        UsuarioAdministrador admin = new UsuarioAdministrador("ADM", "123456", null);
        gerenciador.adicionarUsuarioAdm(admin);

        Task task = gerenciador.criarTask("TÍtulo task", "Desc task", categoria, LocalDateTime.now().plusDays(1), usuarioCriador);

        assertTrue(task.getUsuariosDonos().contains(admin));
        assertTrue(task.getUsuariosDonos().contains(usuarioCriador));
    }


    // Teste relacionado à criação de uma TaskPeriódica a partir de um usuário admin //

    @Test
    void testCriarTaskPeriodica() {
        UsuarioAdministrador admin = new UsuarioAdministrador("ADM", "123456", null);
        gerenciador.adicionarUsuarioAdm(admin);

        Task task = gerenciador.criarTask("TÍtulo task", "Desc task", categoria, LocalDateTime.now().plusDays(1), usuarioCriador, Duration.ofDays(3), LocalDateTime.now().plusDays(30));

        assertTrue(task.getUsuariosDonos().contains(admin));
        assertTrue(task.getUsuariosDonos().contains(usuarioCriador));
    }


    // Teste relacionado à busca e remoção de uma task do sistema //

    @Test
    void testBuscarApagarTask() {
        Task task = gerenciador.criarTask("Título task", "Desc task", categoria, LocalDateTime.now().plusDays(1), usuarioCriador);
        UUID id = task.getId();

        // Busca
        assertEquals(task, gerenciador.buscarTask(id));

        // Apaga
        boolean apagou = gerenciador.apagarTask(id);
        assertTrue(apagou);
        assertNull(gerenciador.buscarTask(id));
    }

}
