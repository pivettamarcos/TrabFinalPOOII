package emissor.visao;

import java.awt.EventQueue;

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
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.SystemColor;

// Janela na qual o usuário irá selecionar os arquivos cujos nomes serão enviados
public class JanelaEmissor extends JFrame {
	private JPanel contentPane;
	private JButton btnEnviarArquivos;
	private JTextField tfIP;
	
	// construtor com os componentes e comportamentos da janela de emissão
	public JanelaEmissor() {
		setResizable(false);
		setTitle("Esta\u00E7\u00E3o Emissora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.control);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImgCronometro = new JLabel("cronometro");
		lblImgCronometro.setBounds(160, 231, 29, 29);
		contentPane.add(lblImgCronometro);
		
		try {
			ImageIcon ic = new ImageIcon(ImageIO.read(JanelaEmissor.class.getResource("/icones/chronometer.png")).getScaledInstance(lblImgCronometro.getWidth(), lblImgCronometro.getHeight(), BufferedImage.SCALE_SMOOTH));
			lblImgCronometro.setIcon(ic);
			
			JLabel lblTitulo = new JLabel("Envio de Arquivos para o Receptor");
			lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setBounds(96, 11, 249, 29);
			contentPane.add(lblTitulo);
			
			JLabel iconeEnviado = new JLabel("");
			iconeEnviado.setIcon(new ImageIcon(JanelaEmissor.class.getResource("/icones/ok1.png")));
			iconeEnviado.setBounds(288, 241, 46, 14);
			contentPane.add(iconeEnviado);
			
			JLabel iconeFalha = new JLabel("");
			iconeFalha.setIcon(new ImageIcon(JanelaEmissor.class.getResource("/icones/clean.png")));
			iconeFalha.setBounds(288, 241, 46, 14);
			contentPane.add(iconeFalha);
			
			btnEnviarArquivos = new JButton("Enviar Arquivos");
			btnEnviarArquivos.setFont(new Font("Dialog", Font.BOLD, 12));
			
			btnEnviarArquivos.setBounds(161, 98, 151, 23);
			contentPane.add(btnEnviarArquivos);
			
			JFormattedTextField ftfCronometro = new JFormattedTextField();
			ftfCronometro.setBounds(199, 240, 79, 20);
			contentPane.add(ftfCronometro);
			MaskFormatter maskData = null;
			try {
				maskData = new MaskFormatter("##:##:####");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			maskData.install(ftfCronometro);
			
			tfIP = new JTextField();
			tfIP.setBounds(184, 135, 113, 20);
			contentPane.add(tfIP);
			tfIP.setColumns(10);
			
			JLabel lblIp = new JLabel("IP:");
			lblIp.setBounds(160, 138, 14, 14);
			contentPane.add(lblIp);
			
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