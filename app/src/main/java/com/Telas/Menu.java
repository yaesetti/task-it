package com.Telas;

import java.awt.*;
import java.time.LocalDateTime;
import javax.swing.*;
import javax.swing.border.Border;

import com.excecoes.DataInvalidaException;
import com.serializacao.*;
import com.tasks.Categoria;
import com.tasks.TaskAbstrata;
import com.tasks.TaskPadrao;
import com.usuarios.Usuario;
import com.Telas.*;

public class Menu {

    private Usuario user;
    private JPanel C2; // painel de post-its

    public JFrame criarJanela() {

        // Carrega usuário
        user = FuncoesSerial.carregarUsuario("Dado/abc.ser");

        // Tela principal
        JFrame frame = new JFrame("POST-IT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // tamanho maior para melhor visualização

        // Painel principal C0
        JPanel C0 = new JPanel();
        C0.setLayout(new BoxLayout(C0, BoxLayout.Y_AXIS));
        C0.setBackground(Color.LIGHT_GRAY);

        // C1 - botões de visualização (Dia, Mês, Total)
        JPanel C1 = new JPanel(new GridLayout(1, 3));
        C1.setOpaque(false);
        C1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        Dimension tamanhoBotaoC1 = new Dimension(170, 40);

        JButton btnDia = fazerbotao("Dia", tamanhoBotaoC1);
        JButton btnMes = fazerbotao("Mês", tamanhoBotaoC1);
        JButton btnTotal = fazerbotao("Total", tamanhoBotaoC1);

        C1.add(btnDia);
        C1.add(btnMes);
        C1.add(btnTotal);

        // C2 - painel de post-its
        C2 = new JPanel(new GridLayout(0, 4, 15, 30));
        C2.setOpaque(false);
        Border paddingBorder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
        C2.setBorder(paddingBorder);

        JScrollPane C2scroll = new JScrollPane(C2);
        C2scroll.setOpaque(false);
        C2scroll.getViewport().setOpaque(false);
        C2scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Popula C2 com post-its existentes
        atualizarPostIts();

        // C3 - botões "Usuário" e "Nova task"
        JPanel C3 = new JPanel(new BorderLayout());
        C3.setOpaque(false);
        C3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        Dimension tamanhoBotaoC3 = new Dimension(170, 60);
        JButton btnUsuario = fazerbotao("Usuário", tamanhoBotaoC3);
        btnUsuario.addActionListener(e -> verUsuario());

        JButton bntNewTask = fazerbotao("Nova task", tamanhoBotaoC3);
        bntNewTask.addActionListener(e -> criarNovaTask());

        C3.add(btnUsuario, BorderLayout.WEST);
        C3.add(bntNewTask, BorderLayout.EAST);
        C3.setBorder(paddingBorder);

        // Monta a tela
        C0.add(C1);
        C0.add(Box.createVerticalStrut(15));
        C0.add(C2scroll);
        C0.add(Box.createVerticalStrut(15));
        C0.add(C3);

        frame.add(C0, BorderLayout.CENTER);

        return frame;
    }

    private JButton fazerbotao(String texto, Dimension tamanho) {
        JButton btn = new JButton(texto);
        btn.setPreferredSize(tamanho);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Atualiza painel de post-its
    private void atualizarPostIts() {
        C2.removeAll();
        for (TaskAbstrata task : user.TaskList) {
            Postit postit = new Postit(task);
            C2.add(postit.PanelPostit());
        }
        C2.revalidate();
        C2.repaint();
    }

    // Cria nova task e adiciona ao painel
    private void criarNovaTask() {

        InserirTarefa tela = new InserirTarefa();
        TaskAbstrata nova_task = tela.TaskNova;

        tela.setVisible(true);
    
        user.adicionarTask(nova_task);
 

        // Cria Post-it visual
        Postit newPostit = new Postit(nova_task);
        C2.add(newPostit.PanelPostit());
        C2.revalidate();
        C2.repaint();
    }

    // Abre janela exibindo usuário
    private void verUsuario() {

        telaUsuario tela = new telaUsuario(this.user);
        tela.setVisible(true);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu app = new Menu();
            TelaCadastroUsuario.salvarUsuario(); // garante que usuário exista
            app.criarJanela().setVisible(true);
        });
    }
}
