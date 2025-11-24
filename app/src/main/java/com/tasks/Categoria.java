package com.tasks;

import java.awt.Color; // Para simular o tipo Color

/**
 * Representa uma categoria para classificar tarefas.
 */
public class Categoria implements java.io.Serializable {
    private String nome;
    private String descricao;
    private Color cor;

    public Categoria(String nome, String descricao, Color cor) {
        this.nome = nome;
        this.descricao = descricao;
        this.cor = cor;
    }

    public String getNome() { 
        return this.nome; 
    }
    public String getDescricao() { 
        return this.descricao; 
    }
    public Color getCor() { 
        return this.cor; 
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void setDescricao(String novaDescricao) {
        this.descricao = novaDescricao;
    }

    public void setCor(Color novaCor) {
        this.cor = novaCor;
    }
}
