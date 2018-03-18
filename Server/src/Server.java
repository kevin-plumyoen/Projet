import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;



/**Classe definissant la base du server*/
public class Server implements Runnable{
	
	/**Nom du server*/	
	private static final String Server = null;
	
	/**Caracteristiques du server*/
	/**Numero du port utiliser*/
	private int port;
	/**ServerSocket qui permet de gerer les connexions*/
	private ServerSocket server = null;
	/**Boolean qui indique si le server est en activite*/
	private boolean isRunning;
	
	/**Liste des joueur incrit sur le server*/
	private ArrayList<Player> playerList = new ArrayList<Player>();

	
	/**constructeur du Sever sans chargement de sauvegarde
	 * @param int pPort : numero du port
	 * */
	public Server(int pPort){
		//Port prend la valeur de pPort
		port = pPort;
		
		//On cree un ServerSocket
		try {
			server = new ServerSocket(port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//On indique que le server est allume
		isRunning=true;
		System.out.println("Server Lance");
		
	}
	
	/**Fonction run pour efectivement lance le server*/
	public void run() {
		//Creation et lancement de la gestion du server
		Gestion gestion = new Gestion(this);
		Thread gere = new Thread(gestion);
		gere.start();
		
		//Boucle de connection en provenance d'un client
		while (isRunning && !this.server.isClosed()) {
			System.out.println(isRunning);
			System.out.println(this.server.isClosed());
			try {
				// On attend une connexion d'un client
				Socket client = server.accept();
				// Une fois reussie, on la traite dans un thread separe
				System.out.println("Connexion cliente reussie.");
				Thread t = new Thread(new ClientInterface(client,this));
				t.start();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		
		try {
			server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Fermeture propre du server
		//this.fermeture(1);

	}
	
	/**Mis à jour du rank des joueurs inscrit*/
	public void majRank() {
		// TODO Auto-generated method stub
		//On parcours la liste de joueur inscrit
		for (int i=1;i<playerList.size();i++) {
			//Si le joueur au rank i est mal place
			if(playerList.get(i).getTotalScore()>playerList.get(i-1).getTotalScore()) {
				//On cherche le bon placement du joueur
				for(int j=0;j<playerList.size();j++) {
					//Des que l'on le trouve on place le joueur au bon endroit et on enleve le doublon à l'ancienne position
					if(playerList.get(i).getTotalScore()>playerList.get(j).getTotalScore()) {
						playerList.add(j, playerList.get(i));
						playerList.remove(i+1);
						break;
					}
				}				
			}
		}
		//on met a jour tous les rank en leur attribuant leur place dans la liste de joueur
		for (int i=0;i<playerList.size();i++) {
			playerList.get(i).setRank(i+1);
		}
	}

	
	/**Methode de fermeture du server*/
	public void fermeture(int cas) {
		switch (cas){
		//fermeture sans sauvegarde
		case 1 :
			isRunning = false;
			Socket soc = new Socket();
			//soc.connect();
			try {
				server.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			System.out.println("Server ferme1");
			break;
		//Fermeture avec sauvegarde ( En cours d'implementation )
		case 2 :
			break;
		//Fermeture par default ( sans sauvegarde )
		default :
			isRunning = false;
			try {
				server.close();
				System.out.println("Server ferme");
			} catch (IOException e) {
				e.printStackTrace();
				server = null;
			}
			break;
		}
		
	}
	
	/**Methode d'ajout d'un nouveau joueur à la liste de joueur inscrit
	 * 
	 * @param p Player : joueur a ajouter
	 */
	public void addPlayer(Player p){
		this.playerList.add(p);
	}
	
	/** Methode permettant de retourer un joueur
	 * 
	 * @param nom String : nom du joueur
	 * @param prenom String : prenom du joueur
	 * @return
	 */
	public Player getPlayer(String nom, String prenom){
		//parcours de la liste de joueur
		for(Player p : playerList){
			//On retourne le joueur quand on le trouve
			if(p.getNom()==nom && p.getPrenom()==prenom){
				return p;
			}
		}
		return (null);
	}
	
	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}
	
	public boolean searchUser(String nom, String prenom){
		System.out.println("debut recherche");
		for(Player p : playerList){
			System.out.println(p.toString());
			System.out.println(p.getNom());
			System.out.println(p.getPrenom());
			if(p.getNom().matches(nom) && p.getPrenom().matches(prenom)){
				System.out.println("trouver !!!");
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
		Server s = new Server(80);
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
