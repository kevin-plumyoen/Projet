/**
 * @author Kevin Plumyoen
 * 
 * Classe de bot capable de jouer et gagner sans intervention de l'utilisateur
 */
public class Bot extends Thread{

	/**Liste de lettres à essayer*/
	static char charList[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','V','W','X','Y','Z'};
	/**Nombre de bot créés. Sert pour déterminer les noms des bots*/
	static int nbBot = 0;
	/**Connexion au serveur*/
	private Connexion con;
	/**Nom du bot*/
	private String name;
	
	/**
	 * Constructeur du bot
	 * @param c : Connexion à copié, contenant les informations du serveur et du compte du joueur
	 */
	public Bot(Connexion c){
		con = new Connexion(c);
		name = "Bot"+nbBot;
		nbBot++;
	}
	
	/**
	 * Fonction executé par le thread
	 */
	public void run() {
		
		init();
		while(!isInterrupted()){
			try{
				play();
				Thread.sleep(10);
			}
			catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
		disconnect();
	}
	
	/**
	 * Joue une partie
	 */
	private void play(){
		if(startGame()){
			System.out.println(name+" : Partie Lancée");
		}
		else return;
		
		if(winGame()){
			System.out.println(getStat());
		}
		else{
			quit();
			return;
		}
		
	}
	
	/**
	 * Initialise la connexion et se connecte au compte du joueur
	 */
	private void init(){
		con.initSocket();
		connect();
	}
	
	/**
	 * Permet de se connecter au compte de l'utilisateur
	 */
	private void connect(){
		con.write("CON "+con.getPlayer());
		if(!con.read().contains("ACK")){
			System.out.println("Erreur "+name+" : Impossible de se connecter au compte");
			return;
		}
	}

	/**
	 * Initialise le jeu
	 * @return True si le jeu est lancé, false sinon
	 */
	private boolean startGame(){
		con.write("READY");
		
		if(!con.read().contains("START")){
			System.out.println("Erreur "+name+" : Impossible de lancer le jeu");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Joue et gagne au jeu, avec un maximum de temps de 1 minute
	 * @return True si la partie est gagnée, false si elle a été abandonnée
	 */
	private boolean winGame(){
		boolean win=false;
		
		long startingTime = System.currentTimeMillis();
		
		int charId[] = {0,0,0,0,0};
		char msg[] =  new char[5];
		
		char c_rep=0;;
		String s_rep = new String();
		
		while(!win){
			if(System.currentTimeMillis()-startingTime>60000) return false;
			
			for(int i = 0; i<5;i++){
				msg[i]=charList[charId[i]];
			}
			//System.out.println(msg);
			con.write("REP "+charArrayToString(msg));
			
			s_rep=con.read();
			if(s_rep.contains("GOOD")) win=true;
			else if(s_rep.contains("BAD")){
				s_rep = s_rep.split(" ")[1];
				for(int i = 0;i<5;i++){
					c_rep=s_rep.charAt(i);
					if(charList[charId[i]]!=c_rep){
						charId[i]++;
					}
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Abandonne la partie
	 */
	private void quit(){
		con.write("QUIT");
	}
	
	/**
	 * Se déconnecte du serveur et ferme le socket
	 */
	private void disconnect(){
		con.write("DECON");
		con.closeConnexion();
	}
	
	/**
	 * Reçoit les stat du joueur envoiés en fin de partie
	 * @return String décrivant les points et le rang du joueur
	 */
	private String getStat(){
		String rep[] = con.read().split(" ");
		return name+" : Votre score est maintenant de "+rep[3]+" et vous êtes rang "+rep[4];
	}
	
	/**
	 * Transforme un tableau de caractère en String
	 * @param c : Tableau de caractère à changer
	 * @return String crée a partir du tableau
	 */
	private String charArrayToString(char c[]){
		String s = new String();
		
		for(int i = 0; i<c.length;i++){
			s+=c[i];
		}
		
		return s;
	}

}
