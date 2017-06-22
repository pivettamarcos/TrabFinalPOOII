package receptor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.LinkedList;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import emissor.modelo.Cliente;
import receptor.visao.JanelaReceptor;

//Classe de controle da recepção de arquivos
public class ControleReceptor {
	private JanelaReceptor jr;
	private int contEmissores;
	private LinkedList<Cliente> clientes;
	private ServerSocket serverSocket;
	
	// construtor da classe
	public ControleReceptor(JanelaReceptor jr, ServerSocket serverSocket){
		clientes = new LinkedList<Cliente>();
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
		        	Socket socketCliente = null;
		        	Cliente cliente = null;
		     		try {
		     			socketCliente = serverSocket.accept();

		     			cliente = new Cliente(socketCliente, clientes.size());
		     			clientes.add(cliente);
		     			System.out.println("mandole");
		     			conecta();
		     		} catch (IOException e) {
		     			e.printStackTrace();
		     		}
		     		estabeleceConexao(cliente.getId(),cliente.getSocket());
		         }
			});
			conecta.start();
		}
	}
	
	// estabelece conexão, recebendo os nomes dos arquivos
	private void estabeleceConexao(int idEmissor, Socket emissor){
		int numEmissor = idEmissor + 1;
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
			
			ativarStatusEmissor(idEmissor);
			
			contEmissores++;
			
			ObjectOutputStream out = new ObjectOutputStream(emissor.getOutputStream());
			out.writeObject("Envio feito com sucesso");  

			System.out.println("[Envio feito com sucesso]");
			
			while(true){
				String msg = (String)in.readObject();
				if(msg.equals("KILL")){
					emissor.close();
					out.close();
					in.close();
					
					JOptionPane optionPane = new JOptionPane("O emissor "+ numEmissor +" foi desconectado", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				    JDialog dialog = optionPane.createDialog(jr, "Atenção");
				    dialog.setModal(false);
				    dialog.setVisible(true);
				    
					desativarStatusEmissor(idEmissor);
					return;
				}else{
					JOptionPane optionPane = new JOptionPane(msg + numEmissor, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				    JDialog dialog = optionPane.createDialog(jr, "Atenção");
				    dialog.setModal(false);
				    dialog.setVisible(true);
				}
			}
			
		} catch (IOException | ClassNotFoundException e) {
			try {
				emissor.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			JOptionPane optionPane = new JOptionPane("O emissor "+numEmissor+" foi desconectado", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		    JDialog dialog = optionPane.createDialog(jr, "Erro");
		    dialog.setModal(false);
		    dialog.setVisible(true);
		    desativarStatusEmissor(idEmissor);
			return;
		}
	}
	
	// adiciona os nomes dos arquivos na tabela 
	private void adicionaNomesATabela(LinkedList<String> nomesArquivos, int contEmissores){
		for (int i = 0; i < nomesArquivos.size(); i++) {
			jr.getModeloTabela().addRow(new Object[]{nomesArquivos.get(i), contEmissores + 1});
	    }
	}
	
	// verifica qual emissor enviou os arquivos (1º a enviar é o 1, 2º a enviar é o 2 e 3º a enviar é o 3)
	public void ativarStatusEmissor(int numEmissor) {
		switch(numEmissor){
			case 0:
				jr.getLblImgClean1().setVisible(false);
				jr.getLblImgOK1().setVisible(true);
			break;
			case 1:
				jr.getLblImgClean2().setVisible(false);
				jr.getLblImgOK2().setVisible(true);
			break;
			case 2:
				jr.getLblImgClean3().setVisible(false);
				jr.getLblImgOK3().setVisible(true);
		}
	}
	
	public void desativarStatusEmissor(int numEmissor) {
		switch(numEmissor){
			case 0:
				jr.getLblImgClean1().setVisible(true);
				jr.getLblImgOK1().setVisible(false);
			break;
			case 1:
				jr.getLblImgClean2().setVisible(true);
				jr.getLblImgOK2().setVisible(false);
			break;
			case 2:
				jr.getLblImgClean3().setVisible(true);
				jr.getLblImgOK3().setVisible(false);
		}
	}
}
