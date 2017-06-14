package emissor.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

public class ControleEmissor extends Thread{
	private JanelaEmissor je;
	private JButton btnEnviarArquivos;
	private LinkedList<String> nomesArquivos;
	private String diretorioImagens;
	private boolean estaRodando;
	private int numArquivosAtual;
	private Thread threadEnvio;
	private Thread threadRecebimento;
	

	
	public ControleEmissor(JanelaEmissor je, JButton btnEnviarArquivos){
		this.je = je;
		this.btnEnviarArquivos = btnEnviarArquivos;
		this.nomesArquivos = new LinkedList<String>();
		this.estaRodando = false;
		inicializaActionListeners();
	}
	
	public void inicializaActionListeners(){
		btnEnviarArquivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				nomesArquivos.removeAll(new LinkedList<String>());
				
				JFileChooser arquivo = new JFileChooser(); 
				arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				arquivo.showOpenDialog(null);  
				diretorioImagens =  arquivo.getSelectedFile().toString();  
				if(diretorioImagens != null){
					estaRodando = true;
					inicializaNumArquivos();
					populaArrayNomeArquivos();
					
					Socket socket = null;
					try {
						socket = new Socket(je.getTfIP().getText(), 2222);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					Thread threadEnvio = new Thread(new ThreadEnvio(socket, nomesArquivos));
					threadEnvio.start();
					
					Thread threadAlteracao = new Thread(new ThreadAlteracao(je, diretorioImagens, numArquivosAtual));
					threadAlteracao.start();
					
					Thread threadRecebimento = new Thread(new ThreadRecebimento(socket));
					threadRecebimento.start();		
				}else{
					JOptionPane.showMessageDialog(null,"Diretório inválido");
				}
			}
		});
	}
	
	public static void main(String[] args) {
		JanelaEmissor je = new JanelaEmissor();
		je.setVisible(true);
		
		ControleEmissor ce = new ControleEmissor(je, je.getBtnEnviarArquivos());
	}

	private void inicializaNumArquivos(){
		int cont = 0;
		File diretorio = new File(diretorioImagens);
		File[] arquivos = diretorio.listFiles();

	    for (int i = 0; i < arquivos.length; i++) {
			  if (arquivos[i].isFile()) {
				  cont++;
			  }
	    }	
	    
	    numArquivosAtual = cont;
	}
	
	private void populaArrayNomeArquivos(){
		File diretorio = new File(diretorioImagens);
		File[] arquivos = diretorio.listFiles();

	    for (int i = 0; i < arquivos.length; i++) {
			  if (arquivos[i].isFile()) {
				  nomesArquivos.add(arquivos[i].getName());
			  }
	    }
	}
}
