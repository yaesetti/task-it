package com.Telas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.gerenciadores.Gerenciador;
import com.excecoes.NomeDuplicadoException;
import com.usuarios.Usuario;
import com.usuarios.TipoUsuario;

/**
 * Tela simples que reutiliza o Gerenciador para criar/login de usuários
 * e retorna o objeto Usuario logado.
 */
public class TelaSimplesGerenciador extends JDialog {

    private Usuario usuarioLogado = null;
    private final Gerenciador ger;

    private JTextField nomeField;
    private JPasswordField senhaField;

    public TelaSimplesGerenciador(Frame parent, Gerenciador ger) {
        super(parent, true);
        this.ger = ger;

        setTitle("Login / Cadastro Simples");
        setSize(320, 180);
        setLayout(new GridLayout(4, 2, 5, 5));
        setLocationRelativeTo(null);

        add(new JLabel("Nome:"));
        nomeField = new JTextField();
        add(nomeField);

        add(new JLabel("Senha:"));
        senhaField = new JPasswordField();
        add(senhaField);

        JButton btnLogin = new JButton("Login");
        JButton btnCadastro = new JButton("Cadastrar");

        add(btnLogin);
        add(btnCadastro);

        btnLogin.addActionListener(e -> fazerLogin());
        btnCadastro.addActionListener(e -> fazerCadastro());
    }

    private void fazerLogin() {
        String nome = nomeField.getText().trim();
        String senha = new String(senhaField.getPassword());

        for (Usuario u : ger.getUsuarios()) {
            if (u.getNome().equals(nome) && ger.validarSenha(u.getId(), senha)) {
                Menu menus = new Menu();
                menus.criarJanela();
                usuarioLogado = u;
                dispose();
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Usuário ou senha incorretos.");
    }

    private void fazerCadastro() {
        String nome = nomeField.getText().trim();
        String senha = new String(senhaField.getPassword());

        if (nome.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        try {
            ger.criarUsuario(TipoUsuario.PADRAO, nome, senha, null);
            JOptionPane.showMessageDialog(this, "Usuário criado com sucesso!");
        } catch (NomeDuplicadoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    // EXEMPLO DE USO:
    public static void main(String[] args) {
        Gerenciador ger = new Gerenciador();
        TelaSimplesGerenciador t = new TelaSimplesGerenciador(null, ger);
        t.setVisible(true);

        Usuario u = t.getUsuarioLogado();
        if (u != null) System.out.println("Logado como: " + u.getNome());
        else System.out.println("Ninguém logou.");
    }
}