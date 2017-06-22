package emissor.modelo;

import java.net.Socket;

public class Cliente {
	private Socket socket;
	private int id;
	
	public Cliente(Socket socket, int id) {
		super();
		this.socket = socket;
		this.id = id;
	}

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
