import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.tasks.Categoria;
import com.tasks.TaskPadrao;import java.awt.Color;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Postit extends JPanel {

    private JTextPane titleArea;
    private JTextArea dataArea;
    private JTextArea categArea;
    private JCheckBox feitoArea;
    private Task tarefaOriginal;

    public Postit(Task task) {

        this.tarefaOriginal = task;
        String title = task.getTitulo();
        String categ = (task.getCategoria()).getNome();
        String desc = task.getDescricao();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String data = task.getData().format(formatter);
        Boolean feito = task.getFeito();
        Color corFundo = (this.getCategoria()).getCor();

        // Criação do painel principal do post-it
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        if (corFundo != null && this.getCategoria() != null) {
            this.setBackground(corFundo);
        } else {
            this.setBackground(new Color(255, 255, 153));
        }
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
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
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
