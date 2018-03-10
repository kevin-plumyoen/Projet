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
	
	private final static int nbJeuMax = 10;
	private static int nbJeu = 0;
	
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
			
			for(Player p : this.s.getPlayerList()) {
			System.out.println(p.toString());
			}	
			
			if(subIn[0].contains("INS")){		
				this.joueur = new Player(subIn[1],subIn[2],this.s);
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
				System.out.println("en co");
				if(s.searchUser(subIn[1],subIn[2])){
					System.out.println("joueur existe");
					writer.println("ACK");
					writer.flush();
					this.joueur = this.s.getPlayer(subIn[1],subIn[2]);
					this.connected=true;
					System.out.println("Connexion Joueur");
				}
				else{
					writer.println("ERR");
					writer.flush();
				}
			}
			else if(subIn[0].contains("QUIT")) {
				System.out.println("quit");
				try {
					this.sock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else {
				writer.println("ERR "+in);
				writer.flush();
				System.out.println(in);
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
			else if(in.contains("DECON")) {
				this.connect();
			}
			else if(in.contains("READY")) {
				writer.println("START");
				writer.flush();
				System.out.println("start");
				if(ClientInterface.nbJeuMax<=ClientInterface.nbJeuMax) {
					this.play();
				}
				else {
					writer.println("ERR "+"MaxGameRunning");
					writer.flush();
				}
				
			}
			else if(in.contains("CHEAT")) {
				this.joueur.addToTotalScore(Integer.parseInt(in.split(" ", 2)[1]));
				this.s.majRank();
			}
			else {
				writer.println("ERR "+in);
				writer.flush();
				System.out.println(in);
			}
		}while(connected);
	}

	public void play() {
		// TODO Auto-generated method stub
		ClientInterface.nbJeu++;
		Thread j = new Thread(new Game(s, sock, joueur));
		j.start();
		
		try {
			j.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ClientInterface.nbJeu--;
		this.attente();
	}
}
