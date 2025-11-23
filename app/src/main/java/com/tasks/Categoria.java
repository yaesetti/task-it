package com.tasks;

import java.awt.Color; // Para simular o tipo Color

/**
 * Representa uma categoria para classificar tarefas.
 */
public class Categoria {
    private String nome;
    private String descricao;
    private Color cor;

    public Categoria(String nome, String descricao, Color cor) {
        this.nome = nome;
        this.descricao = descricao;
        this.cor = cor;
    }

    // Getters
    public String getNome() { 
        return nome; 
    }
    public String getDescricao() { 
        return descricao; 
    }
    public Color getCor() { 
        return cor; 
    }

}
