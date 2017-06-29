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

//Classe de controle da recepção de arquivos
public class ControleReceptor {
	private JanelaReceptor jr;
	private int contEmissores; // contador de emissores
	private LinkedList<Cliente> clientes; // lista de clientes que irão se conectar
	private ServerSocket serverSocket; // socket que opera em determinada porta aguardando conexão
	
	// construtor da classe, recebendo a janela e o serverSocket, que estabelece a porta que o servidor estará rodando
	public ControleReceptor(JanelaReceptor jr, ServerSocket serverSocket){
		clientes = new LinkedList<Cliente>();
		contEmissores = 0;
		this.serverSocket = serverSocket;
		this.jr = jr;
	}

	// método main do receptor, chamando o construtor do controle
	public static void main(String[] args) {
		JanelaReceptor jr = new JanelaReceptor();
		jr.setVisible(true);
		jr.setLocationRelativeTo(null);
		ControleReceptor ce = null;
		try {
			ce = new ControleReceptor(jr, new ServerSocket(2222)); // porta de conexão: 2222
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(jr,"Erro ao criar serversocket", "Erro", JOptionPane.ERROR_MESSAGE);
		}	
		ce.conecta();
		
	}
	
	// método com Thread interna para realizar a conexão com o emissor de dados, considerando o número de clientes máximo igual a 3
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
		     		// após conectar com o cliente, envia e exibe os nomes dos arquivos do diretório selecionado no receptor
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
		    			out.writeObject("O receptor já possui o número de emissores máximo");  
		     		} catch (IOException e) {
		    			JOptionPane.showMessageDialog(jr,"Erro ao aceitar cliente", "Erro", JOptionPane.ERROR_MESSAGE);
		     		}
		         }
			});
			conecta2.start();
		}
	}
	
	// estabelece conexão, recebendo os nomes dos arquivos
	private void mandaNomesArquivos(int idEmissor, Socket emissor){
		int numEmissor = idEmissor + 1;
		try {		
			System.out.println("[Conexão do emissor "+ clientes.size() +"]");
			// recebimento da lista com os nomes dos arquivos de imagens cujo diretório foi selecionado
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
			// mostra que determinado emissor está ativo no momento
			ativarStatusEmissor(idEmissor);
			contEmissores++;
			
			// envia mensagem de envio para o emissor
			ObjectOutputStream out = new ObjectOutputStream(emissor.getOutputStream());
			out.writeObject("Envio feito com sucesso");  

			System.out.println("[Envio feito com sucesso]");
			
			// se o emissor for desconectado, avisa para o servidor
			while(true){
				String msg = (String)in.readObject();
				if(msg.equals("KILL")){ // quando usuário fecha a janela manda uma String "KILL"
					emissor.close();
					
					// avisa que emissor foi desconectado
					JOptionPane optionPane = new JOptionPane("O emissor "+numEmissor+" foi desconectado", JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				    JDialog dialog = optionPane.createDialog(jr, "Atenção");
				    dialog.setModal(false);
				    dialog.setVisible(true);
				    
				    // mostra o ícone de emissor desconectado
					desativarStatusEmissor(idEmissor);
					return;
					
				}else{
					JOptionPane optionPane = new JOptionPane(msg +numEmissor, JOptionPane.WARNING_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				    JDialog dialog = optionPane.createDialog(jr, "Atenção");
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
			
			// capturando a exceção de i/o, o servidor também é desconectado
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
	
	// verifica qual emissor enviou os arquivos (1º a enviar é o primeiro, 2º a enviar é o segundo e 3º a enviar é o terceiro)
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
	
	// mostra ícone que representa que o emissor foi desconectado
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