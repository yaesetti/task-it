package com.usuarios;

import java.util.UUID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Usuario {
    private String nome;
    private String senha;
    private String imagePath;
    private final UUID id;
    private final List<UUID> taskIds;

    public Usuario(String nome, String senha, String imagePath) {
        this.nome = nome;
        this.senha = senha;
        this.imagePath = imagePath;
        this.id = UUID.randomUUID();
        this.taskIds = new ArrayList<>();
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

    public boolean adicionarTask(UUID id) {
        if (this.taskIds.contains(id) || this.taskIds.size() >= this.getMaxTasks()) {
            return false;
        }
        this.taskIds.add(id);
        return true;
    }

    public boolean removerTask(UUID id) {
        return this.taskIds.remove(id);
    }

    public List<UUID> getTaskIds() {
        return Collections.unmodifiableList(this.taskIds);
    }

    public abstract TipoUsuario getTipoUsuario();

    public abstract int getMaxTasks();
}
