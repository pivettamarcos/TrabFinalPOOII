package emissor.visao;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import java.awt.SystemColor;
import java.awt.Toolkit;
import javax.swing.JSeparator;

// Janela na qual o usuário irá selecionar os arquivos cujos nomes serão enviados
public class JanelaEmissor extends JFrame {
	private JPanel contentPane;
	private JButton btnEnviarArquivos;
	private JTextField tfIP;
	
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
		
		JLabel lblImgCronometro = new JLabel("cronometro");
		lblImgCronometro.setBounds(22, 235, 30, 29);
		contentPane.add(lblImgCronometro);
		
		try {
			ImageIcon ic = new ImageIcon(ImageIO.read(JanelaEmissor.class.getResource("/icones/timer.png")).getScaledInstance(lblImgCronometro.getWidth(), lblImgCronometro.getHeight(), BufferedImage.SCALE_SMOOTH));
			lblImgCronometro.setIcon(ic);
			
			JLabel lblTitulo = new JLabel("Envio de Arquivos para o Receptor");
			lblTitulo.setForeground(SystemColor.activeCaptionText);
			lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setBounds(61, 11, 330, 29);
			contentPane.add(lblTitulo);
			
			JLabel iconeEnviado = new JLabel("");
			iconeEnviado.setIcon(new ImageIcon(JanelaEmissor.class.getResource("/icones/ok1.png")));
			iconeEnviado.setBounds(149, 237, 46, 23);
			contentPane.add(iconeEnviado);
			
			JLabel iconeFalha = new JLabel("");
			iconeFalha.setIcon(new ImageIcon(JanelaEmissor.class.getResource("/icones/clean.png")));
			iconeFalha.setBounds(149, 237, 46, 23);
			contentPane.add(iconeFalha);
			
			btnEnviarArquivos = new JButton("Enviar Arquivos");
			btnEnviarArquivos.setFont(new Font("Arial", Font.BOLD, 12));
			
			btnEnviarArquivos.setBounds(284, 234, 150, 29);
			contentPane.add(btnEnviarArquivos);
			
			JFormattedTextField ftfCronometro = new JFormattedTextField();
			ftfCronometro.setBounds(60, 240, 79, 20);
			contentPane.add(ftfCronometro);
			MaskFormatter maskData = null;
			try {
				maskData = new MaskFormatter("##:##:####");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			maskData.install(ftfCronometro);
			
			tfIP = new JTextField();
			tfIP.setBounds(187, 126, 113, 20);
			contentPane.add(tfIP);
			tfIP.setColumns(10);
			
			JLabel lblIp = new JLabel("IP:");
			lblIp.setFont(new Font("Arial", Font.PLAIN, 11));
			lblIp.setBounds(150, 129, 14, 14);
			contentPane.add(lblIp);
			
			JSeparator separator = new JSeparator();
			separator.setForeground(SystemColor.scrollbar);
			separator.setBounds(0, 51, 444, 35);
			contentPane.add(separator);
			
			JSeparator separator_1 = new JSeparator();
			separator_1.setForeground(SystemColor.scrollbar);
			separator_1.setBounds(0, 221, 444, 23);
			contentPane.add(separator_1);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// getters e setters para referenciar os componentes gráficos no controle
	public JButton getBtnEnviarArquivos() {
		return btnEnviarArquivos;
	}

	public void setBtnEnviarArquivos(JButton btnEnviarArquivos) {
		this.btnEnviarArquivos = btnEnviarArquivos;
	}

	public JTextField getTfIP() {
		return tfIP;
	}
}