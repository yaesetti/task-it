package com.tasks;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import java.time.Duration;

import com.categorias.Categoria;
import com.usuarios.Usuario;
import com.excecoes.UsuarioInvalidoException;
import com.excecoes.DataInvalidaException;

public interface Task {
    public String getTitulo();

    public String getDescricao();

    public Categoria getCategoria();

    public UUID getId();

    public boolean getFeito();

    public List<Subtarefa> getSubtarefas();

    public LocalDateTime getData();

    public List<Usuario> getUsuariosDonos();

    public void setTitulo(String novoTitulo);

    public void setDescricao(String novaDescricao);

    public void setCategoria(Categoria novaCategoria);

    public void setFeito(boolean novoFeito);

    public void trocarFeito();

    public void adicionarSubtarefa(String titulo, String descricao);

    public void adicionarSubtarefa(String titulo);

    public boolean excluirSubtarefa(UUID id);

    public void setFeitoTodasSubtarefas(boolean novoFeito);

    public void setData(LocalDateTime novaData) throws DataInvalidaException;

    public Duration calcularTempoRestante();

    public void adicionarUsuarioDono(Usuario novoUsuarioDono) throws UsuarioInvalidoException;

    public boolean removerUsuarioDono(UUID id);
}
