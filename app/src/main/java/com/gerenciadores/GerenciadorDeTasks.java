package com.gerenciadores;

import java.awt.Color;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.tasks.Task;
import com.tasks.TaskPadrao;
import com.tasks.TaskPeriodica;
import com.tasks.Categoria;
import com.excecoes.NomeDuplicadoException;
import com.excecoes.DataInvalidaException;
import com.excecoes.UsuarioInvalidoException;
import com.usuarios.Usuario;

public class GerenciadorDeTasks implements Serializable {
    private final ArrayList<Task> tasks;
    private final ArrayList<Categoria> categorias;
    private final ArrayList<Usuario> usuariosAdm;

    public GerenciadorDeTasks() {
        this.tasks = new ArrayList<>();
        this.categorias = new ArrayList<>();
        this.usuariosAdm = new ArrayList<>();
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(this.tasks);
    }

    public List<Categoria> getCategorias() {
        return Collections.unmodifiableList(this.categorias);
    }

    public List<Usuario> getUsuariosAdm() {
        return Collections.unmodifiableList(this.usuariosAdm);
    }

    /**
     * Cria uma TaskPadrao (sem recorrencia) e a coloca na lista de tasks do GerenciadorDeTasks
     * @param titulo 
     * @param descricao
     * @param categoria
     * @param data
     * @param usuarioCriador
     */
    public Task criarTask(String titulo, String descricao, Categoria categoria, LocalDateTime data, Usuario usuarioCriador) {
        if (usuarioCriador.getMaxTasks() <= usuarioCriador.getTaskIds().size()) {
            return null;
        }
        List<Usuario> usuariosDonos = new ArrayList<>(this.usuariosAdm);
        usuariosDonos.add(usuarioCriador);

        try {
            TaskPadrao task = new TaskPadrao(titulo, descricao, categoria, data, usuariosDonos);

            for (Usuario usuario : usuariosDonos) {
                usuario.adicionarTask(task.getId());
            }
            
            this.tasks.add(task);
            return task;

        } catch (DataInvalidaException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    /**
     * Cria uma TaskPeriodica (com recorrencia) e a coloca na lista de tasks do GerenciadorDeTasks
     * @param titulo
     * @param descricao
     * @param categoria
     * @param data
     * @param usuarioCriador
     * @param recorrencia
     * @param dataFinal
     */
    public Task criarTask(String titulo, String descricao, Categoria categoria, LocalDateTime data, Usuario usuarioCriador, Duration recorrencia, LocalDateTime dataFinal) {
        if (usuarioCriador.getMaxTasks() <= usuarioCriador.getTaskIds().size()) {
            return null;
        }
        List<Usuario> usuariosDonos = new ArrayList<>(this.usuariosAdm);
        usuariosDonos.add(usuarioCriador);

        try {
            TaskPeriodica task = new TaskPeriodica(titulo, descricao, categoria, data, usuariosDonos, recorrencia, dataFinal);

            for (Usuario usuario : usuariosDonos) {
                try {
                    task.adicionarUsuarioDono(usuario);
                    usuario.adicionarTask(task.getId());
                } catch (UsuarioInvalidoException e) {
                    System.err.println(e.getMessage());
                }
            }
            this.tasks.add(task);
            return task;

        } catch (DataInvalidaException e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public boolean apagarTask(UUID id) {
        return this.tasks.removeIf(task -> id.equals(task.getId()));
    }

    /**
     * Busca uma Task no GerenciadorDeTasks a partir de um ID
     * @param id
     * @return A task referente ao ID ou NULL caso nao encontre
     */
    public Task buscarTask(UUID id) {
        for (Task task : this.tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }

    public void criarCategoria(String nome, String descricao, Color cor) throws NomeDuplicadoException {
        for (Categoria categoria : this.categorias) {
            if (categoria.getNome().equals(nome)) {
                throw new NomeDuplicadoException("Nome Duplicado: Ja existe uma categoria com este nome!");
            }
        }
        this.categorias.add(new Categoria(nome, descricao, cor));
    }

    public void criarCategoria(String nome, Color cor) throws NomeDuplicadoException {
        for (Categoria categoria : this.categorias) {
            if (categoria.getNome().equals(nome)) {
                throw new NomeDuplicadoException("Nome Duplicado: Ja existe uma categoria com este nome!");
            }
        }
        this.categorias.add(new Categoria(nome, "", cor));
    }

    public boolean apagarCategoria(String nome) {
        return this.categorias.removeIf(categoria -> nome.equals(categoria.getNome()));
    }

    public List<Task> getTasksPorCategoria(String nome) {
        ArrayList<Task> temp = new ArrayList<>();
        for (Task task : this.tasks) {
            if (task.getCategoria().getNome().equals(nome)) {
                temp.add(task);
            }
        }
        return Collections.unmodifiableList(temp);
    }

    public boolean removerUsuarioDeTodasTasks(UUID id) {
        boolean flag = false;
        for (Task task : this.tasks) {
            flag |= task.removerUsuarioDono(id);
        }
        return flag;
    }

    public void adicionarUsuarioAdm(Usuario novoUsuarioAdm) {
        this.usuariosAdm.add(novoUsuarioAdm);
    }

    public boolean removerUsuarioAdm(UUID id) {
        return this.usuariosAdm.removeIf(usuario -> usuario.getId().equals(id));
    }
}
