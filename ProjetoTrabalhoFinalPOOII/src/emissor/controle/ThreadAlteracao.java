package emissor.controle;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

// Classe com lógica do fluxo de execução responsável por controlar a adição ou remoção de arquivos do diretório selecionado
public class ThreadAlteracao implements Runnable{
	private ObjectOutputStream oos ;
	private JanelaEmissor je;
	private String diretorioImagens;
	private int numArquivosAtual;
	private int cont;
	
	// construtor da Thread
	public ThreadAlteracao(ObjectOutputStream oos, JanelaEmissor je, String diretorioImagens, int numArquivosAtual) {
		super();
		this.oos = oos;
		this.je = je;
		this.diretorioImagens = diretorioImagens;
		this.numArquivosAtual = numArquivosAtual;
	}
	
	// método de execução da Thread
	public void run() {
		while(true){
			verificaAlteracao();
		}
	}
	
	// conta os arquivos, armazenando em cont e comparando com número de arquivos atual
	public void verificaAlteracao() {
		cont = 0;
		File diretorio = new File(diretorioImagens);
		File[] arquivos = diretorio.listFiles();

	    for (int i = 0; i < arquivos.length; i++) {
			  if (arquivos[i].isFile()) {
				  cont++;
			  }
	    }
		
	    try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	    
	    final int threadCont = cont;
	    
	    if(cont > numArquivosAtual){
	    	
	    	try {
				oos.writeObject("Algum arquivo foi adicionado no diretório\n"
	    			+ "Nº arquivo(s) adicionado(s): "+ (cont - numArquivosAtual)+
	    			"\nNº arquivos atual: "+ cont + "\n Emissor:");
			} catch (IOException e) {
				e.printStackTrace();
			}  
	    	
	    	new Thread(new Runnable(){
	    		private int cont;
	    		private int numArquivosAtual;
	    		
	    		public Runnable init(int cont, int numArquivosAtual) {
	    	        this.cont = cont;
	    	        this.numArquivosAtual = numArquivosAtual;
	    	        return this;
	    	    }
	    		
	            public void run(){
	            	JOptionPane.showMessageDialog(je, "Algum arquivo foi adicionado no diretório\n"
	    	    			+ "Nº arquivo(s) adicionado(s): "+ (threadCont - numArquivosAtual)+
	    	    			"\nNº arquivos atual: "+ cont, "Atenção", JOptionPane.WARNING_MESSAGE);
	            }
	        }.init(cont, numArquivosAtual)).start();
	    	
	    	numArquivosAtual = cont;
	    }else if(cont < numArquivosAtual){
	    	try {
				oos.writeObject("Algum arquivo foi removido do diretório\n"
	    			+ "Nº arquivo(s) removido(s): "+ (numArquivosAtual - cont)+
	    			"\nNº arquivos atual: "+ cont + "\nEmissor:");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    	
	    	new Thread(new Runnable(){
	    		private int cont;
	    		private int numArquivosAtual;
	    		
	    		public Runnable init(int cont, int numArquivosAtual) {
	    	        this.cont = cont;
	    	        this.numArquivosAtual = numArquivosAtual;
	    	        return this;
	    	    }
	    		
	            public void run(){
	            	JOptionPane.showMessageDialog(je, "Algum arquivo foi removido do diretório\n"
	    	    			+ "Nº arquivo(s) removido(s): "+ (numArquivosAtual - threadCont)+
	    	    			"\nNº arquivos atual: "+ cont, "Atenção", JOptionPane.WARNING_MESSAGE);
	            }
	        }.init(cont, numArquivosAtual)).start();
	    	
	    	numArquivosAtual = cont;
	    }
	}
	
}
