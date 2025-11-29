package com.serializacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.usuarios.Usuario;

public final class UsuariosSnapshot implements Serializable {
    private final List<Usuario> usuarios;
    private final Map<UUID, String> senhas;

    public UsuariosSnapshot(List<Usuario> usuarios, Map<UUID, String> senhas) {
        // cópias defensivas para evitar aliasing externo
        this.usuarios = usuarios == null ? Collections.emptyList() : new ArrayList<>(usuarios);
        this.senhas = senhas == null ? Collections.emptyMap() : new HashMap<>(senhas);
    }

    /**
     * Retorna uma visão imutável da lista de usuários.
     */
    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(this.usuarios);
    }

    /**
     * Retorna uma visão imutável do mapa de hashes de senha (UUID -> hash).
     */
    public Map<UUID, String> getSenhas() {
        return Collections.unmodifiableMap(this.senhas);
    }
}