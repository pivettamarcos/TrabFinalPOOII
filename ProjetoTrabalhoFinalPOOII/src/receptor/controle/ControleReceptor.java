package receptor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import receptor.visao.JanelaReceptor;

//Classe de controle da recepção de arquivos
public class ControleReceptor {
	private JanelaReceptor jr;
	private int contEmissores;
	private LinkedList<Socket> clientes;
	private ServerSocket serverSocket;
	
	// construtor da classe
	public ControleReceptor(JanelaReceptor jr, ServerSocket serverSocket){
		clientes = new LinkedList<Socket>();
		contEmissores = 0;
		this.serverSocket = serverSocket;
		this.jr = jr;
	}

	// método main do receptor
	public static void main(String[] args) {
		JanelaReceptor jr = new JanelaReceptor();
		jr.setVisible(true);
		jr.setLocationRelativeTo(null);
		
		ControleReceptor ce = null;
		try {
			ce = new ControleReceptor(jr, new ServerSocket(2222));
		} catch (IOException e1) {
			e1.printStackTrace();
		}	
		ce.conecta();
		
	}
	
	// método com Thread interna para realizar a conexão
	private void conecta(){
		if(clientes.size() < 3){
			Thread conecta = new Thread(new Runnable() {
		         public void run() {
		        	Socket cliente = null;
		     		try {
		     			cliente = serverSocket.accept();
		     			clientes.add(cliente);
		     			
		     			conecta();
		     		} catch (IOException e) {
		     			e.printStackTrace();
		     		}
		     		estabeleceConexao(clientes.size(),cliente);
		         }
			});
			conecta.start();
		}
	}
	
	// estabelece conexão, recebendo os nomes dos arquivos
	private void estabeleceConexao(int numEmissor, Socket emissor){
		try {		
			System.out.println("[Conexão do emissor "+ clientes.size() +"]");
			ObjectInputStream in = new ObjectInputStream(emissor.getInputStream());
			LinkedList<String> nomesArquivos = new LinkedList<String>();
			
			try {
				nomesArquivos = (LinkedList<String>)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			System.out.println(nomesArquivos);
			
			adicionaNomesATabela(nomesArquivos, contEmissores);
			
			ativarStatusEmissor();
			
			contEmissores++;
			
			ObjectOutputStream out = new ObjectOutputStream(emissor.getOutputStream());
			out.writeObject("Envio feito com sucesso");  

			System.out.println("[Envio feito com sucesso]");
			
			while(true){
				String msg = (String)in.readObject();
				if(msg.equals("KILL")){
					emissor.close();
					JOptionPane.showMessageDialog(jr, "O emissor "+numEmissor+" foi desconectado", "Atenção", JOptionPane.ERROR_MESSAGE);
					desativarStatusEmissor();
					return;
				}else{
					
					new Thread(new Runnable(){
			    		private String msg;
			    		private int numEmissor;
			    		
			    		public Runnable init(String msg, int numEmissor) {
			    	        this.msg = msg;
			    	        this.numEmissor = numEmissor;
			    	        return this;
			    	    }
			    		
			            public void run(){
			            	JOptionPane.showMessageDialog(jr, msg +numEmissor, "Atenção", JOptionPane.WARNING_MESSAGE);
			            }
			        }.init(msg, numEmissor)).start();
					
					
				}
			}
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(jr, "O emissor "+numEmissor+" foi desconectado", "Atenção", JOptionPane.ERROR_MESSAGE);
			desativarStatusEmissor();
			return;
		} catch (ClassNotFoundException e) {
			JOptionPane.showMessageDialog(jr,"Erro ao receber os arquivos do emissor "+numEmissor, "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	// adiciona os nomes dos arquivos na tabela 
	private void adicionaNomesATabela(LinkedList<String> nomesArquivos, int contEmissores){
		for (int i = 0; i < nomesArquivos.size(); i++) {
			jr.getModeloTabela().addRow(new Object[]{nomesArquivos.get(i), contEmissores + 1});
	    }
	}
	
	// verifica qual emissor enviou os arquivos (1º a enviar é o 1, 2º a enviar é o 2 e 3º a enviar é o 3)
	public void ativarStatusEmissor() {
		switch(clientes.size()){
			case 1:
				jr.getLblImgClean1().setVisible(false);
				jr.getLblImgOK1().setVisible(true);
			break;
			case 2:
				jr.getLblImgClean2().setVisible(false);
				jr.getLblImgOK2().setVisible(true);
			break;
			case 3:
				jr.getLblImgClean3().setVisible(false);
				jr.getLblImgOK3().setVisible(true);
		}
	}
	
	public void desativarStatusEmissor() {
		switch(clientes.size()){
			case 1:
				jr.getLblImgClean1().setVisible(true);
				jr.getLblImgOK1().setVisible(false);
			break;
			case 2:
				jr.getLblImgClean2().setVisible(true);
				jr.getLblImgOK2().setVisible(false);
			break;
			case 3:
				jr.getLblImgClean3().setVisible(true);
				jr.getLblImgOK3().setVisible(false);
		}
	}
}
