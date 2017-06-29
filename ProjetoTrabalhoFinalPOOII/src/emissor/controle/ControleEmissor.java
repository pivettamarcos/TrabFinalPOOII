package emissor.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
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
	private int numArquivosAtual;
	private Socket socket;
	private ObjectOutputStream oos;
	private ThreadCronometro threadCronometro;
	
	// construtor da classe
	public ControleEmissor(JanelaEmissor je, JButton btnEnviarArquivos){
		this.je = je;
		this.btnEnviarArquivos = btnEnviarArquivos;
		this.nomesArquivos = new LinkedList<String>(); 
		
		inicializaActionListeners();
	}
	
	// registro e controle dos eventos de envio
	public void inicializaActionListeners(){
		btnEnviarArquivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(nomesArquivos != null)
					nomesArquivos.clear();
				abreDiretorio();
				btnEnviarArquivos.setEnabled(false);
				je.getTfIP().setEnabled(false);
			}
		});
		
		je.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if(socket != null)
		    		terminarSocket();
		    }
		});
	}
	
	// m�todo main do emissor
	public static void main(String[] args) {
		JanelaEmissor je = new JanelaEmissor();
		je.setVisible(true);
		je.setLocationRelativeTo(null);
		
		ControleEmissor ce = new ControleEmissor(je, je.getBtnEnviarArquivos());
	}
	
	// m�todo que faz as verifica��es para o envio
	public void abreDiretorio() {
		JFileChooser arquivo = new JFileChooser(); 
		arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); // seleciona apenas diret�rios
		arquivo.showOpenDialog(null);  
		diretorioImagens =  arquivo.getSelectedFile().toString();  
		// verifica se diret�rio foi selecionado para adicionar os arquivos
		if(diretorioImagens != null){
			System.out.println(socket);
			if(socket == null){ 
				if(estabeleConexao()){
					inicializaNumArquivos();
					populaArrayNomeArquivos();
					chamaThreads();
				}else{
					JOptionPane.showMessageDialog(je,"N�o foi poss�vel se conectar ao servidor", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}			
		}else{
			JOptionPane.showMessageDialog(null,"Diret�rio inv�lido");
		}
	}
	
	// conta os arquivos para poder efetuar o controle de adi��o e remo��o
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
	
	// estabelece conex�o para envio, recebendo o IP do usu�rio e a porta definida (2222)
	public boolean estabeleConexao() {
		socket = null;
		try {
			socket = new Socket(je.getTfIP().getText(), 2222);
			oos = new ObjectOutputStream(socket.getOutputStream());
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	// chama as threads necess�rias para a execu��o concorrente do envio, da altera��o e do recebimento dos dados
	public void chamaThreads() {
		threadCronometro = new ThreadCronometro(je);
		Thread tCronometro = new Thread(threadCronometro);
		tCronometro.start();
		
		Thread threadEnvio = new Thread(new ThreadEnvio(oos, je, nomesArquivos));
		threadEnvio.start();
		
		Thread threadAlteracao = new Thread(new ThreadAlteracao(oos, je, diretorioImagens, numArquivosAtual));
		threadAlteracao.start();
		
		Thread threadRecebimento = new Thread(new ThreadRecebimento(socket, je, threadCronometro));
		threadRecebimento.start();	
	}
	
	// finaliza o socket quando o usu�rio sair da aplica��o
	public void terminarSocket(){
		try {
			oos.writeObject("KILL");
			oos.close();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(je,"Erro ao remover o socket", "Erro", JOptionPane.ERROR_MESSAGE);
		}  
	}
}