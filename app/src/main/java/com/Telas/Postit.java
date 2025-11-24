 package com.Telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout; // Não é mais estritamente necessário, mas mantido
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class Postit extends JPanel {

    private JTextPane titleArea;
    private JTextArea dataArea;
    private JTextArea categArea;
    private JCheckBox feitoArea;

    public Postit(String title, String data, String categ, Boolean feito) {

        this.setBackground(new Color(255, 255, 153));
        this.setPreferredSize(new Dimension(200, 200));
        this.setLayout(new BorderLayout());

        JPanel Top = new JPanel();
        Top.setLayout(new GridLayout(0, 1, 4, 2));
        Top.setOpaque(false);
        titleArea = new JTextPane();
        titleArea.setText(title);
        titleArea.setOpaque(false);
        titleArea.setBorder(null);
        titleArea.setFont(new Font("Segoe Print", Font.ITALIC, 17));
        titleArea.setEditable(false);
        StyledDocument doc = titleArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        Top.add(titleArea);

        titleArea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        // 2. DETECTOR DE CLIQUES: Faz o texto agir como botão
        titleArea.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                // AQUI VOCÊ COLOCA A AÇÃO DO CLIQUE
                System.out.println("Cliquei no título: " + title);
                
                // Exemplo: Mudar a cor do fundo para indicar seleção?
                // setBackground(Color.PINK);
            }
        });

        JPanel linha = new JPanel();
        linha.setBackground(new Color(0, 0, 0)); // Um amarelo mais escuro/queimado
        linha.setPreferredSize(new Dimension(130, 2)); // 100px de largura, 2px de altura
        linha.setMaximumSize(new Dimension(100, 2));   // Garante que não estique
        
        // Wrapper para centralizar a linha horizontalmente
        JPanel linhaWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        linhaWrapper.setOpaque(false);
        linhaWrapper.add(linha);
        
        Top.add(linhaWrapper);

        dataArea = new JTextArea("  "+data);
        this.configurarAreaTexto(dataArea, "Segoe Print", Font.BOLD, 13);
        Top.add(dataArea);

        categArea = new JTextArea("  Categoria: "+categ);
        this.configurarAreaTexto(categArea, "Segoe Print", Font.BOLD, 13);
        Top.add(categArea);
        this.add(Top, BorderLayout.NORTH);

        JPanel Down = new JPanel();
        Down.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Down.setOpaque(false);
        Down.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));
        feitoArea = new JCheckBox("Feito", feito); // O segundo parâmetro marca ou desmarca
        feitoArea.setOpaque(false);
        feitoArea.setFont(new Font("Segoe Print", Font.BOLD, 10));
        feitoArea.setEnabled(true);
        feitoArea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Down.add(feitoArea);
        this.add(Down, BorderLayout.SOUTH);

    }

    private void configurarAreaTexto(JTextArea textArea, String fontName, int style, int size) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setBorder(null);
        textArea.setFont(new Font(fontName, style, size));
        textArea.setEditable(false);
    }

    public JPanel PanelPostit() {

        JPanel Wrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Wrapper.setOpaque(false);
        Wrapper.add(this);

        return Wrapper;
    }
    
}
