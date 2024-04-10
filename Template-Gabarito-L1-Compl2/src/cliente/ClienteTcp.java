package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClienteTcp {

	Socket s = null;
	DataOutputStream out;
	DataInputStream in;

	public ClienteTcp(String ipServer, int portServer) throws UnknownHostException, IOException {
		this.s = new Socket(ipServer, portServer);
		this.out = new DataOutputStream(s.getOutputStream());
		this.in = new  DataInputStream(s.getInputStream());
	}

	public void sendRequest(String request) throws IOException {
		out.writeUTF(request);
	}

	public String getResponse() throws IOException {
		return in.readUTF();
	}

	public void close() throws IOException {
		s.close();
	}
}