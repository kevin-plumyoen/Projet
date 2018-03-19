/**
 * @author Kevin Plumyoen
 *
 *	Classe representant le Client et sa gestion globale
 */
public class Client{
	/**Connexion à utiliser pour jouer*/
	private Connexion con;
	/**UI fournissant les fonctions pour communiquer avec l'utilisateur*/
	private UI ui;
	
	/**
	 * Constructeur par défaut de Client
	 */
	public Client(){
		con = new Connexion();
		ui = new UI();
	}
	
	/**
	 * Permet d'initialisé les informations de connexion en demandant à l'utilisateur
	 */
	public void init(){
		ui.hostMenu(con);
		ui.playerIdMenu(con);
	}
	
	/**
	 * Fonction principale du client
	 */
	public void start(){
		GameManager g = null;
		BotManager b = null;
		int i;
		boolean loop=true;
		init();
		
		while(loop){
			if(!con.isConnected())
				if(!con.initSocket()) continue;
			
			if((i=ui.mainMenu(con))>0){
				switch(i){
					case 1 :
						g=new GameManager(con,ui);
						g.gameMenu();
						break;
					case 2 :
						g=new GameManager(con,ui);
						g.gameMenu();
						break;
					case 5 :
						loop = false;
						break;
					case 6 :
						b = new BotManager(con);
						b.botMenu();
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
	 * Fonction qui crée un client et lance son fonctionnement
	 * @param args
	 */
	 public static void main(String[] args) {
		 Client c = new Client();
		 c.start();
	 } 

}