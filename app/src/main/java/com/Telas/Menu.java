package com.telas;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Objects;

import javax.swing.*;
import javax.swing.border.Border;

import com.gerenciadores.Gerenciador;
import com.serializacao.FuncoesSerial;
import com.tasks.Task;
import com.tasks.Categoria;
import com.usuarios.Usuario;

public class Menu {

    private Usuario user;
    private JPanel C2;
    private Gerenciador ger;

    public JFrame criarJanela(Usuario user1, Gerenciador gerenciador) {

        this.ger = gerenciador;
        this.user = user1;
        JFrame frame = new JFrame("POST-IT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
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
        btnDia.addActionListener(e -> atualizaDia());

        JButton btnMes = fazerbotao("Mês", tamanhoBotaoC1);
        btnMes.addActionListener(e -> atualizaMes());

        JButton btnTotal = fazerbotao("Total", tamanhoBotaoC1);
        btnTotal.addActionListener(e -> atualizarPostIts());

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
        if (C2 == null) {
            return;
        }
        C2.removeAll();

        if (this.user == null || this.ger == null) {
            C2.revalidate();
            C2.repaint();
            return;
        }

        if (this.user.getTaskIds() == null || this.user.getTaskIds().isEmpty()) {
            C2.revalidate();
            C2.repaint();
            return;
        }

        List<UUID> userTaskIds = this.user.getTaskIds();
        List<Task> tasks = this.ger.getGerTasks().getTasks();

        if (tasks != null && !tasks.isEmpty() && userTaskIds != null && !userTaskIds.isEmpty()) {
            for (Task task : tasks) {
                if (task == null || task.getId() == null) {
                    continue;
                }
                if (userTaskIds.contains(task.getId())) {
                    Postit postit = new Postit(task, ger);
                    C2.add(postit.PanelPostit());
                }
            }
        }

        C2.revalidate();
        C2.repaint();
    }

    /**
     * Filtra as Tasks de modo a exibir somente as que possuem data == dia atual
     */
    private void atualizaDia() {
        if (C2 == null) return;
        C2.removeAll();

        if (this.user == null || this.ger == null) {
            C2.revalidate();
            C2.repaint();
            return;
        }

        List<UUID> userTaskIds = this.user.getTaskIds();
        List<Task> tasks = this.ger.getGerTasks() == null ? null : this.ger.getGerTasks().getTasks();

        if (tasks != null && !tasks.isEmpty() && userTaskIds != null && !userTaskIds.isEmpty()) {
            java.time.LocalDate hoje = java.time.LocalDate.now();
            for (Task task : tasks) {
                if (task == null || task.getId() == null) continue;
                if (!userTaskIds.contains(task.getId())) continue;

                if (task.getData() != null) {
                    if (task.getData().toLocalDate().isEqual(hoje)) {
                        Postit postit = new Postit(task, ger);
                        C2.add(postit.PanelPostit());
                    }
                }
            }
        }

        C2.revalidate();
        C2.repaint();
    }

    /**
     * Filtra as Tasks de modo a exibir somente as que possuem data no mês atual
     */
    private void atualizaMes() {
        if (C2 == null) return;
        C2.removeAll();

        if (this.user == null || this.ger == null) {
            C2.revalidate();
            C2.repaint();
            return;
        }

        List<UUID> userTaskIds = this.user.getTaskIds();
        List<Task> tasks = this.ger.getGerTasks() == null ? null : this.ger.getGerTasks().getTasks();

        if (tasks != null && !tasks.isEmpty() && userTaskIds != null && !userTaskIds.isEmpty()) {
            java.time.LocalDate hoje = java.time.LocalDate.now();
            for (Task task : tasks) {
                if (task == null || task.getId() == null) continue;
                if (!userTaskIds.contains(task.getId())) continue;

                if (task.getData() != null) {
                    java.time.LocalDate d = task.getData().toLocalDate();
                    if (d.getYear() == hoje.getYear() && d.getMonthValue() == hoje.getMonthValue()) {
                        Postit postit = new Postit(task, ger);
                        C2.add(postit.PanelPostit());
                    }
                }
            }
        }

        C2.revalidate();
        C2.repaint();
    }

    /**
     * Cria uma Task nova usando a API de GerenciadorDeTasks (criarTask(...))
     * e adiciona ao painel. Também transfere subtarefas do diálogo para a Task criada.
     */
    private void criarNovaTask() {
        InserirTarefa tela = new InserirTarefa();
        // mostra diálogo e aguarda (o diálogo usa JOptionPane)
        tela.setVisible(true);

        // se usuário cancelou, encerra
        if (!tela.isDeuCerto()) {
            return;
        }

        // monta categoria a partir do diálogo
        Categoria categoria = new Categoria(tela.getTituloCategoria(), tela.getDescCategoria(), tela.getCorCategoria());

        // chama API centralizada para criar a task (valida limites, data, etc.)
        Task criada = this.ger.getGerTasks().criarTask(
            tela.getTitulo(),
            tela.getDescricao(),
            categoria,
            tela.getData(),
            this.user
        );

        if (criada == null) {
            JOptionPane.showMessageDialog(null, "Não foi possível criar a task (limite atingido ou data inválida).", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // copia subtarefas do diálogo para a Task criada
        List<String> subs = tela.getSubtarefas();
        if (subs != null) {
            for (String s : subs) {
                if (s != null && !s.trim().isEmpty()) {
                    criada.adicionarSubtarefa(s.trim());
                }
            }
        }

        FuncoesSerial.salvarGerenciador(ger);
        atualizarPostIts();
    }

    /**
     * Abre a janela que mostra as informações do usuario
     */
    private void verUsuario() {
        telaUsuario tela = new telaUsuario(this.user);
        tela.setVisible(true);
    }
}
