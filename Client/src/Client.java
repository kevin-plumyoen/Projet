import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client implements Runnable{

	private String nom;
	private String prenom;
	
	private Socket sock;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private String in;
	private String out;
	
	private boolean connected = false;
	private byte[] bs;
	
	public Client(){
		try {
			this.sock = new Socket("127.0.0.1",60000);
			writer = new PrintWriter(sock.getOutputStream());
			reader = new BufferedReader (new InputStreamReader (sock.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void connect(){
		System.out.println("Debut connect");
		do{	
			writer.println("INS rakik KK");
			writer.flush();
			System.out.println("Envoi");
			try {
				in = reader.readLine();
				if(in.contains("ACK")){
					connected=true;
					System.out.println("Connexion établie");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}while(!connected);
	}
	
	public void debutJeu() {
		boolean ok = false;
		do {
			System.out.println("commencer? o/n");
			Scanner sc = new Scanner(System.in);
			String rep = null;
			
			rep = sc.nextLine();
			
			if(rep.contains("o")){
				System.out.println("go");
				writer.println("READY");
				writer.flush();
				ok=true;		
			}
			
				
		}while(!ok);
	
		do {
			try {
				in = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(in.contains("START")){
				System.out.println("D�but du jeu");
				this.jeu();
			}
		}while(ok);
	}
	
	private void jeu() {
		// TODO Auto-generated method stub
		boolean play =true;
		do {
			
		}while(play);
	}

	public void quit(){
		writer.println("QUIT");
		writer.flush();
		try {
			sock.close();
			System.out.println("Connexion fermée");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	 public static void main(String[] args) {
		 Client c = new Client();		
		 Thread t = new Thread(c);
		 try {
			t.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 t.start();
	 } 
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		connect();
		debutJeu();
		jeu();
		quit();
		
	}
	
	
	

}
