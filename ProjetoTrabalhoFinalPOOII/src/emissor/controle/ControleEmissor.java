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

// Classe de controle do envio de nomes de arquivos para a receptora
public class ControleEmissor extends Thread{
	private JanelaEmissor je;
	private JButton btnEnviarArquivos;
	private LinkedList<String> nomesArquivos;
	private String diretorioImagens;
	private boolean estaRodando;
	private int numArquivosAtual;
	private Thread threadEnvio;
	private Thread threadRecebimento;
	private Socket socket;
	private ObjectOutputStream oos;
	
	// construtor da classe
	public ControleEmissor(JanelaEmissor je, JButton btnEnviarArquivos){
		this.je = je;
		this.btnEnviarArquivos = btnEnviarArquivos;
		this.nomesArquivos = new LinkedList<String>(); 
		this.estaRodando = false;
		
		inicializaActionListeners();
	}
	
	// registro e controle dos eventos de envio
	public void inicializaActionListeners(){
		btnEnviarArquivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(nomesArquivos != null)
					nomesArquivos.clear();
				abreDiretorio();
				//btnEnviarArquivos.setEnabled(false);
			}
		});
		
		je.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        terminarSocket();
		    }
		});
		
		
	}
	
	// método main do emissor
	public static void main(String[] args) {
		JanelaEmissor je = new JanelaEmissor();
		je.setVisible(true);
		
		ControleEmissor ce = new ControleEmissor(je, je.getBtnEnviarArquivos());
	}
	
	// método que faz as verificações para o envio
	public void abreDiretorio() {
		JFileChooser arquivo = new JFileChooser(); 
		arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // seleciona apenas diretórios
		arquivo.showOpenDialog(null);  
		diretorioImagens =  arquivo.getSelectedFile().toString();  
		// verifica se diretório foi selecionado para adicionar os arquivos
		if(diretorioImagens != null){
			estaRodando = true;
			inicializaNumArquivos();
			populaArrayNomeArquivos();
			estabeleConexao();
			chamaThreads();
			
		}else{
			JOptionPane.showMessageDialog(null,"Diretório inválido");
		}
	}
	
	// conta os arquivos para poder efetuar o controle de adição e remoção
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
	
	// popula array com os nomes dos arquivos a serem enviados
	private void populaArrayNomeArquivos(){
		File diretorio = new File(diretorioImagens);
		File[] arquivos = diretorio.listFiles();

	    for (int i = 0; i < arquivos.length; i++) {
			  if (arquivos[i].isFile()) {
				  nomesArquivos.add(arquivos[i].getName());
			  }
	    }
	}
	
	// estabelece conexão para envio, informando IP e porta
	public void estabeleConexao() {
		socket = null;
		try {
			socket = new Socket(je.getTfIP().getText(), 2222);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			oos = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// chama as threads necessárias para a execução concorrente do envio, da alteração e do recebimento dos dados
	public void chamaThreads() {
		Thread threadEnvio = new Thread(new ThreadEnvio(oos, nomesArquivos));
		threadEnvio.start();
		
		Thread threadAlteracao = new Thread(new ThreadAlteracao(oos, je, diretorioImagens, numArquivosAtual));
		threadAlteracao.start();
		
		Thread threadRecebimento = new Thread(new ThreadRecebimento(socket, je));
		threadRecebimento.start();		
	}
	
	public void terminarSocket(){
		try {
			oos.writeObject("KILL");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
}
