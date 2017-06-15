package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;

//Classe com l�gica do fluxo de execu��o respons�vel por receber os arquivos do diret�rio
public class ThreadRecebimento implements Runnable{
	private Socket socket;
	
	// construtor da Thread
	public ThreadRecebimento(Socket socket) {
		super();
		this.socket = socket;
	}

	// m�todo de execu��o da Thread
	public void run() {
		recebeArquivos();
	}
	
	// recebe os arquivos com a utiliza��o da classe ObjectInputStream e do m�todo readObject
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
