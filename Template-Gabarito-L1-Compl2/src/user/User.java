package user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import cliente.ClienteTcp;;

public class User {

	ClienteTcp clienteTcp = null;

	public User() {
		try {
			clienteTcp = new ClienteTcp("localhost", 7896);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int selecionaOperacao() throws IOException {

		int operacao = 0;
		String resultado = null; 
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String opt = null;
		do {
			opt = stdin.readLine();
		} while (opt.equals("\n") || opt.equals("") || opt.isEmpty());
		operacao = Integer.parseInt(opt);

		switch (operacao) {
		case 1:
			System.out.print("Digite a operação de soma: ");
			clienteTcp.sendRequest(stdin.readLine()); // "4 + 5"
			resultado = clienteTcp.getResponse();
			System.out.println("Resultado: " + resultado);
			break;

		case 2:
			break;

		case 3:
			break;

		case 4:
			break;

		case 0:
			clienteTcp.close();
			break;

		default:
			System.out.println("Operação invalida, tente outra.");
			break;
		}
		return operacao;
	}

	public void printMenu() {
		System.out.println("\nDigite o n# da função que deseja executar: ");
		System.out.println("1 - Soma");
		System.out.println("2 - Subtracão");
		System.out.println("3 - Divisão");
		System.out.println("4 - Multiplicação");
		System.out.println("0 - Sair\n");
	}

	public static void main(String args[]) throws IOException {

		User user = new User();

		int operacao = -1;
		do {
			user.printMenu();
			try {
				operacao = user.selecionaOperacao();
			} catch (IOException ex) {
				System.out.println("Escolha uma das operações pelo número");
			}
		} while (operacao != 0);
	}
}
