import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;


public class Server implements Runnable{

	//On initialise des valeurs par défaut
	private int port;
	private ServerSocket server = null;
	private boolean isRunning;
	private final int nbPlayerMax=10;
	private int nbPlayer;
	
	private ArrayList<Player> playerList = new ArrayList<Player>();
	
	public Server(int pPort){
		port = pPort;
		try {
			server = new ServerSocket(port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		isRunning=true;
		System.out.println("Server Lancé");
	}
	
	// On lance notre serveur
	public void run() {
		while (isRunning) {
			if(nbPlayer<nbPlayerMax){
				try {
					// On attend une connexion d'un client
					Socket client = server.accept();
					// Une fois reçue, on la traite dans un thread séparé
					System.out.println("Connexion cliente reçue.");
					Thread t = new Thread(new ClientInterface(client,this));
					t.start();
					
					Thread gestion = new Thread(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							boolean run = true;
							System.out.println("gestion");
							do {
								System.out.println("Gestion du serveur :");
								System.out.println("[1] to close the server");
								System.out.println("[2] to reset player list (erase scores to)");
								System.out.println("[3] to reset scores");
								
								Scanner sc = new Scanner(System.in);
								int choix = sc.nextInt();
								switch (choix) {
								case 1 :
									try {
										server.close();
										System.out.println("Server fermé");
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									break;
								case 2 :
									playerList = new ArrayList<Player>();
									break;
								case 3 :
									for(Player p : playerList) {
										p.setLastScore(0);
										p.addToTotalScore(-(p.getTotalScore()));
									}
									break;
								}
							}while(run);
						}
						
					});
					gestion.start();
					
					for(Player p : playerList) {
						System.out.println(p.toString());
					}				
					
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			server.close();
			System.out.println("Server fermé");
		} catch (IOException e) {
			e.printStackTrace();
			server = null;
		}

	}

	public void majRank() {
		// TODO Auto-generated method stub
		for (int i=1;i<playerList.size();i++) {
			if(playerList.get(i).getTotalScore()>playerList.get(i-1).getTotalScore()) {
				for(int j=0;j<playerList.size();j++) {
					if(playerList.get(i).getTotalScore()>playerList.get(i-1).getTotalScore()) {
						playerList.add(j, playerList.get(i));
						playerList.remove(i+1);
					}
				}				
			}
		}
		for (int i=1;i<playerList.size();i++) {
			playerList.get(i).setRank(i+1);
		}
	}

	public void close() {
		isRunning = false;
	}
	
	public void addPlayer(Player p){
		this.playerList.add(p);
	}
	
	public boolean searchUser(String nom, String prenom){
		for(Player p : playerList){
			if(p.getNom()==nom && p.getPrenom()==prenom){
				return true;
			}
		}
		return false;
		
	}
	public boolean searchUser(Player j){
		for(Player p : playerList){
			if(p.getNom()==j.getNom() && p.getPrenom()==j.getPrenom()){
				return true;
			}
		}
		return false;
		
	}
	
	public static void main(String[] args){
		Server s = new Server(60000);
		Thread t = new Thread(s);
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
