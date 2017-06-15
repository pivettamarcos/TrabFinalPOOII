package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;

//Classe com lógica do fluxo de execução responsável por receber os arquivos do diretório
public class ThreadRecebimento implements Runnable{
	private Socket socket;
	
	// construtor da Thread
	public ThreadRecebimento(Socket socket) {
		super();
		this.socket = socket;
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
			System.out.println((String)in.readObject());
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
	}
	
}
