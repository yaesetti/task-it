package com.tasks;

import java.time.LocalDateTime;
import java.util.List;

import com.usuarios.Usuario;
import com.excecoes.DataInvalidaException;

public class TaskPadrao extends TaskAbstrata {
    public TaskPadrao(String titulo, String descricao, Categoria categoria, LocalDateTime data, List<Usuario> usuariosDonos) throws DataInvalidaException {
        super(titulo, descricao, categoria, data, usuariosDonos);
    }
}
