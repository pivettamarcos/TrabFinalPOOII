package emissor.controle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import javax.swing.JOptionPane;
import emissor.visao.JanelaEmissor;

// Classe com lógica do fluxo de execução responsável por enviar os nomes dos arquivos para o servidor (receptor)
public class ThreadEnvio implements Runnable{
	private ObjectOutputStream oos;
	private JanelaEmissor je;
	private LinkedList<String> nomesArquivos;
	
	// construtor da Thread recebendo a saída, a janela e a lista com os nomes dos arquivos
	public ThreadEnvio(ObjectOutputStream oos, JanelaEmissor je, LinkedList<String> nomesArquivos) {
		super();
		this.oos = oos;
		this.je = je;
		this.nomesArquivos = nomesArquivos;
	}

	// método de execução da Thread
	public void run() {
		enviaArquivos();
	}

	// envia os arquivos para a receptora com a utilização da classe ObjectOutputStream e do método writeObject
	public void enviaArquivos() {
		try {			
			oos.writeObject(nomesArquivos);  
		} catch (IOException e) {
			JOptionPane.showMessageDialog(je,"Erro ao enviar os arquivos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}