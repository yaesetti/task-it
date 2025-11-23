import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;             // Faltava este
import javax.swing.BoxLayout;
import javax.swing.JButton;         // Faltava este
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;     // Faltava este
import javax.swing.JSeparator;      // Faltava este
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class PostitDetail extends JFrame{
    
    public PostitDetail(String title, String data, String categ, Boolean feito, String desc) {
        
        // Criação da janela secundária
        this.setTitle("Detalhes: " + title);
        this.setSize(400, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Painel principal representando o post-it em detalhe
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(255, 255, 153));
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
        // Fonte maior (24) para destaque
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
        JTextArea categArea = new JTextArea("Categoria: " + categ);
        configurarAreaTexto(categArea, "Segoe Print", Font.BOLD, 14);
        topPanel.add(categArea);

        // Adiciona um espaçamento antes da descrição
        topPanel.add(javax.swing.Box.createVerticalStrut(20));

        // Adiciona o Topo ao Norte
        painelPrincipal.add(topPanel, BorderLayout.NORTH);


        // =================================================================================
        // 2. CENTRO (A Descrição)
        // =================================================================================
        
        // Labelzinha para indicar o campo (opcional, mas fica bom)
        JTextArea lblDesc = new JTextArea("Descrição:");
        configurarAreaTexto(lblDesc, "Segoe Print", Font.BOLD, 14);
        lblDesc.setForeground(Color.DARK_GRAY);
        // Precisamos adicionar o label no topPanel ou criar um painel wrapper pro centro.
        // Vamos adicionar no final do topPanel para simplificar:
        topPanel.add(lblDesc);


        // Área de Texto da Descrição
        JTextArea areaDescricao = new JTextArea(desc);
        areaDescricao.setFont(new Font("Segoe Print", Font.PLAIN, 16));
        areaDescricao.setLineWrap(true);
        areaDescricao.setWrapStyleWord(true);
        areaDescricao.setOpaque(false);
        areaDescricao.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        areaDescricao.setEditable(false);

        // ScrollPane transparente (sem borda feia)
        JScrollPane scroll = new JScrollPane(areaDescricao);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(220, 220, 100))); // Borda sutil
        
        painelPrincipal.add(scroll, BorderLayout.CENTER);


        // =================================================================================
        // 3. BASE (Status e Botão)
        // =================================================================================
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JCheckBox chkFeito = new JCheckBox("Feito", feito);
        chkFeito.setOpaque(false);
        chkFeito.setFont(new Font("Segoe Print", Font.BOLD, 14));
        
        bottomPanel.add(chkFeito);

        painelPrincipal.add(bottomPanel, BorderLayout.SOUTH);

        // Finaliza
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

}


