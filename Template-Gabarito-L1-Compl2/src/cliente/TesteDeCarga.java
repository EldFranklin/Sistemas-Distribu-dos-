package cliente;

import java.io.IOException;

public class TesteDeCarga {

	static int erroSend = 0;
	static int erroRecev = 0;
	static int erroConnect = 0;
	static double sucesso = 0; 
	
	public long executar(String ipServer, int portServer, int rodadas) {
		
		erroSend = erroRecev = erroConnect = 0;
		sucesso = 0; 
		
		Thread[] threads = new Thread[rodadas];
		
		long tempoInicialST = System.currentTimeMillis();
		
		for (int i = 0; i < rodadas; i++) {
			threads[i] = new Thread(new Runnable() {
				public void run() {
					ClienteTcp clientTest = null;
					try {
						clientTest = new ClienteTcp(ipServer, portServer);
					} catch (IOException e) {
						erroConnect++;
					}
					try {
						if (clientTest != null) {
							clientTest.sendRequest("1 + 1");
						}
					} catch (IOException e) {
						erroSend++;
					}
					try {
						if (clientTest != null) {
							clientTest.getResponse();
							sucesso++;
						}
					} catch (IOException e) {
						erroRecev++;
					}
					if (clientTest != null) {
						clientTest.close();
					}
				}
			});
			threads[i].start();
		}
		for (int i = 0; i < rodadas; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
 				e.printStackTrace();
			}
		}
		
		long tempoFinalST = System.currentTimeMillis() - tempoInicialST;
		
		System.out.println("Erros de Connect: " + erroConnect);
		System.out.println("Erros de Send: " + erroSend);
		System.out.println("Erros de Recv: " + erroRecev);
		System.out.println("Sucesso: " + sucesso );
		double taxaDeSucesso = sucesso/rodadas;
		System.out.println("Taxa de Sucesso: " + taxaDeSucesso*100 +"%" );

		return tempoFinalST;
		
	}

	public static void main(String args[])  {
		
		int rodadas = Integer.parseInt(args[0]);
		
		TesteDeCarga testeDeCarga = new TesteDeCarga();
		
		System.out.println("Testando SingleThread");
		System.out.println("TempoST: " + testeDeCarga.executar("localhost", 8000, rodadas) + "ms");
		
		System.out.println();

		System.out.println("Testando MultiThread");
		System.out.println("TempoMT: " + testeDeCarga.executar("localhost", 7896, rodadas) + "ms");
	}
}
