import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientInterface implements Runnable {
	private Socket sock;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private String in;
	private String out;
	
	private Server s;
	private boolean connected = false;
	private Player joueur;
	
	public ClientInterface(Socket client, Server s) {
		sock = client;
		this.s=s;
		try {
			writer = new PrintWriter(sock.getOutputStream());
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("lien etabli");
	}
	
	public void connect() {
		do{
			try {
				in = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String[] subIn = in.split(" ", 3);
			this.joueur = new Player(subIn[1],subIn[2]);
			if(subIn[0].contains("INS")){					
				if((s.searchUser(this.joueur))){
					writer.println("ERR");
					writer.flush();
				}
				else {
					s.addPlayer(this.joueur);
					writer.println("ACK");
					writer.flush();
					this.connected=true;
					System.out.println("Inscription Joueur");
				}
				
				
			}
			else if(subIn[0].contains("CON")){
				if(s.searchUser(subIn[1],subIn[2])){
					writer.println("ACK");
					writer.flush();
					this.connected=true;
					System.out.println("Connexion Joueur");
				}
				else{
					writer.println("ERR");
					writer.flush();
				}
			}
		}while(!connected);
	}

	public void run() {		
	
			//Ã‰tape de connexion ou d'inscription 
			this.connect();
			//Mise en attente
			
			this.attente();
			
			try {
				this.sock.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	public void attente() {
		// TODO Auto-generated method stub
		do{
			System.out.println("connecté ^^");
			try {
				in = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(in.contains("QUIT")){
				connected=false;
				System.out.println("Fermeture InterfaceClient");
			}
			else if(in.contains("READY")) {
				writer.println("START");
				writer.flush();
				System.out.println("start");
				this.play();
			}
		}while(connected);
	}

	public void play() {
		// TODO Auto-generated method stub
		Thread j = new Thread(new Game(s, sock, joueur));
		j.start();
		
		try {
			j.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.attente();
	}
}
