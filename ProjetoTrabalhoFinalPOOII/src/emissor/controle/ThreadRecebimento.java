package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

//Classe com l�gica do fluxo de execu��o respons�vel por receber os arquivos do diret�rio
public class ThreadRecebimento implements Runnable{
	private Socket socket;
	private JanelaEmissor je;
	private ThreadCronometro threadCronometro;
	
	// construtor da Thread, que recebe o socket de conex�o realizada, a janela e a thread do cronometro que conta o tempo de envio
	public ThreadRecebimento(Socket socket, JanelaEmissor je, ThreadCronometro threadCronometro) {
		super();
		this.socket = socket;
		this.je = je;
		this.threadCronometro = threadCronometro;
	}

	// m�todo de execu��o da Thread
	public void run() {
		recebeArquivos();
	}
	
	// controle de recebimento dos arquivos com a utiliza��o da classe ObjectInputStream e do m�todo readObject, para enviar mensagem de sucesso ao emissor
	public void recebeArquivos() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			JOptionPane optionPane = new JOptionPane((String)in.readObject(), JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		    JDialog dialog = optionPane.createDialog(je, "Sucesso");
		    dialog.setModal(false);
		    dialog.setVisible(true);
		    			
			threadCronometro.setRodando(false);
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(je,"Erro ao enviar os arquivos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}	
}
