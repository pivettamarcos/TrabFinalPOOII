package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.LinkedList;

public class ThreadRecebimento implements Runnable{
	private Socket socket;
	
	public ThreadRecebimento(Socket socket) {
		super();
		this.socket = socket;
	}

	public void run() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		LinkedList<String> nomesArquivos = new LinkedList<String>();
		
		try {
			System.out.println((String)in.readObject());
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
