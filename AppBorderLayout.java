import java.awt.BorderLayout;
import java.awt.Color; // Não é mais estritamente necessário, mas mantido
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.text.DateFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;


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
        Dimension tamanhoFixoPostit = new Dimension(250, 250);
        for(int i = 0; i<2; i++) {

            Postit newPostit;
            newPostit = new Postit("Trabalho de Mc322", "15/02/2004", "Faculdade", false);
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