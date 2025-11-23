import java.awt.BorderLayout;
import java.awt.Color; // Não é mais estritamente necessário, mas mantido
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import com.tasks.Categoria;
import com.tasks.TaskPadrao;
import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class AppBorderLayout {

    public JFrame criarJanela() {
        
        // Criação da tela principal
        JFrame frame = new JFrame("POST-IT");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        
        // Criação de C0 - Painel de fundo para toda a tela
        JPanel C0 = new JPanel();
        C0.setLayout(new BoxLayout(C0,BoxLayout.Y_AXIS)); // Disposição de caixas empilhadas
        C0.setBackground(Color.LIGHT_GRAY);

        // Criação de C1 - Painel para abas de visualização
        JPanel C1 = new JPanel();
        C1.setLayout(new GridLayout(1, 3)); // Disposição de malha 1x3
        C1.setOpaque(false);
        C1.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        // Botões de C1
        Dimension tamanhoBotaoC1 = new Dimension(170, 40);        
        JButton btnDia = new JButton("Dia"); // Dia
        btnDia.setPreferredSize(tamanhoBotaoC1);
        btnDia.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JButton btnMes = new JButton("Mês"); // Mês
        btnMes.setPreferredSize(tamanhoBotaoC1);
        btnMes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JButton btnTotal = new JButton("Total"); // Total
        btnTotal.setPreferredSize(tamanhoBotaoC1);
        btnTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Adiciona oos botões em C1
        C1.add(btnDia);
        C1.add(btnMes);
        C1.add(btnTotal);

        // Criação de C2 - Painel com post-its
        JPanel C2 = new JPanel();
        C2.setLayout(new GridLayout(0, 4, 15, 30)); // Disposição em malha com 4 colunas de post-its
        C2.setOpaque(false);
        
        int padding = 20; // Espaçamento entre as bordas da tela
        Border paddingBorder = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        C2.setBorder(paddingBorder);

        // Tornando C2 scrollável apenas na vertical
        JScrollPane C2scroll = new JScrollPane(C2);
        C2scroll.setOpaque(false);
        C2scroll.getViewport().setOpaque(false);
        C2scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Inserção artificial de post-its
        Categoria catUrgente = new Categoria("Urgente", "Prioridade alta", new Color(255, 102, 102)); // Vermelho claro
        Categoria catTrabalho = new Categoria("Trabalho", "Coisas do escritório", new Color(173, 216, 230)); // Azul claro
        Categoria catPessoal = new Categoria("Pessoal", "Minha vida", new Color(255, 255, 153)); // Amarelo clássico
        Categoria catEstudos = new Categoria("Estudos", "Faculdade", new Color(144, 238, 144)); // Verde claro
        // Lista para alternar categorias no loop
        Categoria[] minhasCategorias = {catUrgente, catTrabalho, catPessoal, catEstudos};
        Dimension tamanhoFixoPostit = new Dimension(250, 250);
        for(int i = 0; i<2; i++) {

            Categoria categoriaDaVez = minhasCategorias[i % 4]; 

            TaskPadrao task = new TaskPadrao(
                "Task " + (i + 1),
                "Descrição detalhada da tarefa " + (i + 1) + "...",
                categoriaDaVez,
                LocalDateTime.now().plusDays(i + 1),
                new ArrayList<>()
            );

            Postit newPostit;
            newPostit = new Postit(task);
            JPanel Wrapper = newPostit.PanelPostit();
            C2.add(Wrapper);
        }

        // Criação de C3 - Painel voltado a botões para acessar perfil e adicionar task
        JPanel C3 = new JPanel();
        C3.setLayout(new BorderLayout());
        C3.setOpaque(false);
        C3.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // Botões de C3
        Dimension tamanhoBotaoC3 = new Dimension(170, 60); // Ex: 100 de largura, 50 de altura
        JButton btnUsuario = new JButton("Usuário");
        btnUsuario.setPreferredSize(tamanhoBotaoC3); 
        btnUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        JButton bntNewTask = new JButton("Nova task");
        bntNewTask.setPreferredSize(tamanhoBotaoC3);
        bntNewTask.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // Adiciona os botões em C3
        C3.add(btnUsuario, BorderLayout.WEST); 
        C3.add(bntNewTask, BorderLayout.EAST);
        C3.setBorder(paddingBorder);

        // Configura a disposição geral em C0
        C0.add(C1);
        C0.add(Box.createVerticalStrut(15));
        C0.add(C2scroll);
        C0.add(Box.createVerticalStrut(15));
        C0.add(C3);

        // Adiciona C0 na tela principal
        frame.add(C0, BorderLayout.CENTER);

        return frame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppBorderLayout app = new AppBorderLayout();
            app.criarJanela().setVisible(true);
        });
    }
}