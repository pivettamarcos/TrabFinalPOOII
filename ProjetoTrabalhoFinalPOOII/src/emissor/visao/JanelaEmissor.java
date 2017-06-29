package emissor.visao;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.JSeparator;

// Janela na qual o usuário irá selecionar os arquivos cujos nomes serão enviados
public class JanelaEmissor extends JFrame {
	private JPanel contentPane;
	private JButton btnEnviarArquivos;
	private JTextField tfIP;
	private JLabel lblCronometro;
	private JLabel iconeEnviado;
	private JLabel iconeFalha;
	
	// construtor com os componentes e comportamentos da janela de emissão
	public JanelaEmissor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(JanelaEmissor.class.getResource("/icones/servericon.png")));
		setResizable(false);
		setTitle("Esta\u00E7\u00E3o Emissora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.controlHighlight);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// imagem do cronômetro
		JLabel lblImgCronometro = new JLabel("");
		lblImgCronometro.setBounds(22, 235, 30, 29);
		contentPane.add(lblImgCronometro);
		
		try {
			ImageIcon ic = new ImageIcon(ImageIO.read(JanelaEmissor.class.getResource("/icones/timer.png")).getScaledInstance(lblImgCronometro.getWidth(), lblImgCronometro.getHeight(), BufferedImage.SCALE_SMOOTH));
			lblImgCronometro.setIcon(ic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel lblTitulo = new JLabel("Envio de Arquivos para o Receptor");
		lblTitulo.setForeground(SystemColor.activeCaptionText);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(61, 11, 330, 29);
		contentPane.add(lblTitulo);
		
		// ícone que indica que os arquivos foram enviados
		iconeEnviado = new JLabel("");
		iconeEnviado.setIcon(new ImageIcon(JanelaEmissor.class.getResource("/icones/ok1.png")));
		iconeEnviado.setBounds(111, 250, 81, 14);
		contentPane.add(iconeEnviado);
		iconeEnviado.setVisible(false);
		
		// ícone que indica que os arquivos não foram enviados
		iconeFalha = new JLabel("");
		iconeFalha.setIcon(new ImageIcon(JanelaEmissor.class.getResource("/icones/clean.png")));
		iconeFalha.setBounds(111, 250, 81, 14);
		contentPane.add(iconeFalha);
		
		// botão de envio dos arquivos selecionados
		btnEnviarArquivos = new JButton("Enviar Arquivos");
		btnEnviarArquivos.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnEnviarArquivos.setBounds(284, 234, 150, 29);
		contentPane.add(btnEnviarArquivos);
		
		// campo de texto para inserção do IP da máquina que receberá os dados
		tfIP = new JTextField();
		tfIP.setBounds(187, 126, 113, 20);
		contentPane.add(tfIP);
		tfIP.setColumns(10);
		
		JLabel lblIp = new JLabel("IP:");
		lblIp.setFont(new Font("Arial", Font.PLAIN, 11));
		lblIp.setBounds(150, 129, 14, 14);
		contentPane.add(lblIp);
		
		// separadores para design
		JSeparator separator = new JSeparator();
		separator.setForeground(SystemColor.scrollbar);
		separator.setBounds(0, 51, 444, 35);
		contentPane.add(separator);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setForeground(SystemColor.scrollbar);
		separator_1.setBounds(0, 221, 444, 23);
		contentPane.add(separator_1);
		
		// label do cronometro que marca tempo de envio
		lblCronometro = new JLabel("00:00:00");
		lblCronometro.setBounds(57, 250, 77, 14);
		contentPane.add(lblCronometro); 
	}

	// getters e setters para referenciar os componentes gráficos no controle de recepção dos dados
	public JButton getBtnEnviarArquivos() {
		return btnEnviarArquivos;
	}

	public JLabel getIconeEnviado() {
		return iconeEnviado;
	}

	public JLabel getIconeFalha() {
		return iconeFalha;
	}

	public JLabel getLblCronometro() {
		return lblCronometro;
	}

	public void setBtnEnviarArquivos(JButton btnEnviarArquivos) {
		this.btnEnviarArquivos = btnEnviarArquivos;
	}

	public JTextField getTfIP() {
		return tfIP;
	}
}