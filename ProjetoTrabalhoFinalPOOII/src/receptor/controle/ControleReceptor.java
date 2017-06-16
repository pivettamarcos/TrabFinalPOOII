package receptor.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
			
			verificaQualEmissor();
			
			contEmissores++;
			
			ObjectOutputStream out = new ObjectOutputStream(emissor.getOutputStream());
			out.writeObject("Envio feito com sucesso");  

			System.out.println("[Envio feito com sucesso]");
			
			while(true){
				String msg = (String)in.readObject();
				if(msg.equals("KILL"))
					emissor.close();
				else
					JOptionPane.showMessageDialog(jr, msg +numEmissor, "Atenção", JOptionPane.WARNING_MESSAGE);
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// adiciona os nomes dos arquivos na tabela 
	private void adicionaNomesATabela(LinkedList<String> nomesArquivos, int contEmissores){
		for (int i = 0; i < nomesArquivos.size(); i++) {
			jr.getModeloTabela().addRow(new Object[]{nomesArquivos.get(i), contEmissores + 1});
	    }
	}
	
	// verifica qual emissor enviou os arquivos (1º a enviar é o 1, 2º a enviar é o 2 e 3º a enviar é o 3)
	public void verificaQualEmissor() {
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
}
