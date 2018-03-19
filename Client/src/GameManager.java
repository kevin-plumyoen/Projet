/**
 * @author Kevin Plumyoen
 *
 *	Classe chargé de gérer le déroulement du jeu
 */
public class GameManager {
	/**UI fournissant les fonctions pour communiquer avec l'utilisateur*/
	private UI ui;
	/**Connexion à utiliser pour jouer*/
	private Connexion con;
	
	/**
	 * Constructeur de GameManager
	 * @param c : Connexion au serveur
	 * @param u : Interface Utilisateur
	 */
	public GameManager(Connexion c,UI u){
		con=c;
		ui=u;
	}
	
	/**
	 * Affiche le menu du jeu et gère l'option choisi par le joueur
	 */
	public void gameMenu(){
		int i;
		boolean loop=true;
		Game g;
		
		while(loop){
			if((i=ui.gameMenu(con))>0){
				switch(i){
				case 1 :
					if(!ready()){
						System.err.println("Erreur : Le jeu n'a pas démarré");
						break;
					}
					g = new Game(con,ui);
					g.play();
					break;

				case 2 :
					disconnexion();
					loop=false;
					break;

				default :
					System.out.println("Option Inconnue\n");
					break;
				}
			}else{
				System.err.println("Erreur "+i);
			}
		}
		
	}
	
	/**
	 * Fonction qui signale au serveur que le client est prêt à jouer
	 * @return True si le serveur accepte, false s'il refuse
	 */
	public boolean ready(){
		con.write("READY");
		String rep = con.read();
		if(rep.contains("START")) return true;
		else return false;
	}
	
	/**
	 * Préviens le serveur que le client se déconnecte puis ferme la connexion
	 */
	public void disconnexion(){
		con.write("DECON");
		con.closeConnexion();
	}
}
