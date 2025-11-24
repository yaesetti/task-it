package com.usuarios;

import java.util.UUID;

import com.tasks.TaskAbstrata;

import java.util.ArrayList;
import java.util.List;

public class Usuario implements java.io.Serializable{
    private String nome;
    private String senha;
    private String imagePath;
    private final UUID id;
    public List<TaskAbstrata> TaskList;

    public Usuario(String nome, String senha, String imagePath) {
        this.nome = nome;
        this.senha = senha;
        this.imagePath = imagePath;
        this.id = UUID.randomUUID();
        this.TaskList = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public String getSenha() {
        return this.senha;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public UUID getId() {
        return this.id;
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
    }

    public void setSenha(String novaSenha) {
        this.senha = novaSenha;
    }

    public void setImagePath(String novoImagePath) {
        this.imagePath = novoImagePath;
    }

    public void adicionarTask(TaskAbstrata task) {
            this.TaskList.add(task);
    }

    public boolean removerTask(UUID id) {
        for (int i = 0; i < this.TaskList.size(); i++) {
            if (this.TaskList.get(i).getId().equals(id)) { 
                    this.TaskList.remove(i); 
                                return true;
            }
        }
        
        return false; 
    }

    public int contarTasksAbertas() {
        return this.TaskList.size();
    }
    }

