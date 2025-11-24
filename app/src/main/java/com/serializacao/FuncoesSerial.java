package com.serializacao;

import java.io.*;
import java.util.List;
import java.io.File;

import com.gerenciadores.Gerenciador;
import com.usuarios.Usuario;

public class FuncoesSerial {

    private static final String ARQUIVO_USUARIOS = "Dado/usuarios.ser";

    // Salva a lista inteira de usuários
    public static void salvarUsuarios(Gerenciador ger) {
        if (ger.getUsuarios().isEmpty()) return; 

        File file = new File(ARQUIVO_USUARIOS);
        try {
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();

            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(ger.getUsuarios());
                System.out.println("Lista de usuários salva com sucesso!");
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar lista de usuários:");
            e.printStackTrace();
        }
    }

    // Carrega a lista de usuários e adiciona ao Gerenciador
    @SuppressWarnings("unchecked")
    public static void carregarUsuarios(Gerenciador ger) {
        File file = new File(ARQUIVO_USUARIOS);
        if (!file.exists()) return; 

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            List<Usuario> usuariosCarregados = (List<Usuario>) ois.readObject();
            for (Usuario u : usuariosCarregados) {
                try {
                    // cria no gerenciador usando os dados existentes
                    ger.criarUsuario(u.getTipoUsuario(), u.getNome(), u.getSenha(), u.getImagePath());
                } catch (Exception e) {
                    System.err.println("Erro ao adicionar usuário: " + u.getNome());
                }
            }
            System.out.println("Lista de usuários carregada com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar lista de usuários:");
            e.printStackTrace();
        }
    }
}
