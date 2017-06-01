package receptor.visao;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

public class TelaReceptor extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaReceptor frame = new TelaReceptor();
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
	public TelaReceptor() {
		setTitle("Esta\u00E7\u00E3o Receptora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(55, 62, 325, 188);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
				{null, null},
			},
			new String[] {
				"Nome do Arquivo", "Emissor"
			}
		));
		scrollPane.setViewportView(table);
		
		JLabel lblEmissor = new JLabel("Emissor 1");
		lblEmissor.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEmissor.setBounds(10, 11, 73, 14);
		contentPane.add(lblEmissor);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TelaReceptor.class.getResource("/icones/ok1.png")));
		lblNewLabel.setBounds(66, 11, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("Emissor 1");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_1.setBounds(181, 11, 73, 14);
		contentPane.add(label_1);
		
		JLabel label_4 = new JLabel("Emissor 1");
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		label_4.setBounds(350, 11, 69, 14);
		contentPane.add(label_4);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(TelaReceptor.class.getResource("/icones/ok1.png")));
		label.setBounds(237, 11, 46, 14);
		contentPane.add(label);
		
		JLabel label_2 = new JLabel("");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setIcon(new ImageIcon(TelaReceptor.class.getResource("/icones/ok1.png")));
		label_2.setBounds(373, 11, 46, 14);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(TelaReceptor.class.getResource("/icones/clean.png")));
		label_3.setBounds(66, 11, 46, 14);
		contentPane.add(label_3);
		
		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(TelaReceptor.class.getResource("/icones/clean.png")));
		label_5.setBounds(237, 11, 46, 14);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setIcon(new ImageIcon(TelaReceptor.class.getResource("/icones/clean.png")));
		label_6.setBounds(373, 11, 46, 14);
		contentPane.add(label_6);
		
		JLabel lblListaDeArquivos = new JLabel("Lista de Arquivos Recebidos");
		lblListaDeArquivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDeArquivos.setBounds(55, 36, 325, 14);
		contentPane.add(lblListaDeArquivos);
	}
}
