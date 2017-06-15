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
import java.awt.SystemColor;

// Janela na qual o usuário irá visualizar a listagem dos nomes dos arquivos e o emissor correspondente
public class JanelaReceptor extends JFrame {
	private JPanel contentPane;
	private JTable table;
	private DefaultTableModel modeloTabela;
	// imagens que indicam o recebimento dos dados de cada um dos emissores
	private JLabel lblImgOK1,lblImgOK2,lblImgOK3; 
	private JLabel lblImgClean1,lblImgClean2,lblImgClean3;
	

	public JanelaReceptor() {
		setTitle("Esta\u00E7\u00E3o Receptora");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 528, 503);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.inactiveCaption);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		// barra de rolagem para a listagem dos arquivos
		JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(73, 85, 358, 356);
		contentPane.add(scrollPane);
		// componente gráfico no qual serão listados os arquivos
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
		lblEmissor1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmissor1.setBounds(23, 26, 73, 14);
		contentPane.add(lblEmissor1);
		
		JLabel lblEmissor2 = new JLabel("Emissor 2");
		lblEmissor2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmissor2.setBounds(193, 26, 73, 14);
		contentPane.add(lblEmissor2);
		
		JLabel lblEmissor3 = new JLabel("Emissor 3");
		lblEmissor3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblEmissor3.setBounds(374, 26, 69, 14);
		contentPane.add(lblEmissor3);
		
		lblImgOK1 = new JLabel("");
		lblImgOK1.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/ok1.png")));
		lblImgOK1.setBounds(101, 26, 46, 14);
		contentPane.add(lblImgOK1);
		lblImgOK1.setVisible(false);
		
		lblImgOK2 = new JLabel("");
		lblImgOK2.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/ok1.png")));
		lblImgOK2.setBounds(269, 26, 46, 14);
		contentPane.add(lblImgOK2);
		lblImgOK2.setVisible(false);
		
		lblImgOK3 = new JLabel("");
		lblImgOK3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblImgOK3.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/ok1.png")));
		lblImgOK3.setBounds(421, 25, 46, 14);
		contentPane.add(lblImgOK3);
		lblImgOK3.setVisible(false);
		
		lblImgClean1 = new JLabel("");
		lblImgClean1.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/clean.png")));
		lblImgClean1.setBounds(101, 26, 46, 14);
		contentPane.add(lblImgClean1);
		
		lblImgClean2 = new JLabel("");
		lblImgClean2.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/clean.png")));
		lblImgClean2.setBounds(269, 26, 46, 14);
		contentPane.add(lblImgClean2);
		
		lblImgClean3 = new JLabel("");
		lblImgClean3.setHorizontalAlignment(SwingConstants.RIGHT);
		lblImgClean3.setIcon(new ImageIcon(JanelaReceptor.class.getResource("/icones/clean.png")));
		lblImgClean3.setBounds(421, 26, 46, 14);
		contentPane.add(lblImgClean3);
		
		JLabel lblListaDeArquivos = new JLabel("Lista de Arquivos Recebidos");
		lblListaDeArquivos.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblListaDeArquivos.setHorizontalAlignment(SwingConstants.CENTER);
		lblListaDeArquivos.setBounds(83, 51, 342, 23);
		contentPane.add(lblListaDeArquivos);
	}

	// getters e setters para referenciar componentes no controle
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
