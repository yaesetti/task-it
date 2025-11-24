package com.gerenciadores;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.excecoes.NomeDuplicadoException;
import com.serializacao.FuncoesSerial;
import com.tasks.TaskAbstrata;
import com.usuarios.TipoUsuario;
import com.usuarios.Usuario;
import com.usuarios.UsuarioAdministrador;
import com.usuarios.UsuarioPadrao;

public class Gerenciador implements Serializable {
    private final List<Usuario> usuarios;
    private final Map<UUID, String> senhas;
    private final GerenciadorDeTasks gerTasks;

    public Gerenciador() {
        this.usuarios = new ArrayList<>();
        this.senhas = new HashMap<>();
        this.gerTasks = new GerenciadorDeTasks();
    }

    private String sha256Base64(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(this.usuarios);
    }

    public GerenciadorDeTasks getGerTasks() {
        return this.gerTasks;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios.clear(); 
        this.usuarios.addAll(usuarios); 
        
        this.senhas.clear(); 
        for (Usuario u : usuarios) {
            String senhaOuHash = u.getSenha();
            
            this.senhas.put(u.getId(), senhaOuHash); 
        }
    }



    public void criarUsuario(TipoUsuario tipo, String nome, String senha, String imagePath, List<TaskAbstrata> ListaTarefas) throws NomeDuplicadoException {
        for (Usuario usuario : this.usuarios) {
            if (usuario.getNome().equals(nome)) {
                throw new NomeDuplicadoException("Nome Duplicado: Ja existe um usuario com este nome!");
            }
        }

        Usuario usuario;
        if (tipo == TipoUsuario.ADMINISTRADOR) {
            usuario = new UsuarioAdministrador(nome, senha, imagePath);
            usuario.TaskList = ListaTarefas;
        }
        else {
            usuario = new UsuarioPadrao(nome, senha, imagePath);
            usuario.TaskList = ListaTarefas;
        }

        this.usuarios.add(usuario);
        String hashed = sha256Base64(senha);
        if (hashed != null) {
            this.senhas.put(usuario.getId(), hashed);
        }
        else {
            this.senhas.put(usuario.getId(), senha);
        }

        FuncoesSerial.salvarUsuarios(this);


    }



    public void criarUsuario(TipoUsuario tipo, String nome, String senha, String imagePath) throws NomeDuplicadoException {
        for (Usuario usuario : this.usuarios) {
            if (usuario.getNome().equals(nome)) {
                throw new NomeDuplicadoException("Nome Duplicado: Ja existe um usuario com este nome!");
            }
        }

        Usuario usuario;
        if (tipo == TipoUsuario.ADMINISTRADOR) {
            usuario = new UsuarioAdministrador(nome, senha, imagePath);
        }
        else {
            usuario = new UsuarioPadrao(nome, senha, imagePath);
        }

        this.usuarios.add(usuario);
        String hashed = sha256Base64(senha);
        if (hashed != null) {
            this.senhas.put(usuario.getId(), hashed);
        }
        else {
            this.senhas.put(usuario.getId(), senha);
        }

        FuncoesSerial.salvarUsuarios(this);


    }

    public boolean validarSenha(UUID id, String fornecida) {
        String armazenada = this.senhas.get(id);

        String fornecidaHashed = sha256Base64(fornecida);
        if (fornecidaHashed.equals(armazenada)) {
            return true;
        }
        return fornecida.equals(armazenada);
    }

    public boolean alterarSenha(UUID id, String atual, String nova) {
        if (!this.validarSenha(id, atual)) {
            return false;
        }
        String novaHashed = sha256Base64(nova);

        if (novaHashed != null) {
            this.senhas.put(id, novaHashed);
        }
        else {
            this.senhas.put(id, nova);
        }
        return true;
    }

    public boolean apagarUsuario(UUID id, String senha) {
        if (!this.validarSenha(id, senha)) {
            return false;
        }

        boolean removido = this.usuarios.removeIf(usuario -> usuario.getId().equals(id));
        this.senhas.remove(id);
        this.gerTasks.removerUsuarioDeTodasTasks(id);
        this.gerTasks.removerUsuarioAdm(id);
        
        return removido;
    }
}
