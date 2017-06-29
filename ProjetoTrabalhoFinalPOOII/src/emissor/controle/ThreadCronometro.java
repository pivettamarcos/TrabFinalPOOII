package emissor.controle;

import javax.swing.JOptionPane;

import emissor.visao.JanelaEmissor;

// Classe com lógica do fluxo de execução responsável por contar a quantidade de tempo necessário para enviar os arquivos
public class ThreadCronometro implements Runnable {
	private JanelaEmissor je;
	private int segundo, minuto, hora; // contadores de unidades de tempo
	private boolean rodando; // flag para controlar quando a thread parou de executar 
	
	// construtor da classe, que tem a referência da interface para manipular os ícones e labels
	public ThreadCronometro(JanelaEmissor je) {
		super();
		this.je = je;
		segundo = 0; minuto = 0; hora = 0;
		rodando = true;
	}

	// método de execução da Thread
	@Override
	public void run() {
		while(rodando){
			disparaCronometro();
		}
	}

	// lógica de funcionamento do cronômetro
	public void disparaCronometro() {
		segundo++;
		if (segundo == 60) {
            minuto++;
            segundo = 0;
        }
        if (minuto == 60) {
            hora++;
            minuto = 0;
        }
        
        // lógica de formatação do tempo no JLabel
        String formatHr = hora <= 9 ? "0" + hora : hora + "";
        String formatMin = minuto <= 9 ? "0" + minuto : minuto + "";
        String formatSeg = segundo <= 9 ? "0" + segundo : segundo + "";

        this.je.getLblCronometro().setText(formatHr + ":" + formatMin + ":" + formatSeg);
        
        try {
			Thread.sleep(1000); // Thread para um segundo para contar a próxima unidade de tempo
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(je,"Erro ao dar sleep na thread", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	// quando parar de enviar e contar o tempo, mostra um check de que os arquivos foram enviados e o cronômetro parou de rodar
	public void setRodando(boolean rodando) {
		if(!rodando){
			this.je.getIconeEnviado().setVisible(true);
			this.je.getIconeFalha().setVisible(false);
		}
		this.rodando = rodando;
	}  
}