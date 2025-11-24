package com.Telas;

import com.tasks.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox; 
import javax.swing.BoxLayout;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.excecoes.DataInvalidaException;

public class InserirTarefa extends JFrame {

    protected TaskAbstrata TaskNova = null;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    boolean deu_certo = false;
    
    // Mapeamento de nomes de cores para objetos Color do AWT
    private static final Map<String, Color> COLOR_MAP = new HashMap<>();
    static {
        COLOR_MAP.put("Importante - vermelho", Color.PINK);
        COLOR_MAP.put("Ta de boas - verde", Color.GREEN);
        COLOR_MAP.put("Atenção - amarelo", Color.YELLOW.brighter());
    }

    public InserirTarefa(){

        // Campos de entrada
        JTextField tituloField = new JTextField(15);
        JTextField descricaoField = new JTextField(15);
        JTextField tituloCategoriaField = new JTextField(15);
        JTextField descCategoriaField = new JTextField(15);
        JTextField dataHoraField = new JTextField("2026-12-25 08:30", 15); // Sugestão de format
        JComboBox<String> corCategoriaBox = new JComboBox<>(COLOR_MAP.keySet().toArray(new String[0]));
        JTextArea subTarefasArea = new JTextArea(3, 15);
        subTarefasArea.setLineWrap(true);
        subTarefasArea.setWrapStyleWord(true);
        JScrollPane scrollSubTarefas = new JScrollPane(subTarefasArea);


        JPanel mainContainer = new JPanel(new BorderLayout());
        
        // Painel A: Apenas para os campos de linha única (GridLayout fica compacto aqui)
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5)); // (0 linhas = automático)
        fieldsPanel.add(new JLabel("Título da Task:"));
        fieldsPanel.add(tituloField);
        fieldsPanel.add(new JLabel("Descrição da Task:"));
        fieldsPanel.add(descricaoField);
        fieldsPanel.add(new JLabel("Nome da Categoria:"));
        fieldsPanel.add(tituloCategoriaField);
        fieldsPanel.add(new JLabel("Descrição da Categoria:"));
        fieldsPanel.add(descCategoriaField);
        fieldsPanel.add(new JLabel("Cor da Categoria:"));
        fieldsPanel.add(corCategoriaBox);
        fieldsPanel.add(new JLabel("Data (AAAA-MM-DD HH:mm):"));
        fieldsPanel.add(dataHoraField);

        // Painel B: Apenas para a Subtarefa (Fica embaixo)
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Espacinho no topo
        subPanel.add(new JLabel("Subtarefas (uma por linha):"), BorderLayout.NORTH);
        subPanel.add(scrollSubTarefas, BorderLayout.CENTER);

        // Juntando tudo no container principal
        mainContainer.add(fieldsPanel, BorderLayout.NORTH);
        mainContainer.add(subPanel, BorderLayout.CENTER);

        // Agora passamos o 'mainContainer' para o JOptionPane em vez do inputPanel antigo
        int result = JOptionPane.showConfirmDialog(
            this,
            mainContainer, // Mudança aqui
            "Insira os Dados da Task",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            deu_certo = true;
            
            String titulo = tituloField.getText();
            String descricao = descricaoField.getText();
            String tituloCategoria = tituloCategoriaField.getText();
            String DescCategoria = descCategoriaField.getText();
            String dataHoraStr = dataHoraField.getText();
            String corSelecionadaStr = (String) corCategoriaBox.getSelectedItem();
            Color corCategoria = COLOR_MAP.get(corSelecionadaStr);
            
            LocalDateTime data = null;
            
            try {
                data = LocalDateTime.parse(dataHoraStr, FORMATTER);
                
                Categoria categoria = new Categoria(tituloCategoria, DescCategoria, corCategoria);
                this.TaskNova = new TaskPadrao(titulo, descricao, categoria, data, null);

                String textoSubtarefas = subTarefasArea.getText();
                String[] linhas = textoSubtarefas.split("\\r?\\n");
                
                for (String linha : linhas) {
                    if (!linha.trim().isEmpty()) {
                        this.TaskNova.adicionarSubtarefa(linha.trim());
                    }
                }

            } catch (DateTimeParseException e) {
                 JOptionPane.showMessageDialog(null, "Formato de data errado. Use AAAA-MM-DD HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
                 deu_certo = false; // Impede abrir a janela de sucesso se der erro
            } catch (DataInvalidaException ex) {
                 JOptionPane.showMessageDialog(null, "Erro na Task: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                 deu_certo = false;
            } catch (Exception ex) {
                 ex.printStackTrace();
                 deu_certo = false;
            }

        } else {
            this.dispose();
        }


        if(deu_certo){

            // Criação da janela secundária
            this.setTitle("Task criada com sucesso :) ");
            this.setSize(400, 400);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setLayout(new BorderLayout());
            // Configuração da janela principal após a criação bem-sucedida
            JPanel painelPrincipal = new JPanel(new BorderLayout());
            painelPrincipal.setBackground(new java.awt.Color(255, 255, 153));
            painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            JLabel labelGif = new JLabel(new ImageIcon("Imagens/kid-meme.gif"));
            painelPrincipal.add(labelGif, BorderLayout.CENTER);
            this.add(painelPrincipal);
            this.setVisible(true);
        }
    }
}