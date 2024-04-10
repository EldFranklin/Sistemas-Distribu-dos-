package servidor;

import calculadora.Calculadora;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorTcpST {
    static ServerSocket welcomeSocket;
    static Socket s;
    static DataInputStream in;
    static DataOutputStream out;
    static Calculadora calculadora = Calculadora.getInstance();

    public static void main(String argv[]) throws Exception {
        welcomeSocket = new ServerSocket(8000);

        while (true) {
            s = welcomeSocket.accept();
            in = new DataInputStream(s.getInputStream());
            out = new DataOutputStream(s.getOutputStream());

            String request = getRequest();
            String response = processRequest(request);
            sendResponse(response);

            in.close();
            out.close();
            s.close();
        }
    }

    public static String getRequest() throws IOException {
        return in.readUTF();
    }

    public static void sendResponse(String response) throws IOException {
        out.writeUTF(response);
    }

    public static String processRequest(String request) {
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
