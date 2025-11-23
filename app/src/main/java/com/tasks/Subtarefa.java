package com.tasks;

import java.util.UUID;

/**
 * Representa um item de trabalho menor que faz parte de uma Task.
 */
public class Subtarefa {
    private String titulo;
    private String descricao;
    private boolean feito;
    private final UUID id;

    public Subtarefa(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.feito = false;
        this.id = UUID.randomUUID();
    }

    /**
     * Alterna o estado de 'feito' da subtarefa.
     */
    public void trocarFeito() {
        this.feito = !this.feito;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public boolean getFeito() { 
        return this.feito; 
    }

    public UUID getId() { 
        return this.id; 
    }

    public void setTitulo(String novoTitulo) {
        this.titulo = novoTitulo;
    }

    public void setDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    public void setFeito(boolean novoFeito) {
        this.feito = novoFeito;
    }
}
