package receptor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import emissor.modelo.Cliente;
import receptor.visao.JanelaReceptor;

//Classe de controle da recep��o de arquivos
public class ControleReceptor {
	private JanelaReceptor jr;
	private int contEmissores; // contador de emissores
	private LinkedList<Cliente> clientes; // lista de clientes que ir�o se conectar
	private ServerSocket serverSocket; // socket que opera em determinada porta aguardando conex�o
	
	// construtor da classe, recebendo a janela e o serverSocket, que estabelece a porta que o servidor estar� rodando
	public ControleReceptor(JanelaReceptor jr, ServerSocket serverSocket){
		clientes = new LinkedList<Cliente>();
		contEmissores = 0;
		this.serverSocket = serverSocket;
		this.jr = jr;
	}

	// m�todo main do receptor, chamando o construtor do controle
	public static void main(String[] args) {
		JanelaReceptor jr = new JanelaReceptor();
		jr.setVisible(true);
		jr.setLocationRelativeTo(null);
		ControleReceptor ce = null;
		try {
			ce = new ControleReceptor(jr, new ServerSocket(2222)); // porta de conex�o: 2222
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(jr,"Erro ao criar serversocket", "Erro", JOptionPane.ERROR_MESSAGE);
		}	
		ce.conecta();
		
	}
	
	// m�todo com Thread interna para realizar a conex�o com o emissor de dados, considerando o n�mero de clientes m�ximo igual a 3
	private void conecta(){
		if(clientes.size() < 3){
			Thread conecta = new Thread(new Runnable() {
		         public void run() {
		        	Socket socketCliente = null;
		        	Cliente cliente = null;
		     		try {
		     			socketCliente = serverSocket.accept();
		     			// modelo recebe o cliente e adiciona-o a lista
		     			cliente = new Cliente(socketCliente, clientes.size());
		     			clientes.add(cliente);
		     			conecta();
		     		} catch (IOException e) {
		    			JOptionPane.showMessageDialog(jr,"Erro ao aceitar cliente", "Erro", JOptionPane.ERROR_MESSAGE);
		     		}
		     		// ap�s conectar com o cliente, envia e exibe os nomes dos arquivos do diret�rio selecionado no receptor
		     		mandaNomesArquivos(cliente.getId(),cliente.getSocket());
		         }
			});
			conecta.start();
		}else{
			Thread conecta2 = new Thread(new Runnable() {
		         public void run() {
		        	Socket socketCliente = null;
		        	Cliente cliente = null;
		     		try {
		     			socketCliente = serverSocket.accept();
		     			ObjectOutputStream out = new ObjectOutputStream(socketCliente.getOutputStream());
		    			out.writeObject("O receptor j� possui o n�mero de emissores m�ximo");  
		     		} catch (IOException e) {
		    			JOptionPane.showMessageDialog(jr,"Erro ao aceitar cliente", "Erro", JOptionPane.ERROR_MESSAGE);
		     		}
		         }
			});
			conecta2.start();
		}
	}
	
	// estabelece conex�o, recebendo os nomes dos arquivos
	private void mandaNomesArquivos(int idEmissor, Socket emissor){
		int numEmissor = idEmissor + 1;
		try {		
			System.out.println("[Conex�o do emissor "+ clientes.size() +"]");
			// recebimento da lista com os nomes dos arquivos de imagens cujo diret�rio foi selecionado
			ObjectInputStream in = new ObjectInputStream(emissor.getInputStream());
			LinkedList<String> nomesArquivos = new LinkedList<String>();
			
			try {
				nomesArquivos = (LinkedList<String>)in.readObject();
			} catch (ClassNotFoundException e) {
    			JOptionPane.showMessageDialog(jr,"Erro ao ler objeto com nome dos arquivos", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			//System.out.println(nomesArquivos);
			// adiciona os nomes na JTable
			adicionaNomesATabela(nomesArquivos, contEmissores);
			// mostra que determinado emissor est� ativo no momento
			ativarStatusEmissor(idEmissor);
			contEmissores++;
			
			// envia mensagem de envio para o emissor
			ObjectOutputStream out = new ObjectOutputStream(emissor.getOutputStream());
			out.writeObject("Envio feito com sucesso");  

			System.out.println("[Envio feito com sucesso]");
			
			// se o emissor for desconectado, avisa para o servidor
			while(true){
				String msg = (String)in.readObject();
				if(msg.equals("KILL")){ // quando usu�rio fecha a janela manda uma String "KILL"
					emissor.close();
					
					// avisa que emissor foi desconectado
					JOptionPane optionPane = new JOptionPane("O emissor "+numEmissor+" foi desconectado", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				    JDialog dialog = optionPane.createDialog(jr, "Aten��o");
				    dialog.setModal(false);
				    dialog.setVisible(true);
				    
				    // mostra o �cone de emissor desconectado
					desativarStatusEmissor(idEmissor);
					return;
					
				}else{
					JOptionPane optionPane = new JOptionPane(msg +numEmissor, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				    JDialog dialog = optionPane.createDialog(jr, "Aten��o");
				    dialog.setModal(false);
				    dialog.setVisible(true);

				}
			}
			
		} catch (IOException | ClassNotFoundException e) {
			
			try {
				emissor.close();
			} catch (IOException e1) {
    			JOptionPane.showMessageDialog(jr,"Erro ao fechar o socket", "Erro", JOptionPane.ERROR_MESSAGE);
			}
			
			// capturando a exce��o de i/o, o servidor tamb�m � desconectado
			JOptionPane optionPane = new JOptionPane("O emissor "+numEmissor+" foi desconectado", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		    JDialog dialog = optionPane.createDialog(jr, "Erro");
		    dialog.setModal(false);
		    dialog.setVisible(true);
		    desativarStatusEmissor(idEmissor);
			return;
		}
	}
	
	// adiciona os nomes dos arquivos na tabela indicando o emissor correspondente 
	private void adicionaNomesATabela(LinkedList<String> nomesArquivos, int contEmissores){
		for (int i = 0; i < nomesArquivos.size(); i++) {
			jr.getModeloTabela().addRow(new Object[]{nomesArquivos.get(i), contEmissores + 1});
	    }
	}
	
	// verifica qual emissor enviou os arquivos (1� a enviar � o primeiro, 2� a enviar � o segundo e 3� a enviar � o terceiro)
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
	
	// mostra �cone que representa que o emissor foi desconectado
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