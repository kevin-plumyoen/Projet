import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Kevin Plumyoen
 *
 *	Classe représentant un connexion entre le client et un serveur 
 */
public class Connexion {
	/**Booleen permettant de savoir si la connexion est établie*/
	private boolean connected;
	/**Socket permettant la connexion au serveur*/
	private Socket  sock;
	/**PrintWriter permettant d'envoyer des informations*/
	private PrintWriter writer;
	/**BufferedReader permettant de recevoir des informations*/
	private BufferedReader reader;
	/**Nom de domaine ou Adresse IP du serveur*/
	private String host;
	/**Port à utiliser pour communiquer*/
	private int port;
	/**Nom du compte*/
	private String name;
	/**Prenom du compte*/
	private String surname;
	
	/**
	 * Constructeur par défaut de Connexion
	 */
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
	
	/**
	 * Constructeur paramétré de Connexion
	 * 
	 * @param h : Nom de domaine ou Adresse IP du serveur
	 * @param p : Port à utiliser
	 * @param n : Nom du compte
	 * @param s : Prenom du compte
	 */
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
	
	/**
	 * Constructeur de copie de Connexion
	 * Le socket n'est pas copié ni initialisé. Seul les informations de connexion (Serveur et compte) sont retenues.
	 * 
	 * @param c : Connexion à copier
	 */
	public Connexion(Connexion c){
		connected=false;
		sock =null;
		writer = null;
		reader = null;
		host = c.getHost();
		port = c.getPort();
		name = c.getPlayer().split(" ",2)[0];
		surname = c.getPlayer().split(" ",2)[1];
	}
	
	/**
	 * Modifie les informations sur le serveur
	 * 
	 * @param h : Nom de domaine ou Adresse IP du serveur
	 * @param p : Port à utiliser
	 */
	public void setConnexion(String h, String p){
		if(!h.matches(""))
			host = h;
		if(!p.matches(""))
			port = Integer.parseInt(p);
	}
	
	/**
	 * Modifie les informations sur le compte du joueur
	 * 
	 * @param n : Nom
	 * @param p : Prenom
	 */
	public void setPlayer(String n, String p){
		name = n;
		surname = p;
	}
	
	/**
	 * Initalise le socket et lance l'initialisation des communications
	 * 
	 * @return True si tout s'est bien passé, false si il y a eu un problème
	 */
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
	
	/**
	 * Initialise les communication avec le server
	 * 
	 * @return True si tout s'est bien passé, false si il y a eu un problème
	 */
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
	
	/**
	 * Lit la prochaine entrée sur le Reader. Si rien n'est disponible, la fonction attend de recevoir quelque chose.
	 * 
	 * @return String lue
	 */
	public String read(){
		String str;
		try {
			str = reader.readLine();
		} catch (IOException e) {
			str="INERR";
		}
		return str;
	}
	
	/**
	 * Envoie un message au serveur
	 * 
	 * @param msg : String à envoier
	 */
	public void write(String msg){
		writer.println(msg);
		writer.flush();
	}
	
	/**
	 * Ferme le socket
	 * @return True si tout s'est bien passé, false si il y a eu un problème
	 */
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
	
	/**
	 * Accesseur de host
	 * @return : Nom de domaine du serveur
	 */
	public String getHost(){
		return host;
	}
	
	/**
	 * Accesseur de port
	 * @return : Port de connexion
	 */
	public int getPort(){
		return port;
	}
	
	/**
	 * Accesseur des informations du joueurs
	 * @return : String sous forme "NOM PRENOM"
	 */
	public String getPlayer(){
		return surname+" "+name;
	}
	
	/**
	 * Accesseur de connected
	 * @return Etat de connexion
	 */
	public boolean isConnected(){
		return connected;
	}
}
