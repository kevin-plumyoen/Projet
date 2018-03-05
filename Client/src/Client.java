import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client{

	private Connexion con;
	private Scanner scn;
	
	public Client(){
		scn = new Scanner(System.in);
		con = new Connexion();
		connexion();
		Menu m = new Menu(con,scn);
	}
	
	private void connexion(){
		String host;
		String port;
		
		System.out.print("Host : ");
		host = scn.nextLine();
		System.out.print("Port : ");
		port = scn.nextLine();
		
		if(!host.matches(""))
			con.host = host;
		if(!port.matches(""))
			con.port = Integer.parseInt(port);
		
		//System.out.println(con.host);
		//System.out.println(con.port);
		
		System.out.print("Trying to connect...");
		
		try {
			con.sock = new Socket(con.host,con.port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Connected");
	}

	 public static void main(String[] args) {
		 Client c = new Client();		
	 } 

}