package emissor.controle;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

// Classe com l�gica do fluxo de execu��o respons�vel por controlar a adi��o ou remo��o de arquivos do diret�rio selecionado
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
	
	// m�todo de execu��o da Thread
	public void run() {
		while(true){
			verificaAlteracao();
		}
	}
	
	// conta os arquivos, armazenando em cont e comparando com n�mero de arquivos atual
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
				oos.writeObject("Algum arquivo foi adicionado no diret�rio\n"
	    			+ "N� arquivo(s) adicionado(s): "+ (cont - numArquivosAtual)+
	    			"\nN� arquivos atual: "+ cont + "\n Emissor:");
			} catch (IOException e) {
				e.printStackTrace();
			}  
	    	
	    	JOptionPane optionPane = new JOptionPane("Algum arquivo foi adicionado no diret�rio\n"
	    			+ "N� arquivo(s) adicionado(s): "+ (threadCont - numArquivosAtual)+
	    			"\nN� arquivos atual: "+ cont, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		    JDialog dialog = optionPane.createDialog(je, "Sucesso");
		    dialog.setModal(false);
		    dialog.setVisible(true);
	    	
	    	numArquivosAtual = cont;
	    }else if(cont < numArquivosAtual){
	    	try {
				oos.writeObject("Algum arquivo foi removido do diret�rio\n"
	    			+ "N� arquivo(s) removido(s): "+ (numArquivosAtual - cont)+
	    			"\nN� arquivos atual: "+ cont + "\nEmissor:");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
	    	
			JOptionPane optionPane = new JOptionPane("Algum arquivo foi removido do diret�rio\n"
	    			+ "N� arquivo(s) removido(s): "+ (numArquivosAtual - threadCont)+
	    			"\nN� arquivos atual: "+ cont, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		    JDialog dialog = optionPane.createDialog(je, "Sucesso");
		    dialog.setModal(false);
		    dialog.setVisible(true);
	    	
	    	numArquivosAtual = cont;
	    }
	}
	
}
