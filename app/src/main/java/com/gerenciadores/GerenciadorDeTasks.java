package com.gerenciadores;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.tasks.Task;
import com.tasks.TaskPadrao;
import com.tasks.TaskPeriodica;
import com.categorias.Categoria;
import com.usuarios.Usuario;

public class GerenciadorDeTasks {
    private ArrayList<Task> tasks;

    public GerenciadorDeTasks() {
        this.tasks = new ArrayList<>();
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
        for (Task task : tasks) {
            if (task.getId().equals(id)) {
                return task;
            }
        }
        return null;
    }
}
