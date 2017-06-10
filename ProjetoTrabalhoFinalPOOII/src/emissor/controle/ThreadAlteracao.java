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

}
