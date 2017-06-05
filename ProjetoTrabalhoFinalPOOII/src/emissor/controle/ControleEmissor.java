package emissor.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JButton;

import emissor.visao.JanelaEmissor;

public class ControleEmissor {
	private JButton btnEnviarArquivos;
	private LinkedList<String> nomesArquivos;
	
	public ControleEmissor(JButton btnEnviarArquivos){
		this.btnEnviarArquivos = btnEnviarArquivos;
		this.nomesArquivos = new LinkedList<String>();
		
		inicializaActionListeners();
	}
	
	public void inicializaActionListeners(){
		btnEnviarArquivos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				populaArrayNomeArquivos();
				estabeleceConexao();
			}
		});
	}
	
	public static void main(String[] args) {
		JanelaEmissor je = new JanelaEmissor();
		je.setVisible(true);
		
		ControleEmissor ce = new ControleEmissor(je.getBtnEnviarArquivos());
	}
	
	private void populaArrayNomeArquivos(){
		File diretorio = new File("C:/Users/marco/Desktop/ArquivosEmissoras/Emissora01");
		File[] arquivos = diretorio.listFiles();

	    for (int i = 0; i < arquivos.length; i++) {
			  if (arquivos[i].isFile()) {
				  nomesArquivos.add(arquivos[i].getName());
			  }
	    }
	}
	
	private void estabeleceConexao(){
		try {
			ServerSocket server = new ServerSocket(1234);
			System.out.println("[Servindo o nome dos arquivos]");

			Socket cliente = server.accept();
				
			ObjectOutputStream out = new ObjectOutputStream(cliente.getOutputStream());
			out.writeObject(nomesArquivos);  

			System.out.println("[Envio encerrado]");
			cliente.close();
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
