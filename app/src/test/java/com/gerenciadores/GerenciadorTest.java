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

public class GerenciadorTest {
    
    private Gerenciador gerenciador;

    @BeforeEach
    void setUp() {
        gerenciador = new Gerenciador();
    }


    // Teste relacionado à criação de um usuário //

    @Test
    void testCriarUsuario() throws NomeDuplicadoException {
        gerenciador.criarUsuario(TipoUsuario.PADRAO, "Lex Luthor", "123456", null);

        List<Usuario> usuarios = gerenciador.getUsuarios();
        assertEquals(1, usuarios.size());
        assertEquals("Lex Luthor", usuarios.get(0).getNome());
        
        UUID id = usuarios.get(0).getId();

        assertTrue(gerenciador.validarSenha(id, "123456"));
        assertFalse(gerenciador.validarSenha(id, "000000"));
    }


    // Teste relacionado ao conflito gerado quando se cria um usuário de mesmo nome que outro //

    @Test
    void testNomeDuplicado() throws NomeDuplicadoException {
        gerenciador.criarUsuario(TipoUsuario.PADRAO, "Adam Strange", "123456", null);

        assertThrows(NomeDuplicadoException.class, () -> {
            gerenciador.criarUsuario(TipoUsuario.ADMINISTRADOR, "Adam Strange", "000000", null);
        });
    }


    // Teste relacionado à mudança de senha //

    @Test
    void testAlterarSenha() throws NomeDuplicadoException {
        gerenciador.criarUsuario(TipoUsuario.PADRAO, "Adam Strange", "antiga", null);
        UUID id = gerenciador.getUsuarios().get(0).getId();

        boolean alterouErrado = gerenciador.alterarSenha(id, "errada", "nova");
        assertFalse(alterouErrado);

        boolean alterouCerto = gerenciador.alterarSenha(id, "antiga", "nova");
        assertTrue(alterouCerto);

        assertTrue(gerenciador.validarSenha(id, "nova"));
    }


    // Teste de exclusão de um usuário do sistema //

    @Test
    void testApagarUsuario() throws NomeDuplicadoException {
        
        gerenciador.criarUsuario(TipoUsuario.PADRAO, "Adam Strange", "123", null);
        Usuario usuario = gerenciador.getUsuarios().get(0);
        UUID id = usuario.getId();

        gerenciador.getGerTasks().criarCategoria("Título categ", Color.BLUE);
        Categoria categ = gerenciador.getGerTasks().getCategorias().get(0);
        Task task = gerenciador.getGerTasks().criarTask("Título task", "Título desc", categ, LocalDateTime.now().plusDays(5), usuario);
    
        boolean apagou = gerenciador.apagarUsuario(id, "123");
        assertTrue(apagou);

        assertTrue(gerenciador.getUsuarios().isEmpty()); 
        assertFalse(gerenciador.validarSenha(id, "123")); 
        assertFalse(task.getUsuariosDonos().contains(usuario));
    }

}
