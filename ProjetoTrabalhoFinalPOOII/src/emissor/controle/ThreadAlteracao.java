package emissor.controle;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import emissor.visao.JanelaEmissor;

// Classe com lógica do fluxo de execução responsável por controlar a adição ou remoção de arquivos do diretório selecionado
public class ThreadAlteracao implements Runnable{
	private ObjectOutputStream oos ;
	private JanelaEmissor je;
	private String diretorioImagens;
	private int numArquivosAtual;
	private int cont;
	
	// construtor da Thread recebendo os paraâmetros necessários para controlar a alteração do diretório selecionado
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

		// conta arquivos para comparar valor atual com anterior
	    for (int i = 0; i < arquivos.length; i++) {
			  if (arquivos[i].isFile()) {
				  cont++;
			  }
	    }
		
	    try {
			Thread.sleep(500); // Thread para execução por 0,5 segundos antes de exibir a alteração realizada
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	    
	    final int threadCont = cont;
	    
	    // se o contador for maior que o número atual, houve uma adição de arquivo
	    if(cont > numArquivosAtual){
	    	
	    	try {
	    		// manda mensagem para o receptor
				oos.writeObject("Algum arquivo foi adicionado no diretório\n"
	    			+ "Nº arquivo(s) adicionado(s): "+ (cont - numArquivosAtual)+
	    			"\nNº arquivos atual: "+ cont + "\n Emissor:");
			} catch (IOException e) {
				e.printStackTrace();
			}  
	    	
	    	// mostra mensagem para o usuário que adicionou o arquivo
	    	JOptionPane optionPane = new JOptionPane("Algum arquivo foi adicionado no diretório\n"
	    			+ "Nº arquivo(s) adicionado(s): "+ (threadCont - numArquivosAtual)+
	    			"\nNº arquivos atual: "+ cont, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		    JDialog dialog = optionPane.createDialog(je, "Sucesso");
		    dialog.setModal(false);
		    dialog.setVisible(true);
	    
	    	numArquivosAtual = cont;
	    // se o contador for menor que o número atual, houve uma adição de arquivo
	    }else if(cont < numArquivosAtual){
	    	try {
				oos.writeObject("Algum arquivo foi removido do diretório\n"
	    			+ "Nº arquivo(s) removido(s): "+ (numArquivosAtual - cont)+
	    			"\nNº arquivos atual: "+ cont + "\nEmissor:");
			} catch (IOException e) {
				e.printStackTrace();
			}  
	    	
	    	// mostra mensagem para o usuário que adicionou o arquivo
			JOptionPane optionPane = new JOptionPane("Algum arquivo foi removido do diretório\n"
	    			+ "Nº arquivo(s) removido(s): "+ (numArquivosAtual - threadCont)+
	    			"\nNº arquivos atual: "+ cont, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		    JDialog dialog = optionPane.createDialog(je, "Sucesso");
		    dialog.setModal(false);
		    dialog.setVisible(true);
	    	
	    	numArquivosAtual = cont;
	    }
	}
}