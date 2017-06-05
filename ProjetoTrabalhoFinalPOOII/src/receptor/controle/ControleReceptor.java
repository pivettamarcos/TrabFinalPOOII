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
import javax.swing.table.DefaultTableModel;

import receptor.visao.JanelaReceptor;

public class ControleReceptor {
	private JanelaReceptor jr;
	private int contEmissores;
	
	public ControleReceptor(JanelaReceptor jr){
		contEmissores = 0;
		this.jr = jr;
	}

	public static void main(String[] args) {
		JanelaReceptor jr = new JanelaReceptor();
		jr.setVisible(true);
		
		ControleReceptor ce = new ControleReceptor(jr);
		while(true)
			ce.estabeleceConexao();
	}
	
	private void estabeleceConexao(){
		try {
			Socket receptor = new Socket("localhost", 1234);
			System.out.println("[Recebendo servidor 1]");
				
			ObjectInputStream in = new ObjectInputStream(receptor.getInputStream());
			LinkedList<String> nomesArquivos = new LinkedList<String>();
			
			try {
				nomesArquivos = (LinkedList<String>)in.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			adicionaNomesATabela(nomesArquivos, contEmissores);
			
			switch(contEmissores){
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
			
			contEmissores++;

			System.out.println("[Recebimento finalizado]");
			receptor.close();
		} catch (IOException e) {
			System.out.println("teste");
		}
	}
	
	private void adicionaNomesATabela(LinkedList<String> nomesArquivos, int contEmissores){
		for (int i = 0; i < nomesArquivos.size(); i++) {
			jr.getModeloTabela().addRow(new Object[]{nomesArquivos.get(i), contEmissores + 1});
	    }
	}
}
