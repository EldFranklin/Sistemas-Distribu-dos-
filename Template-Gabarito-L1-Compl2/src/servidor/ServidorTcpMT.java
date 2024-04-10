package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import calculadora.Calculadora;

public class ServidorTcpMT {

    public static void main(String args[]) {
        try {
            int serverPort = 7896;
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while (true) {
                Socket clientSocket = listenSocket.accept();
                Connection connection = new Connection(clientSocket);
                connection.start();
            }
        } catch (IOException e) {
            System.out.println("Listen :" + e.getMessage());
        }
    }
}

class Connection extends Thread {
    Socket clientSocket;
    Calculadora calculadora = Calculadora.getInstance();
    DataInputStream in = null;
    DataOutputStream out = null;

    public Connection(Socket aClientSocket) throws IOException {
        this.clientSocket = aClientSocket;
        this.out = new DataOutputStream(clientSocket.getOutputStream());
        this.in = new DataInputStream(clientSocket.getInputStream());
    }

    public String getRequest() throws IOException {
        return in.readUTF();
    }

    public void sendResponse(String response) throws IOException {
        out.writeUTF(response);
    }

    public void run() {
        try {
            String request = getRequest();
            String response = processRequest(request);
            sendResponse(response);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Error while closing socket: " + e.getMessage());
            }
        }
    }

    private String processRequest(String request) {
        String[] parts = request.split(" ");
        double op1 = Double.parseDouble(parts[0]);
        double op2 = Double.parseDouble(parts[2]);
        double result = 0;
        switch (parts[1]) {
            case "+":
                result = calculadora.add(op1, op2);
                break;
            case "-":
                result = calculadora.sub(op1, op2);
                break;
            case "*":
                result = calculadora.mult(op1, op2);
                break;
            case "/":
                result = calculadora.div(op1, op2);
                break;
            default:
                return "Operação inválida!";
        }
        return Double.toString(result);
    }
}
