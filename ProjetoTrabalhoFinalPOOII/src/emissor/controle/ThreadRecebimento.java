package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

//Classe com lógica do fluxo de execução responsável por receber os arquivos do diretório
public class ThreadRecebimento implements Runnable{
	private Socket socket;
	private JanelaEmissor je;
	
	// construtor da Thread
	public ThreadRecebimento(Socket socket, JanelaEmissor je) {
		super();
		this.socket = socket;
		this.je = je;
	}

	// método de execução da Thread
	public void run() {
		recebeArquivos();
	}
	
	// recebe os arquivos com a utilização da classe ObjectInputStream e do método readObject
	public void recebeArquivos() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		LinkedList<String> nomesArquivos = new LinkedList<String>();
		
		try {
			JOptionPane.showMessageDialog(je,(String)in.readObject());

		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
