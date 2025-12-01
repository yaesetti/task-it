package com.telas;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ItemEvent; 
import java.time.Duration;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;

/**
 * Janela/dialog para coletar os dados de uma nova Task.
 * Não cria a Task aqui — apenas coleta campos e fornece getters para quem chamou.
 */
public class InserirTarefa extends JFrame {
    private String titulo;
    private String descricao;
    private String tituloCategoria;
    private String descCategoria;
    private LocalDateTime data;
    private Color corCategoria;
    private final List<String> subtarefas = new ArrayList<>();
    private boolean isPeriodica = false;
    private Duration recorrencia;
    private LocalDateTime dataFinal;
    
    private boolean deuCerto = false;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // Mapeamento de nomes de cores para objetos Color do AWT
    private static final Map<String, Color> COLOR_MAP = new HashMap<>();
    static {
        COLOR_MAP.put("Importante - vermelho", Color.PINK);
        COLOR_MAP.put("Ta de boas - verde", Color.GREEN);
        COLOR_MAP.put("Atenção - amarelo", Color.YELLOW.brighter());
    }

    public InserirTarefa() {
        // Campos de entrada
        JTextField tituloField = new JTextField(15);
        JTextField descricaoField = new JTextField(15);
        JTextField tituloCategoriaField = new JTextField(15);
        JTextField descCategoriaField = new JTextField(15);
        JTextField dataHoraField = new JTextField("2026-12-25 08:30", 15); // Sugestão de formato
        JComboBox<String> corCategoriaBox = new JComboBox<>(COLOR_MAP.keySet().toArray(new String[0]));
        JTextArea subTarefasArea = new JTextArea(3, 15);
        subTarefasArea.setLineWrap(true);
        subTarefasArea.setWrapStyleWord(true);
        JScrollPane scrollSubTarefas = new JScrollPane(subTarefasArea);

        JCheckBox checkPeriodica = new JCheckBox("É uma tarefa periódica?");
        JTextField diasRecorrenciaField = new JTextField("7", 5);
        JTextField dataFinalField = new JTextField("2027-12-25 08:30", 15);
        dataFinalField.setToolTipText("Deixe vazio para sem data final");

        // Labels para os campos novos
        JLabel lblRecorrencia = new JLabel("Repetir a cada (dias):");
        JLabel lblDataFinal = new JLabel("Data Final (Opcional):");

        // Começam desabilitados
        diasRecorrenciaField.setEnabled(false);
        dataFinalField.setEnabled(false);
        lblRecorrencia.setEnabled(false);
        lblDataFinal.setEnabled(false);

        // Lógica visual: Habilitar campos se checkbox marcado
        checkPeriodica.addItemListener(e -> {
            boolean selecionado = (e.getStateChange() == ItemEvent.SELECTED);
            diasRecorrenciaField.setEnabled(selecionado);
            dataFinalField.setEnabled(selecionado);
            lblRecorrencia.setEnabled(selecionado);
            lblDataFinal.setEnabled(selecionado);
        });

        JPanel mainContainer = new JPanel(new BorderLayout());

        // Painel A: campos de linha única
        JPanel fieldsPanel = new JPanel(new GridLayout(0, 2, 5, 5));
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
        // Adiciona separador ou espaço visual
        fieldsPanel.add(new JLabel("--- Periodicidade ---"));
        fieldsPanel.add(checkPeriodica);

        fieldsPanel.add(lblRecorrencia);
        fieldsPanel.add(diasRecorrenciaField);

        fieldsPanel.add(lblDataFinal);
        fieldsPanel.add(dataFinalField);

        // Painel B: subtarefas
        JPanel subPanel = new JPanel(new BorderLayout());
        subPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        subPanel.add(new JLabel("Subtarefas (uma por linha):"), BorderLayout.NORTH);
        subPanel.add(scrollSubTarefas, BorderLayout.CENTER);

        mainContainer.add(fieldsPanel, BorderLayout.NORTH);
        mainContainer.add(subPanel, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(
            this,
            mainContainer,
            "Insira os Dados da Task",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            // pega valores dos campos
            this.titulo = tituloField.getText();
            this.descricao = descricaoField.getText();
            this.tituloCategoria = tituloCategoriaField.getText();
            this.descCategoria = descCategoriaField.getText();
            String dataHoraStr = dataHoraField.getText();
            String corSelecionadaStr = (String) corCategoriaBox.getSelectedItem();
            this.corCategoria = COLOR_MAP.get(corSelecionadaStr);

            try {
                this.data = LocalDateTime.parse(dataHoraStr, FORMATTER);

                this.isPeriodica = checkPeriodica.isSelected();
                if (this.isPeriodica) {
                    // Tenta ler dias
                    String diasStr = diasRecorrenciaField.getText();
                    if (diasStr == null || diasStr.trim().isEmpty()) {
                        throw new Exception("Para tasks periódicas, informe os dias de recorrência.");
                    }
                    long dias = Long.parseLong(diasStr.trim());
                    if (dias <= 0) throw new Exception("A recorrência deve ser maior que 0 dias.");
                    
                    this.recorrencia = Duration.ofDays(dias);

                    // Tenta ler Data Final (se tiver texto)
                    String fimStr = dataFinalField.getText();
                    if (fimStr != null && !fimStr.trim().isEmpty()) {
                        this.dataFinal = LocalDateTime.parse(fimStr, FORMATTER);
                        if (this.dataFinal.isBefore(this.data)) {
                            throw new Exception("A data final não pode ser antes da data de início.");
                        }
                    } else {
                        this.dataFinal = null; // Sem data final
                    }
                }

                // subtarefas
                String textoSubtarefas = subTarefasArea.getText();
                String[] linhas = textoSubtarefas.split("\\r?\\n");
                for (String linha : linhas) {
                    if (!linha.trim().isEmpty()) {
                        this.subtarefas.add(linha.trim());
                    }
                }

                this.deuCerto = true;

            } catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(null, "Formato de data errado. Use AAAA-MM-DD HH:mm", "Erro", JOptionPane.ERROR_MESSAGE);
                this.deuCerto = false;
            } catch (Exception ex) {
                ex.printStackTrace();
                this.deuCerto = false;
            }
        } else {
            this.deuCerto = false;
            this.dispose();
        }

        if (this.deuCerto) {
            // janela de confirmação simples
            this.setTitle("Task criada com sucesso :) ");
            this.setSize(400, 400);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            this.setLayout(new BorderLayout());
            JPanel painelPrincipal = new JPanel(new BorderLayout());
            painelPrincipal.setBackground(new Color(255, 255, 153));
            painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/Imagens/kid-meme.gif"));
                JLabel labelGif = new JLabel(icon);
                painelPrincipal.add(labelGif, BorderLayout.CENTER);
            } catch (Exception ignored) {
                // recurso pode não existir em tempo de desenvolvimento; não falhar por isso
            }
            this.add(painelPrincipal);
            this.setVisible(true);
        }
    }

    // Getters para o chamador (Menu) usar os valores coletados

    public boolean isDeuCerto() {
        return deuCerto;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getTituloCategoria() {
        return tituloCategoria;
    }

    public String getDescCategoria() {
        return descCategoria;
    }

    public LocalDateTime getData() {
        return data;
    }

    public Color getCorCategoria() {
        return corCategoria;
    }

    public List<String> getSubtarefas() {
        return new ArrayList<>(subtarefas);
    }

    public boolean isPeriodica() { 
        return isPeriodica; 
    }
    
    public Duration getRecorrencia() { 
        return recorrencia; 
    }
    
    public LocalDateTime getDataFinal() { 
        return dataFinal; 
    }
}
