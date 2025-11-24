package com.serializacao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.usuarios.Usuario;

public class FuncoesSerial {

    public static void salvarUsuario(Usuario usuario, String arquivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(usuario);
            System.out.println("Usuário salvo com sucesso!");
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuário:");
            e.printStackTrace();
        }
    }

    public static Usuario carregarUsuario(String arquivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (Usuario) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar usuário:");
            e.printStackTrace();
            return null;
        }
    }
}
