package com.usuarios;

public class UsuarioPadrao extends Usuario {
    public UsuarioPadrao(String nome, String senha, String imagePath) {
        super(nome, senha, imagePath);
    }

    @Override
    public TipoUsuario getTipoUsuario() {
        return TipoUsuario.PADRAO;
    }

    @Override
    public int getMaxTasks() {
        return 50;
    }
}
