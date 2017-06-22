package emissor.controle;

import emissor.visao.JanelaEmissor;

// Classe com l�gica do fluxo de execu��o respons�vel por contar a quantidade de tempo necess�rio para enviar os arquivos
public class ThreadCronometro implements Runnable {
	private JanelaEmissor je;
	private int segundo, minuto, hora;
	private boolean rodando;
	
	// construtor da classe, que tem a refer�ncia da interface para manipular os �cones e labels
	public ThreadCronometro(JanelaEmissor je) {
		super();
		this.je = je;
		segundo = 0; minuto = 0; hora = 0;
		rodando = true;
	}

	// m�todo de execu��o da Thread
	@Override
	public void run() {
		while(rodando){
			disparaCronometro();
			
		}
	}

	// l�gica de funcionamento do cron�metro
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
        
        String formatHr = hora <= 9 ? "0" + hora : hora + "";
        String formatMin = minuto <= 9 ? "0" + minuto : minuto + "";
        String formatSeg = segundo <= 9 ? "0" + segundo : segundo + "";

        this.je.getLblCronometro().setText(formatHr + ":" + formatMin + ":" + formatSeg);
        
        try {
			Thread.sleep(1000);
			System.out.println(formatSeg);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void setRodando(boolean rodando) {
		if(!rodando){
			this.je.getIconeEnviado().setVisible(true);
			this.je.getIconeFalha().setVisible(false);
		}
		this.rodando = rodando;
	}  
	
	
}
