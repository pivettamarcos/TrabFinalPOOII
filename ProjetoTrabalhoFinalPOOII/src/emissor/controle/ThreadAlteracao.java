package emissor.controle;

import java.io.File;

import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

public class ThreadAlteracao implements Runnable{
	private JanelaEmissor je;
	private String diretorioImagens;
	private int numArquivosAtual;
	
	public ThreadAlteracao(JanelaEmissor je, String diretorioImagens, int numArquivosAtual) {
		super();
		this.je = je;
		this.diretorioImagens = diretorioImagens;
		this.numArquivosAtual = numArquivosAtual;
	}
	
	public void run() {
		int cont = 0;
		File diretorio = new File(diretorioImagens);
		File[] arquivos = diretorio.listFiles();

	    for (int i = 0; i < arquivos.length; i++) {
			  if (arquivos[i].isFile()) {
				  cont++;
			  }
	    }	
	    if(cont > numArquivosAtual){
	    	JOptionPane.showMessageDialog(je,"Algum arquivo foi adicionado no diret�rio\n"
	    			+ "N� arquivo(s) adicionado(s): "+ (cont - numArquivosAtual)+
	    			"\nN� arquivos atual: "+ cont, "Altera��o no diret�rio" , JOptionPane.INFORMATION_MESSAGE);
	    	numArquivosAtual = cont;
	    }else if(cont < numArquivosAtual){
	    	JOptionPane.showMessageDialog(je,"Algum arquivo foi removido do diret�rio\n"
	    			+ "N� arquivo(s) reeovido(s): "+ (numArquivosAtual - cont)+
	    			"\nN� arquivos atual: "+ cont, "Altera��o no diret�rio" , JOptionPane.INFORMATION_MESSAGE);
	    	numArquivosAtual = cont;
	    }
	}

}
