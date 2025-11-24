package com.Telas;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.Box;
import javax.swing.ImageIcon;
import com.usuarios.*;
import java.awt.Image;

public class telaUsuario extends JFrame {
    

    public telaUsuario(Usuario usuario) {
        
        // Criação da janela secundária
        // this.setTitle("Perfil do usuário");
        // this.setSize(400, 400);
        // this.setLocationRelativeTo(null);
        // this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        // this.setLayout(new BorderLayout());
        
        // JPanel painelPrincipal = new JPanel(new BorderLayout());
        // painelPrincipal.setBackground(new java.awt.Color(255, 255, 153));
        // painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        // JLabel labelGif = new JLabel(new ImageIcon("Imagens/Homer.jpeg"));
        // painelPrincipal.add(labelGif, BorderLayout.NORTH);
        // this.add(painelPrincipal);
        // this.setVisible(true);

        this.setTitle(" Perfil de " + usuario.getNome());
        this.setSize(300, 350); // Aumenta um pouco para caber os textos
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        
        // C1: Painel principal (usa BoxLayout para organizar verticalmente)
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBackground(new java.awt.Color(255, 255, 153));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // --- 1. Topo: Painel da Imagem (Centralizado) ---
        
        // C1.1: Painel Wrapper para centralizar a imagem (FlowLayout padrão é CENTER)
        JPanel painelImagemWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelImagemWrapper.setOpaque(false); // Mantém o fundo do painelPrincipal

        // C1.1.1: Label da Imagem
        ImageIcon imagemRedimensionada = redimensionarImagem("Imagens/Homer.jpeg", 100, 100);
        JLabel labelImagem = new JLabel(imagemRedimensionada);

        // Para garantir que o JLabel (e, portanto, a imagem) não exceda um tamanho fixo,
        // você pode envolvê-lo em um painel com tamanho máximo, mas o FlowLayout geralmente já funciona bem.
        
        painelImagemWrapper.add(labelImagem);

        JPanel painelInfo = new JPanel();
        painelInfo.setOpaque(false);
        painelInfo.setLayout(new BoxLayout(painelInfo, BoxLayout.Y_AXIS));
        painelInfo.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Rótulo Nome
        JLabel labelNome = new JLabel("Usuário: " + usuario.getNome());
        labelNome.setFont(labelNome.getFont().deriveFont(20f));
        labelNome.setAlignmentX(CENTER_ALIGNMENT); // Centraliza o texto no BoxLayout

        // Rótulo Tasks Abertas
        int tasksAbertas = usuario.contarTasksAbertas();
        JLabel labelTasks = new JLabel("Tasks em aberto: " + tasksAbertas);
        labelTasks.setFont(labelTasks.getFont().deriveFont(16f));
        labelTasks.setAlignmentX(CENTER_ALIGNMENT); // Centraliza o texto no BoxLayout

        painelInfo.add(labelNome);
        painelInfo.add(Box.createVerticalStrut(5)); // Espaçamento vertical
        painelInfo.add(labelTasks);

        // --- Montagem final ---
        painelPrincipal.add(painelImagemWrapper);
        painelPrincipal.add(painelInfo);
        
        this.add(painelPrincipal, BorderLayout.CENTER);
        this.setVisible(true);
    }

    private ImageIcon redimensionarImagem(String caminho, int largura, int altura) {
        ImageIcon originalIcon = new ImageIcon(caminho);
        java.awt.Image imagem = originalIcon.getImage();
        // 1. O retorno de getScaledInstance() é java.awt.Image
        java.awt.Image imagemRedimensionada = imagem.getScaledInstance(largura, altura, Image.SCALE_SMOOTH);
        // 2. O construtor do ImageIcon deve receber o objeto Image, não uma String
        return new ImageIcon(imagemRedimensionada);
    }

}
