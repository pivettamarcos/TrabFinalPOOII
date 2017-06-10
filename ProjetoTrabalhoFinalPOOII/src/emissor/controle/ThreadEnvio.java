package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class ThreadEnvio implements Runnable{
	LinkedList<String> nomesArquivos;
	private Socket socket;
	
	public ThreadEnvio(Socket socket, LinkedList<String> nomesArquivos) {
		super();
		this.socket = socket;
		this.nomesArquivos = nomesArquivos;
	}

	public void run() {
		try {			
			System.out.println("[Servindo o nome dos arquivos]");
				
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(nomesArquivos);  
			System.out.println(nomesArquivos);

			System.out.println("[Envio encerrado]");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
