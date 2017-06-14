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

public class JanelaEmissor extends JFrame {

	private JPanel contentPane;
	private JButton btnEnviarArquivos;
	private JTextField tfIP;
	
	public JanelaEmissor() {
		setResizable(false);
		setTitle("Esta\u00E7\u00E3o Emissora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
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
			lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
			lblTitulo.setBounds(128, 11, 211, 14);
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
			btnEnviarArquivos.setFont(new Font("Meiryo", Font.BOLD, 12));
			
			btnEnviarArquivos.setBounds(161, 98, 136, 23);
			contentPane.add(btnEnviarArquivos);
			
			JFormattedTextField ftfCronometro = new JFormattedTextField();
			ftfCronometro.setBounds(199, 240, 79, 20);
			contentPane.add(ftfCronometro);
			MaskFormatter maskData = null;
			try {
				maskData = new MaskFormatter("##:##:####");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

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
