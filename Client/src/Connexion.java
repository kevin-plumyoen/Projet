import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion {
	public Socket sock;
	public PrintWriter writer;
	public BufferedReader reader;
	public String host;
	public int port;
	
	public Connexion(){
		sock = null;
		writer = null;
		reader = null;
		host = "localhost";
		port = 60000;
	}
}
