package com.telas;


import com.tasks.*;
import com.gerenciadores.Gerenciador; 
import com.serializacao.FuncoesSerial; 

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.format.DateTimeFormatter;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;             
import javax.swing.BoxLayout;
import javax.swing.JButton;         
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;     
import javax.swing.JSeparator;      
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.util.ArrayList;

import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PostitDetail extends JFrame{
    
    private Task tarefaOriginal;
    private Gerenciador gerenciador;
    private List<JCheckBox> checkBoxesSubtarefas;
    private JCheckBox chkFeito; 
    private boolean flagAtualizacao = false;

    public PostitDetail(Task task, Gerenciador ger) {
        
        this.tarefaOriginal = task;
        this.gerenciador = ger;
        this.checkBoxesSubtarefas = new ArrayList<>();

        // Informações básicas da task que serão exibidas na janela secundária
        String title = task.getTitulo();
        String categ = (task.getCategoria()).getNome();
        String categDesc = (task.getCategoria()).getDescricao();
        String desc = task.getDescricao();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = task.getData().format(formatter);
        Boolean feito = task.getFeito();
        Color corFundo = (task.getCategoria()).getCor();

        // Criação da janela secundária
        this.setTitle("Detalhes: " + title);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Painel principal representando o post-it em detalhe
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        if (corFundo != null) {
            painelPrincipal.setBackground(corFundo);
        } else {
            painelPrincipal.setBackground(new Color(255, 255, 153));
        }
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Criação de painel para abrigar o título centralizado
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setOpaque(false);

        // Título
        JTextPane titleArea = new JTextPane();
        titleArea.setText(title);
        titleArea.setOpaque(false);
        titleArea.setBorder(null);
        titleArea.setFont(new Font("Segoe Print", Font.BOLD | Font.ITALIC, 24)); 
        titleArea.setEditable(false);

        // Centralizar
        StyledDocument doc = titleArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        topPanel.add(titleArea);

        // Painel auxiliar para abrigar a linha sob o título
        JPanel linha = new JPanel();
        linha.setBackground(new Color(0, 0, 0)); // Amarelo queimado
        linha.setPreferredSize(new Dimension(160, 2)); // Mais larga que no postit pequeno
        linha.setMaximumSize(new Dimension(160, 2));
        JPanel linhaWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        linhaWrapper.setOpaque(false);
        linhaWrapper.add(linha);
        topPanel.add(linhaWrapper);

        // Painel para o prazo
        JTextArea dataArea = new JTextArea("Prazo: " + data);
        configurarAreaTexto(dataArea, "Segoe Print", Font.BOLD, 14);
        topPanel.add(dataArea);

        // Painel para a categoria
        JTextArea categArea;
        if (categDesc.equals("")) {
            categArea = new JTextArea("Categoria: " + categ);
        }
        else {
            categArea = new JTextArea("Categoria: " + categ + " -> "+ categDesc);
        }
        configurarAreaTexto(categArea, "Segoe Print", Font.BOLD, 14);
        topPanel.add(categArea);
        topPanel.add(javax.swing.Box.createVerticalStrut(20));
        painelPrincipal.add(topPanel, BorderLayout.NORTH);

        // Área de Texto da Descrição
        JPanel conteudoScroll = new JPanel();
        conteudoScroll.setLayout(new BoxLayout(conteudoScroll, BoxLayout.Y_AXIS));
        conteudoScroll.setOpaque(false);

        JTextArea lblDesc = new JTextArea("Descrição:");
        configurarAreaTexto(lblDesc, "Segoe Print", Font.BOLD, 14);
        lblDesc.setForeground(Color.DARK_GRAY);
        conteudoScroll.add(lblDesc);

        JTextArea areaDescricao = new JTextArea(desc);
        areaDescricao.setFont(new Font("Segoe Print", Font.PLAIN, 16));
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        areaDescricao.setOpaque(false);
        areaDescricao.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        areaDescricao.setEditable(false);
        conteudoScroll.add(areaDescricao);

        // Áreade de texto/interação com subtarefas
        List<Subtarefa> listaSubs = task.getSubtarefas();
        if (listaSubs != null && !listaSubs.isEmpty()) {
            
            conteudoScroll.add(Box.createVerticalStrut(20)); 

            JTextArea lblSub = new JTextArea("Subtarefas:");
            configurarAreaTexto(lblSub, "Segoe Print", Font.BOLD, 14);
            lblSub.setForeground(Color.DARK_GRAY);
            
            lblSub.setAlignmentX(Component.CENTER_ALIGNMENT); 
            conteudoScroll.add(lblSub);

            for (Subtarefa sub : listaSubs) {

                // Área com caixa de conclusão para cada subtarefa
                JCheckBox chkSub = new JCheckBox(sub.getTitulo(), sub.getFeito());
                chkSub.setOpaque(false);
                chkSub.setFont(new Font("Segoe Print", Font.PLAIN, 14));
                chkSub.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0)); 
                
                chkSub.setAlignmentX(Component.CENTER_ALIGNMENT);
                chkSub.setHorizontalAlignment(JCheckBox.CENTER);
                
                chkSub.addItemListener(e -> {

                    if (flagAtualizacao) return;

                    boolean isChecked = (e.getStateChange() == ItemEvent.SELECTED);
                    sub.setFeito(isChecked);
                    
                    verificarTodasSubtarefas();
                    if (gerenciador != null) FuncoesSerial.salvarGerenciador(gerenciador);
                });

                checkBoxesSubtarefas.add(chkSub);
                conteudoScroll.add(chkSub); 
            }
        }

        JScrollPane scroll = new JScrollPane(conteudoScroll);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(0, 0, 0, 30))); 
        
        painelPrincipal.add(scroll, BorderLayout.CENTER);

        // Área com caixa de conclusão da task
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JCheckBox chkFeito = new JCheckBox("Feito", feito);
        chkFeito.setOpaque(false);
        chkFeito.setFont(new Font("Segoe Print", Font.BOLD, 14));
        
        chkFeito.addItemListener(e -> {

            if (flagAtualizacao) return; 
        
            flagAtualizacao = true;

           try {
                boolean isChecked = (e.getStateChange() == ItemEvent.SELECTED);
                
                tarefaOriginal.setFeito(isChecked);
                tarefaOriginal.setFeitoTodasSubtarefas(isChecked);
                
                for (JCheckBox cb : checkBoxesSubtarefas) {
                    cb.setSelected(isChecked);
                }

                if (gerenciador != null) FuncoesSerial.salvarGerenciador(gerenciador);
            
            } finally {
                flagAtualizacao = false; 
            }
        });
        bottomPanel.add(chkFeito);

        painelPrincipal.add(bottomPanel, BorderLayout.SOUTH);

        
        add(painelPrincipal);

    }

    private void configurarAreaTexto(JTextArea textArea, String fontName, int style, int size) {
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setOpaque(false);
        textArea.setBorder(null);
        textArea.setFont(new Font(fontName, style, size));
        textArea.setEditable(false);
    }

    private void verificarTodasSubtarefas() {
        if (checkBoxesSubtarefas.isEmpty()) return;

        boolean todasMarcadas = true;
        for (JCheckBox cb : checkBoxesSubtarefas) {
            if (!cb.isSelected()) {
                todasMarcadas = false;
                break;
            }
        }

        if (chkFeito.isSelected() != todasMarcadas) {
            flagAtualizacao = true;
            chkFeito.setSelected(todasMarcadas);
            tarefaOriginal.setFeito(todasMarcadas);
            
            flagAtualizacao = false;
        }
    }

}


