import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class AppGUI {

    private JLabel labelStatus;

    public JFrame criarJanela() {
        JFrame frame = new JFrame("App de Teste Swing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        
        // FlowLayout organiza os componentes na ordem em que são adicionados
        frame.setLayout(new FlowLayout());

        labelStatus = new JLabel("Status: Aguardando clique...");
        JButton botao = new JButton("Clique Aqui");
        
        // Define o que acontece quando o botão é clicado
        botao.addActionListener(e -> {
            labelStatus.setText("Status: Botão Clicado com Sucesso!");
        });
        
        frame.add(labelStatus);
        frame.add(botao);

        return frame;
    }
} 