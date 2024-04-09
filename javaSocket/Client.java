import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args) {
        String serverName = "localhost";
        int serverPort = 12000;

        try {
            Socket clientSocket = new Socket(serverName, serverPort);
            System.out.println("Conectado ao servidor " + serverName + ":" + serverPort);

            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Digite a operação no formato 'operacao,valor1,valor2': ");
            String sentence = inFromUser.readLine();

            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(sentence + '\n');

            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String modifiedSentence = inFromServer.readLine();
            System.out.println("Resultado recebido do servidor: " + modifiedSentence);

            clientSocket.close();
        } catch (UnknownHostException e) {
            System.err.println("Endereço do servidor desconhecido: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
        }
    }
}
