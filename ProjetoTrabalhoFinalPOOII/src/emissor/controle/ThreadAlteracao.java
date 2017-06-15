package emissor.controle;

import java.io.File;

import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

// Classe com lógica do fluxo de execução responsável por controlar a adição ou remoção de arquivos do diretório selecionado
public class ThreadAlteracao implements Runnable{
	private JanelaEmissor je;
	private String diretorioImagens;
	private int numArquivosAtual;
	
	// construtor da Thread
	public ThreadAlteracao(JanelaEmissor je, String diretorioImagens, int numArquivosAtual) {
		super();
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
	    			"\nNº arquivos atual: "+ cont, "Alteração no diretório " , JOptionPane.INFORMATION_MESSAGE);
	    	numArquivosAtual = cont;
	    }else if(cont < numArquivosAtual){
	    	JOptionPane.showMessageDialog(je,"Algum arquivo foi removido do diretório\n"
	    			+ "Nº arquivo(s) removido(s): "+ (numArquivosAtual - cont)+
	    			"\nNº arquivos atual: "+ cont, "Alteração no diretório " , JOptionPane.INFORMATION_MESSAGE);
	    	numArquivosAtual = cont;
	    }
	}
	
}
