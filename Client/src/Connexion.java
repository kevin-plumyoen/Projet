import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connexion {
	private boolean connected;
	private Socket  sock;
	private PrintWriter writer;
	private BufferedReader reader;
	private String host;
	private int port;
	private String name;
	private String surname;
	
	public Connexion(){
		connected=false;
		sock =null;
		writer = null;
		reader = null;
		host = "localhost";
		port = 60000;
		name = "Default";
		surname = "Default";
	}
	
	public Connexion(String h,String p, String n, String s){
		connected=false;
		sock =null;
		writer = null;
		reader = null;
		host = "localhost";
		port = 60000;
		setConnexion(h,p);
		setPlayer(n,s);
	}
	
	public void setConnexion(String h, String p){
		if(!h.matches(""))
			host = h;
		if(!p.matches(""))
			port = Integer.parseInt(p);
	}
	
	public void setPlayer(String n, String p){
		name = n;
		surname = p;
	}
	
	public boolean initSocket(){
		try {
			sock = new Socket(host,port);
		} catch (UnknownHostException e) {
			System.err.println("Erreur: Host Inconnu");
			return false;
		} catch (IOException e) {
			System.err.println("Erreur: Echec Connexion");
			return false;
		}
			
		connected = initNetworkIO();
		
		return connected;
	}
	
	private boolean initNetworkIO(){
		boolean success=true;
		try {
			writer = new PrintWriter(sock.getOutputStream());
			reader = new BufferedReader (new InputStreamReader (sock.getInputStream()));
		} catch (IOException e) {
			success=false;
			System.err.println("Erreur: Echec Connexion");
		}
		return success;
	}
	
	public String read(){
		String str;
		try {
			str = reader.readLine();
		} catch (IOException e) {
			str="INERR";
		}
		return str;
	}
	
	public void write(String msg){
		writer.println(msg);
		writer.flush();
	}
	
	public boolean closeConnexion(){
		boolean success=true;
		try {
			sock.close();
		} catch (IOException e) {
			System.err.println("Erreur: Impossible de fermer la connexion");
			success=false;
		}
		return success;
	}
	
	public String getHost(){
		return host;
	}
	
	public int getPort(){
		return port;
	}
	
	public String getPlayer(){
		return surname+" "+name;
	}
	
	public boolean isConnected(){
		return connected;
	}
}
