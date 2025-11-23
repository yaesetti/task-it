package com.tasks;

import java.util.UUID;

/**
 * Representa um item de trabalho menor que faz parte de uma Task.
 */
public class Subtarefa {
    private String titulo;
    private String descricao;
    private boolean feito;
    private UUID id;

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
        System.out.println("Subtarefa '" + this.titulo + "' agora está " + (this.feito ? "CONCLUÍDA" : "PENDENTE") + ".");
    }

    // Getters e Setters
    public UUID getId() { 
        return id; 
    }
    public String getTitulo() { 
        return titulo; 
    }
    public boolean isFeito() { 
        return feito; 
    }
    public void setFeito(boolean feito) { 
        this.feito = feito; 
    }

}
