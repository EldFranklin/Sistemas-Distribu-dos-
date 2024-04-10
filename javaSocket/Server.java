import java.io.*;
import java.net.*;

public class Server {

    public static void main(String[] args) {
        final int serverPort = 12000;
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Servidor entrou no modo Listen...");

            int timeout = 15000; // Tempo limite em milissegundos

            while (true) {
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Conectado por " + connectionSocket.getInetAddress());

                BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                String request = inFromClient.readLine();
                String result = processRequest(request);
                
                DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                outToClient.writeBytes(result + '\n');

                connectionSocket.close();

                serverSocket.setSoTimeout(timeout);

                try {
                    serverSocket.accept();
                } catch (SocketTimeoutException e) {
                    System.out.println("Tempo limite atingido. Encerrando o servidor...");
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String processRequest(String request) {
        String[] parts = request.split(",");
        if (parts.length != 3) {
            return "Erro: Requisição inválida";
        }
        String ope = parts[0];
        double ope1 = Double.parseDouble(parts[1]);
        double ope2 = Double.parseDouble(parts[2]);
        switch (ope) {
            case "add":
                return String.valueOf(ope1 + ope2);
            case "sub":
                return String.valueOf(ope1 - ope2);
            case "mul":
                return String.valueOf(ope1 * ope2);
            case "div":
                if (ope2 != 0) {
                    return String.valueOf(ope1 / ope2);
                } else {
                    return "Erro: Divisão por zero";
                }
            default:
                return "Erro: Operação inválida";
        }
    }
}
