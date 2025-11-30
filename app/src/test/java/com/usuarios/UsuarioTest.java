package com.usuarios;

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

public class UsuarioTest {

    private UsuarioPadrao usuario;
    private UsuarioAdministrador admin;

    @BeforeEach
    void setUp() {
        usuario = new UsuarioPadrao("Lex Luthor", "123456", "caminho");
        admin = new UsuarioAdministrador("ADM", "000000", "caminho 2");
    }


    // Teste relacionado aos construtores //

    @Test
    void testConstrutorPadrao() {
        assertNotNull(usuario.getId());
        assertEquals("Lex Luthor", usuario.getNome());
        assertEquals("caminho", usuario.getImagePath());
        assertEquals(TipoUsuario.PADRAO, usuario.getTipoUsuario());
        assertEquals(50, usuario.getMaxTasks());
        assertNotNull(usuario.getTaskIds());
        assertTrue(usuario.getTaskIds().isEmpty());
    }

    @Test
    void testConstrutorAdmin() {
        assertEquals(TipoUsuario.ADMINISTRADOR, admin.getTipoUsuario());
        assertEquals(Integer.MAX_VALUE, admin.getMaxTasks());
    }


    // Teste relacionado à mudança de atributos //

    @Test
    void testSetters() {
        usuario.setNome("LL");
        usuario.setImagePath("caminho novo");

        assertEquals("LL", usuario.getNome());
        assertEquals("caminho novo", usuario.getImagePath());
    }  


    // Teste relacionado à remoção de tarefa //

    @Test
    void testAdicionarRemoverTask() {
        UUID taskId = UUID.randomUUID();

        boolean adicionou = usuario.adicionarTask(taskId);
        assertTrue(adicionou);
        assertTrue(usuario.getTaskIds().contains(taskId));

        boolean removeu = usuario.removerTask(taskId);
        assertTrue(removeu);
    }


    // Teste relacionado à inclusão de task duplicada //

    @Test
    void testNaoAdicionaDuplicado() {
        UUID taskId = UUID.randomUUID();

        usuario.adicionarTask(taskId);
        boolean adicionou = usuario.adicionarTask(taskId);

        assertFalse(adicionou);
    }


    // Teste relacionado ao limite de tasks do UsuárioPadrão //
    
    @Test
    void testLimiteTaskPadrao() {

        for (int i = 0; i < 50; i++) {
            usuario.adicionarTask(UUID.randomUUID());
        }
        
        boolean adicionouExtra = usuario.adicionarTask(UUID.randomUUID());
        
        assertFalse(adicionouExtra);
        assertEquals(50, usuario.contarTasksAbertas());
    }

    @Test
    void testLimiteTaskAdmin() {

        for (int i = 0; i < 50; i++) {
            admin.adicionarTask(UUID.randomUUID());
        }
        
        boolean adicionouExtra = admin.adicionarTask(UUID.randomUUID());
        
        assertTrue(adicionouExtra);
        assertEquals(51, admin.contarTasksAbertas());
    }
    
}
