package com.usuarios;

public class UsuarioAdministrador extends Usuario {
    public UsuarioAdministrador(String nome, String senha, String imagePath) {
        super(nome, senha, imagePath);
    }

    @Override
    public TipoUsuario getTipoUsuario() {
        return TipoUsuario.ADMINISTRADOR;
    }

    @Override
    public int getMaxTasks() {
        return Integer.MAX_VALUE;
    }
}
