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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.awt.Color;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import com.gerenciadores.Gerenciador;
import com.serializacao.FuncoesSerial;
import com.usuarios.Usuario;

public class Postit extends JPanel {

    private final JTextPane titleArea;
    private final JTextArea dataArea;
    private final JTextArea categArea;
    private final JCheckBox feitoArea;
    private final Task tarefaOriginal;
    private final Gerenciador gerenciador;

    public Postit(Task task, Gerenciador ger) {
        if (task == null) {
            throw new IllegalArgumentException("IllegalArgumentException: Essa task é null");
        }

        this.tarefaOriginal = task;
        this.gerenciador = ger;

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

        // Criação de painel para abrigar o título
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
                PostitDetail detail = new PostitDetail(task, gerenciador);
                detail.setVisible(true);
                detail.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosed(WindowEvent e) {
                        atualizarVisualizacao();
                    }
                });
            }
        });

        JPanel linha = new JPanel();
        linha.setBackground(new Color(0, 0, 0)); 
        linha.setPreferredSize(new Dimension(130, 2)); 
        linha.setMaximumSize(new Dimension(130, 2)); 
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
        if (tarefaOriginal instanceof TaskPeriodica) {
            TaskPeriodica tp = (TaskPeriodica) tarefaOriginal;
            long dias = tp.getRecorrencia().toDays();
            
            // Texto indicativo
            JTextArea recArea = new JTextArea("   --- Repetir a cada: " + dias + " dias ---");
            // Usando a mesma formatação visual dos outros campos
            configurarAreaTexto(recArea, "Segoe Print", Font.BOLD, 11); // Fonte levemente menor para caber
             
            
            Top.add(recArea);
        }

        this.add(Top, BorderLayout.NORTH);

        // Criação de painel na base da janela 
        JPanel Down = new JPanel(new BorderLayout());
        Down.setOpaque(false);
        Down.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0));

        // Painel em formato de caixa de conclusão
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        rightPanel.setOpaque(false);

        feitoArea = new JCheckBox("Feito", feito);
        feitoArea.setOpaque(false);
        feitoArea.setFont(new Font("Segoe Print", Font.BOLD, 10));
        feitoArea.setEnabled(true);
        feitoArea.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        feitoArea.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                boolean isChecked = (e.getStateChange() == ItemEvent.SELECTED);
                
                tarefaOriginal.setFeito(isChecked);
                tarefaOriginal.setFeitoTodasSubtarefas(isChecked);
              
                if (gerenciador != null) {
                    FuncoesSerial.salvarGerenciador(gerenciador);
                }

                revalidate();
                repaint();
            }
        });

        rightPanel.add(feitoArea);

        // Botão para apagar a Task
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);

        JButton apagarBtn = new JButton("Apagar");
        apagarBtn.setOpaque(false);
        apagarBtn.setFont(new Font("Segoe Print", Font.BOLD, 10));
        apagarBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        apagarBtn.addActionListener(ev -> {
            int escolha = JOptionPane.showConfirmDialog(Postit.this, 
                "Tem certeza que deseja apagar essa Task?", 
                "Confirmar exclusao", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.WARNING_MESSAGE);
                if (escolha != JOptionPane.YES_OPTION) return;

                UUID id = tarefaOriginal.getId();

                if (gerenciador != null) {
                    boolean removed = gerenciador.getGerTasks().apagarTask(id);

                    for (Usuario usuario : tarefaOriginal.getUsuariosDonos()) {
                        usuario.removerTask(id);
                    }

                    FuncoesSerial.salvarGerenciador(ger);
                }

                java.awt.Container parent = Postit.this.getParent();
                if (parent != null) {
                    parent.remove(Postit.this);
                    parent.revalidate();
                    parent.repaint();
                }
        });
        leftPanel.add(apagarBtn);

        // Juntando os dois botões no painel Down
        Down.add(leftPanel, BorderLayout.WEST);
        Down.add(rightPanel, BorderLayout.EAST);

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

    private void atualizarVisualizacao() {
        boolean estaFeito = tarefaOriginal.getFeito();
        feitoArea.setSelected(estaFeito);

        revalidate();
        repaint();
    }
    
}
