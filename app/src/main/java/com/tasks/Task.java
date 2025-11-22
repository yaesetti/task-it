package com.tasks;

import com.excecoes.DataInvalidaException;
import com.categorias.Categoria;
import com.usuarios.Usuario;

import java.util.UUID;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

public abstract class Task implements TaskInterface {
    private String titulo;
    private String descricao;
    private Categoria categoria;
    private final UUID id;
    private boolean feito;
    private ArrayList<Subtarefa> subtarefas;
    private LocalDateTime data;
    private ArrayList<Usuario> usuariosDonos;

    public Task(String titulo, String descricao, Categoria categoria, LocalDateTime data, ArrayList<Usuario> usuariosDonos) throws DataInvalidaException {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.id = UUID.randomUUID();
        this.feito = false;
        this.subtarefas = new ArrayList<>();
        this.data = data;
        this.usuariosDonos = usuariosDonos;

        if (Duration.between(LocalDateTime.now(), data).isNegative()) {
            throw new DataInvalidaException("Data Invalida: A data de uma Task nao pode ser no passado!");
        }
    }
}
