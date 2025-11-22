package com.tasks;

import java.util.ArrayList;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.Duration;

import com.categorias.Categoria;
import com.usuarios.Usuario;

public interface TaskInterface {
    public String getTitulo();

    public String getDescricao();

    public Categoria getCategoria();

    public UUID getId();

    public boolean getFeito();

    public ArrayList<Subtarefa> getSubtarefas();

    public LocalDateTime getData();

    public ArrayList<Usuario> getUsuariosDonos();

    public void setTitulo(String novoTitulo);

    public void setDescricao(String novaDescricao);

    public void setCategoria(Categoria novaCategoria);

    public void setFeito(boolean novoFeito);

    public void trocarFeito();

    //TODO
    public void adicionarSubtarefa();

    public void excluirSubtarefa(UUID id);

    public void setFeitoTodasSubtarefas();

    public Duration calcularTempoRestante();
}
