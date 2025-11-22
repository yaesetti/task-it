package com.tasks;

import java.util.UUID;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.Duration;

import com.excecoes.DataInvalidaException;
import com.categorias.Categoria;
import com.usuarios.Usuario;

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

        // DEVE ESTAR ERRADO
        if (data.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("Data Invalida: A data de uma Task nao pode ser no passado!");
        }
    }

    @Override
    public String getTitulo() {
        return this.titulo;
    }

    @Override
    public String getDescricao() {
        return this.descricao;
    }

    @Override
    public Categoria getCategoria() {
        return this.categoria;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public boolean getFeito() {
        return this.feito;
    }

    @Override
    public ArrayList<Subtarefa> getSubtarefas() {
        return this.subtarefas;
    }

    @Override
    public LocalDateTime getData() {
        return this.data;
    }

    @Override
    public ArrayList<Usuario> getUsuariosDonos() {
        return this.usuariosDonos;
    }

    @Override
    public void setTitulo(String novoTitulo) {
        this.titulo = novoTitulo;
    }

    @Override
    public void setDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    @Override
    public void setCategoria(Categoria novaCategoria) {
        this.categoria = novaCategoria;
    }

    @Override
    public void setFeito(boolean novoFeito) {
        this.feito = novoFeito;
    }

    @Override
    public void trocarFeito() {
        this.feito = !this.feito;
    }

    //TODO
    @Override
    public void adicionarSubtarefa() {

    }

    @Override
    public void excluirSubtarefa(UUID id) {
        this.subtarefas.removeIf(subtarefa -> id.equals(subtarefa.getId()));
    }

    @Override
    public void setFeitoTodasSubtarefas(boolean novoFeito) {
        for (Subtarefa subtarefa : this.subtarefas) {
            subtarefa.setFeito(novoFeito);
        }
    }

    @Override
    public void setData(LocalDateTime novaData) {
        this.data = novaData;
    }

    @Override
    public Duration calcularTempoRestante() {
        return Duration.between(LocalTimeDate.now(), this.data);
    }
}
