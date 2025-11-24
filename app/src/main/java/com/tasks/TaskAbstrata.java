package com.tasks;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.time.LocalDateTime;
import java.time.Duration;

import com.excecoes.DataInvalidaException;
import com.excecoes.UsuarioInvalidoException;
import com.usuarios.Usuario;

public abstract class TaskAbstrata implements Task, java.io.Serializable {
    private String titulo;
    private String descricao;
    private Categoria categoria;
    private final UUID id;
    private boolean feito;
    private ArrayList<Subtarefa> subtarefas;
    private LocalDateTime data;
    private ArrayList<Usuario> usuariosDonos;

    public TaskAbstrata(String titulo, String descricao, Categoria categoria, LocalDateTime data, List<Usuario> usuariosDonos) throws DataInvalidaException {
        this.titulo = titulo;
        this.descricao = descricao;
        this.categoria = categoria;
        this.id = UUID.randomUUID();
        this.feito = false;
        this.subtarefas = new ArrayList<>();

        if (data == null) {
            throw new DataInvalidaException("Data Invalida: A data de uma Task nao pode ser vazia!");
        }

        if (data.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("Data Invalida: A data de uma Task nao pode ser no passado!");
        }
        this.data = data;
        
        if (usuariosDonos == null) {
            this.usuariosDonos = new ArrayList<>();
        }
        else {
            this.usuariosDonos = new ArrayList<>(usuariosDonos);
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
        return new ArrayList<>(this.subtarefas);
    }

    @Override
    public LocalDateTime getData() {
        return this.data;
    }

    @Override
    public List<Usuario> getUsuariosDonos() {
        return Collections.unmodifiableList(this.usuariosDonos);
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

    @Override
    public void adicionarSubtarefa(String titulo, String descricao) {
        this.subtarefas.add(new Subtarefa(titulo, descricao));
    }

    @Override
    public void adicionarSubtarefa(String titulo) {
        this.subtarefas.add(new Subtarefa(titulo, ""));
    }

    @Override
    public boolean excluirSubtarefa(UUID id) {
        return this.subtarefas.removeIf(subtarefa -> id.equals(subtarefa.getId()));
    }

    @Override
    public void setFeitoTodasSubtarefas(boolean novoFeito) {
        for (Subtarefa subtarefa : this.subtarefas) {
            subtarefa.setFeito(novoFeito);
        }
    }

    @Override
    public void setData(LocalDateTime novaData) throws DataInvalidaException {
        if (novaData == null) {
            throw new DataInvalidaException("Data Invalida: A data de uma Task nao pode ser vazia!");
        }
        if (novaData.isBefore(LocalDateTime.now())) {
            throw new DataInvalidaException("Data Invalida: A data de uma Task nao pode ser no passado!");
        }
        this.data = novaData;
    }

    @Override
    public Duration calcularTempoRestante() {
        return Duration.between(LocalDateTime.now(), this.data);
    }

    @Override
    public void adicionarUsuarioDono(Usuario novoUsuarioDono) throws UsuarioInvalidoException {
        if (novoUsuarioDono == null) {
            throw new UsuarioInvalidoException("Usuario Invalido: O usuario nao pode ser vazio!");
        }
        if (this.usuariosDonos.stream().anyMatch(usuario -> usuario.getId().equals(novoUsuarioDono.getId()))) {
            throw new UsuarioInvalidoException("Usuario Invalido: Este usuario já eh dono desta task!");
        }
        this.usuariosDonos.add(novoUsuarioDono);
    }

    @Override
    public boolean removerUsuarioDono(UUID id) {
        for (int i = 0; i < this.usuariosDonos.size(); i++) {
            Usuario usuario = this.usuariosDonos.get(i);
            if (usuario.getId().equals(id)) {
                this.usuariosDonos.remove(i);
                usuario.removerTask(this.id);
                return true;
            }
       }
       return false;
    }
}
