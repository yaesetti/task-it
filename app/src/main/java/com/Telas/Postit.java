package com.telas;

import com.tasks.*;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Color;

public class Postit extends JPanel {

    private final JTextPane titleArea;
    private final JTextArea dataArea;
    private final JTextArea categArea;
    private final JCheckBox feitoArea;
    private final Task tarefaOriginal;

    public Postit(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("IllegalArgumentException: Essa task é null");
        }

        this.tarefaOriginal = task;
        String title = task.getTitulo();

        Categoria categoria = task.getCategoria();
        String categ;
        Color corFundo;
        if (categoria == null) {
            categ = "Sem categoria";
            corFundo = new Color(255, 255, 153);
        }
        else {
            categ = categoria.getNome();
            corFundo = categoria.getCor();
        }

        String desc = task.getDescricao();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data;
        if (task.getData() == null) {
            data = "Sem data";
        }
        else {
            data = task.getData().format(formatter);
        }

        boolean feito = task.getFeito();

        // Criação do painel principal do post-it
        this.setBackground(corFundo);
        this.setPreferredSize(new Dimension(200, 200));
        this.setLayout(new BorderLayout());

        // Criação de painel para abrigar o título centralizado
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
        if (doc.getLength() > 0) {
            doc.setParagraphAttributes(0, doc.getLength(), center, false);
        }
        else {
            try {
                doc.insertString(0, title, null);
            } catch (javax.swing.text.BadLocationException e) {
                e.printStackTrace();
            }
        }
        Top.add(titleArea);

        // Faz com que o título emule um botão que leva para a segunda visualização
        titleArea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        titleArea.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                PostitDetail detail = new PostitDetail(task);
                detail.setVisible(true);
            }
        });

        // Painel auxiliar para abrigar a linha sob o título
        JPanel linha = new JPanel();
        linha.setBackground(new Color(0, 0, 0)); // Um amarelo mais escuro/queimado
        linha.setPreferredSize(new Dimension(130, 2)); 
        linha.setMaximumSize(new Dimension(130, 2)); 
        // Wrapper para centralizar a linha horizontalmente
        JPanel linhaWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 5));
        linhaWrapper.setOpaque(false);
        linhaWrapper.add(linha);
        Top.add(linhaWrapper);

        // Painel para abrigar a data do prazo
        dataArea = new JTextArea("  Prazo: "+data);
        this.configurarAreaTexto(dataArea, "Segoe Print", Font.BOLD, 13);
        Top.add(dataArea);

        // Painel para abrigar a categoria da task
        categArea = new JTextArea("  Categoria: "+categ);
        this.configurarAreaTexto(categArea, "Segoe Print", Font.BOLD, 13);
        Top.add(categArea);
        this.add(Top, BorderLayout.NORTH);

        // Criação de painel na base da janela 
        JPanel Down = new JPanel();
        Down.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Down.setOpaque(false);
        Down.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));

        // Painel em formato de caixa assinalável para marcar a conclusão
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
