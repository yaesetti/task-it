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
        JTextField tituloField = new JTextField(20);
        JTextField descricaoField = new JTextField(20);
        JTextField tituloCategoriaField = new JTextField(20);
        JTextField descCategoriaField = new JTextField(20);
        JTextField dataHoraField = new JTextField("2026-12-25 08:30", 20); // Sugestão de format
        JComboBox<String> corCategoriaBox = new JComboBox<>(COLOR_MAP.keySet().toArray(new String[0]));


        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 10, 5)); // 6 linhas, 2 colunas, espaçamento
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Título da Task:"));
        inputPanel.add(tituloField);
        inputPanel.add(new JLabel("Descrição da Task:"));
        inputPanel.add(descricaoField);
        inputPanel.add(new JLabel("Nome da Categoria:"));
        inputPanel.add(tituloCategoriaField);
        inputPanel.add(new JLabel("Descrição da Categoria:"));
        inputPanel.add(descCategoriaField);
        inputPanel.add(new JLabel("Cor da Categoria:"));
        inputPanel.add(corCategoriaBox);
        inputPanel.add(new JLabel("Data e Hora (AAAA-MM-DD HH:mm):"));
        inputPanel.add(dataHoraField);


        int result = JOptionPane.showConfirmDialog(
            this,
            inputPanel,
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

            } catch (DateTimeParseException e) {
                 JOptionPane.showMessageDialog(null, "Formato de data errado, tente de novo :)", "Erro no formato de data", JOptionPane.ERROR_MESSAGE);
            } catch (DataInvalidaException ex) {
                 JOptionPane.showMessageDialog(null, "Erro ao criar a Task: " + ex.getMessage(), "Erro no formato de data", JOptionPane.ERROR_MESSAGE);
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