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

public class JanelaReceptor extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modeloTabela;
	
	private JLabel lblImgOK1,lblImgOK2,lblImgOK3;
	private JLabel lblImgClean1,lblImgClean2,lblImgClean3;
	

	public JanelaReceptor() {
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
				
			},
			new String[] {
				"Nome do Arquivo", "Emissor"
			}
		));
		modeloTabela = (DefaultTableModel) table.getModel();
		

		scrollPane.setViewportView(table);
		
		JLabel lblEmissor1 = new JLabel("Emissor 1");
		lblEmissor1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEmissor1.setBounds(10, 11, 73, 14);
		contentPane.add(lblEmissor1);
		
		JLabel lblEmissor2 = new JLabel("Emissor 2");
		lblEmissor2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEmissor2.setBounds(181, 11, 73, 14);
		contentPane.add(lblEmissor2);
		
		JLabel lblEmissor3 = new JLabel("Emissor 3");
		lblEmissor3.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblEmissor3.setBounds(350, 11, 69, 14);
		contentPane.add(lblEmissor3);
		
		lblImgOK1 = new JLabel("");
		lblImgOK1.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/ok1.png")));
		lblImgOK1.setBounds(66, 11, 46, 14);
		contentPane.add(lblImgOK1);
		lblImgOK1.setVisible(false);
		
		lblImgOK2 = new JLabel("");
		lblImgOK2.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/ok1.png")));
		lblImgOK2.setBounds(237, 11, 46, 14);
		contentPane.add(lblImgOK2);
		lblImgOK2.setVisible(false);
		
		lblImgOK3 = new JLabel("");
		lblImgOK3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblImgOK3.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/ok1.png")));
		lblImgOK3.setBounds(373, 11, 46, 14);
		contentPane.add(lblImgOK3);
		lblImgOK3.setVisible(false);
		
		lblImgClean1 = new JLabel("");
		lblImgClean1.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/clean.png")));
		lblImgClean1.setBounds(66, 11, 46, 14);
		contentPane.add(lblImgClean1);
		
		lblImgClean2 = new JLabel("");
		lblImgClean2.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/clean.png")));
		lblImgClean2.setBounds(237, 11, 46, 14);
		contentPane.add(lblImgClean2);
		
		lblImgClean3 = new JLabel("");
		lblImgClean3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblImgClean3.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/clean.png")));
		lblImgClean3.setBounds(373, 11, 46, 14);
		contentPane.add(lblImgClean3);
		
		JLabel lblListaDeArquivos = new JLabel("Lista de Arquivos Recebidos");
		lblListaDeArquivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDeArquivos.setBounds(55, 36, 325, 14);
		contentPane.add(lblListaDeArquivos);
	}

	public DefaultTableModel getModeloTabela() {
		return modeloTabela;
	}

	public void setModeloTabela(DefaultTableModel modeloTabela) {
		this.modeloTabela = modeloTabela;
	}

	public JLabel getLblImgOK1() {
		return lblImgOK1;
	}

	public JLabel getLblImgOK2() {
		return lblImgOK2;
	}

	public JLabel getLblImgOK3() {
		return lblImgOK3;
	}

	public JLabel getLblImgClean1() {
		return lblImgClean1;
	}

	public JLabel getLblImgClean2() {
		return lblImgClean2;
	}

	public JLabel getLblImgClean3() {
		return lblImgClean3;
	}
	
	
}
