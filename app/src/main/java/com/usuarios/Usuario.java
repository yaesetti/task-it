package com.usuarios;

import java.io.Serializable;
import java.util.UUID;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Usuario implements Serializable {
    private String nome;
    private String imagePath;
    private final UUID id;
    private final List<UUID> taskIds;

    public Usuario(String nome, String senha, String imagePath) {
        this.nome = nome;
        this.imagePath = imagePath;
        this.id = UUID.randomUUID();
        this.taskIds = new ArrayList<>();
    }

    public String getNome() {
        return this.nome;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public UUID getId() {
        return this.id;
    }

    public List<UUID> getTaskIds() {
        return Collections.unmodifiableList(this.taskIds);
    }

    public void setNome(String novoNome) {
        this.nome = novoNome;
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

    public int contarTasksAbertas() {
        return this.taskIds.size();
    }

    public abstract int getMaxTasks();

    public abstract TipoUsuario getTipoUsuario();
}
