package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

//Classe com lógica do fluxo de execução responsável por receber os arquivos do diretório
public class ThreadRecebimento implements Runnable{
	private Socket socket;
	private JanelaEmissor je;
	private ThreadCronometro threadCronometro;
	
	// construtor da Thread
	public ThreadRecebimento(Socket socket, JanelaEmissor je, ThreadCronometro threadCronometro) {
		super();
		this.socket = socket;
		this.je = je;
		this.threadCronometro = threadCronometro;
	}

	// método de execução da Thread
	public void run() {
		recebeArquivos();
	}
	
	// recebe os arquivos com a utilização da classe ObjectInputStream e do método readObject
	public void recebeArquivos() {
		ObjectInputStream in = null;

		try {
			in = new ObjectInputStream(socket.getInputStream());
			JOptionPane optionPane = new JOptionPane((String)in.readObject(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		    JDialog dialog = optionPane.createDialog(je, "Sucesso");
		    dialog.setModal(false);
		    dialog.setVisible(true);
		    			
			threadCronometro.setRodando(false);
			System.out.println("parou");

		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(je,"Erro ao enviar os arquivos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
