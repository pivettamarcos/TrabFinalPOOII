package emissor.controle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

//Classe que escuta pela mensagem de retorno da aplicação receptor
public class ThreadRecebimento implements Runnable{
	private Socket socket;
	private JanelaEmissor je;
	private ThreadCronometro threadCronometro;
	
	// construtor da Thread, que recebe o socket de conexão realizada, a janela e a thread do cronometro que conta o tempo de envio
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
	
	// controle de recebimento dos arquivos com a utilização da classe ObjectInputStream e do método readObject, para enviar mensagem de sucesso ao emissor
	public void recebeArquivos() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
			String recebimento = (String)in.readObject();
			
			JOptionPane optionPane = null;
			JDialog dialog = null;
			if(recebimento.equals("Envio feito com sucesso")){
				optionPane = new JOptionPane(recebimento, JOptionPane.INFORMATION_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				dialog = optionPane.createDialog(je, "Sucesso");
			}else if(recebimento.equals("O receptor já possui o número de emissores máximo")){
				optionPane = new JOptionPane(recebimento, JOptionPane.ERROR_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				dialog = optionPane.createDialog(je, "Erro");
			}

			
		    dialog.setModal(false);
		    dialog.setVisible(true);
		    			
			threadCronometro.setRodando(false);
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(je,"Erro ao enviar os arquivos", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}	
}
