package com.gerenciadores;

import java.awt.Color;
import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tasks.Task;
import com.tasks.TaskPadrao;
import com.tasks.TaskPeriodica;
import com.tasks.Categoria;
import com.excecoes.CategoriaDuplicadaException;
import com.usuarios.Usuario;

public class GerenciadorDeTasks {
    private ArrayList<Task> tasks;
    private ArrayList<Categoria> categorias;

    public GerenciadorDeTasks() {
        this.tasks = new ArrayList<>();
        this.categorias = new ArrayList<>();
    }
    /**
     * Cria uma TaskPadrao (sem recorrencia) e a coloca na lista de tasks do GerenciadorDeTasks
     * @param titulo 
     * @param descricao
     * @param categoria
     * @param data
     * @param usuariosDonos
     */
    public void criarTask(String titulo, String descricao, Categoria categoria, LocalDateTime data, List<Usuario> usuariosDonos) {
        this.tasks.add(new TaskPadrao(titulo, descricao, categoria, data, usuariosDonos));
    }

    /**
     * Cria uma TaskPeriodica (com recorrencia) e a coloca na lista de tasks do GerenciadorDeTasks
     * @param titulo
     * @param descricao
     * @param categoria
     * @param data
     * @param usuariosDonos
     * @param recorrencia
     * @param dataFinal
     */
    public void criarTask(String titulo, String descricao, Categoria categoria, LocalDateTime data, List<Usuario> usuariosDonos, Duration recorrencia, LocalDateTime dataFinal) {
        this.tasks.add(new TaskPeriodica(titulo, descricao, categoria, data, usuariosDonos, recorrencia, dataFinal));
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

    public void criarCategoria(String nome, String descricao, Color cor) throws CategoriaDuplicadaException {
        for (Categoria categoria : this.categorias) {
            if (categoria.getNome().equals(nome)) {
                throw new CategoriaDuplicadaException();
            }
        }
        this.categorias.add(new Categoria(nome, descricao, cor));
    }

    public void criarCategoria(String nome, Color cor) throws CategoriaDuplicadaException {
        for (Categoria categoria : this.categorias) {
            if (categoria.getNome().equals(nome)) {
                throw new CategoriaDuplicadaException();
            }
        }
        this.categorias.add(new Categoria(nome, "", cor));
    }

    public boolean apagarCategoria(String nome) {
        return this.categorias.removeIf(categoria -> nome.equals(categoria.getNome()));
    }
}
