package emissor.modelo;

import java.net.Socket;

// Classe de modelo do cliente, que contém o Socket responsável pela conexão e o id do emissor
public class Cliente {
	private Socket socket;
	private int id;
	
	// construtor da classe
	public Cliente(Socket socket, int id) {
		super();
		this.socket = socket;
		this.id = id;
	}
	
	// getters e setters do cliente
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
