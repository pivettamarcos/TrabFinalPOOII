package emissor.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
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
	private JButton btnAlterarDiretório;
	private LinkedList<String> nomesArquivos;
	private String diretorioImagens;
	private boolean estaRodando;
	private int numArquivosAtual;
	

	
	public ControleEmissor(JanelaEmissor je, JButton btnEnviarArquivos, JButton btnAlterarDiretorio){
		this.je = je;
		this.btnEnviarArquivos = btnEnviarArquivos;
		this.btnAlterarDiretório = btnAlterarDiretorio;
		this.nomesArquivos = new LinkedList<String>();
		this.estaRodando = false;
		
		inicializaActionListeners();
	}
	
	public void inicializaActionListeners(){
		btnEnviarArquivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser arquivo = new JFileChooser(); 
				arquivo.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				arquivo.showOpenDialog(null);  
				diretorioImagens =  arquivo.getSelectedFile().toString();  
				if(diretorioImagens != null){
					estaRodando = true;
					inicializaNumArquivos();
					populaArrayNomeArquivos();
					estabeleceConexao();
					
					new Thread() {
						public void run() {
							while(true){
								checaNumArquivos(je);
							}
						}
					}.start();
				}else{
					JOptionPane.showMessageDialog(null,"Diretório inválido");
				}
			}
		});
	}
	
	public static void main(String[] args) {
		JanelaEmissor je = new JanelaEmissor();
		je.setVisible(true);
		
		ControleEmissor ce = new ControleEmissor(je, je.getBtnEnviarArquivos(), je.getBtnAlterarDiretorio());
	}
	
	private void checaNumArquivos(JanelaEmissor je){
		int cont = 0;
		File diretorio = new File(diretorioImagens);
		File[] arquivos = diretorio.listFiles();

	    for (int i = 0; i < arquivos.length; i++) {
			  if (arquivos[i].isFile()) {
				  cont++;
			  }
	    }	
	    if(cont > numArquivosAtual){
	    	JOptionPane.showMessageDialog(je,"Algum arquivo foi adicionado no diretório\n"
	    			+ "Nº arquivo(s) adicionado(s): "+ (cont - numArquivosAtual)+
	    			"\nNº arquivos atual: "+ cont, "Alteração no diretório" , JOptionPane.INFORMATION_MESSAGE);
	    	numArquivosAtual = cont;
	    }else if(cont < numArquivosAtual){
	    	JOptionPane.showMessageDialog(je,"Algum arquivo foi removido do diretório\n"
	    			+ "Nº arquivo(s) reeovido(s): "+ (numArquivosAtual - cont)+
	    			"\nNº arquivos atual: "+ cont, "Alteração no diretório" , JOptionPane.INFORMATION_MESSAGE);
	    	numArquivosAtual = cont;
	    }
	    	
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
	
	private void estabeleceConexao(){
		try {
			ServerSocket server = new ServerSocket(1234);
			System.out.println("[Servindo o nome dos arquivos]");

			Socket cliente = server.accept();
				
			ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
			out.writeObject(nomesArquivos);  

			System.out.println("[Envio encerrado]");
			cliente.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
