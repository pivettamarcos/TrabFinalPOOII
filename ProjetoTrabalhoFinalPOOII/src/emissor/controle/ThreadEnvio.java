package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

//Classe com lógica do fluxo de execução responsável por enviar os nomes dos arquivos
public class ThreadEnvio implements Runnable{
	LinkedList<String> nomesArquivos;
	private ObjectOutputStream oos;
	
	// construtor da Thread
	public ThreadEnvio(ObjectOutputStream oos, LinkedList<String> nomesArquivos) {
		super();
		this.oos = oos;
		this.nomesArquivos = nomesArquivos;
	}

	// método de execução da Thread
	public void run() {
		enviaArquivos();
	}

	// envia os arquivos para a receptora com a utilização da classe ObjectOutputStream e do método writeObject
	public void enviaArquivos() {
		try {			
			System.out.println("[Servindo o nome dos arquivos]");
			oos.writeObject(nomesArquivos);  
			System.out.println(nomesArquivos);

			System.out.println("[Envio encerrado]");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
