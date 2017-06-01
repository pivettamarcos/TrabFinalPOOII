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

public class JanelaEmissor extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JanelaEmissor frame = new JanelaEmissor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JanelaEmissor() {
		setResizable(false);
		setTitle("Esta\u00E7\u00E3o Emissora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(160, 231, 29, 29);
		contentPane.add(lblNewLabel);
		
		try {
			ImageIcon ic = new ImageIcon(ImageIO.read(JanelaEmissor.class.getResource("/icones/chronometer.png")).getScaledInstance(lblNewLabel.getWidth(), lblNewLabel.getHeight(), BufferedImage.SCALE_SMOOTH));
			lblNewLabel.setIcon(ic);
			
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
			
			JButton btnNewButton = new JButton("Enviar Arquivos");
			btnNewButton.setFont(new Font("Meiryo", Font.BOLD, 12));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
				}
			});
			btnNewButton.setBounds(164, 123, 136, 23);
			contentPane.add(btnNewButton);
			
			JFormattedTextField formattedTextField = new JFormattedTextField();
			formattedTextField.setBounds(199, 240, 79, 20);
			contentPane.add(formattedTextField);
			MaskFormatter maskData = null;
			try {
				maskData = new MaskFormatter("##:##:####");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			maskData.install(formattedTextField);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
}
