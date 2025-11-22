package com.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.categorias.Categoria;
import com.usuarios.Usuario;
import com.excecoes.DataInvalidaException;

public class TaskPadrao extends Task {
    public TaskPadrao(String titulo, String descricao, Categoria categoria, LocalDateTime data, ArrayList<Usuario> usuariosDonos) throws DataInvalidaException {
        super(titulo, descricao, categoria, data, usuariosDonos);
    }
}
