package com.Telas;

import com.usuarios.Usuario;

import java.io.File;

import com.serializacao.FuncoesSerial;


public class TelaCadastroUsuario {

public static void salvarUsuario(){
    Usuario novoUsuario = new Usuario("Agnes","123456",null);

    File dir = new File("Dado");
    if (!dir.exists()) {
        dir.mkdirs(); // cria o diretório e todos os pais, se necessário
    }

    FuncoesSerial.salvarUsuario(novoUsuario, "Dado/abc.ser");
    System.out.println("Usuário salvo");
}



    

}