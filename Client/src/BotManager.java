import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Kevin Plumyoen
 *
 *	Classe permettant de gérer les bots qui jouent sur le serveur
 */
public class BotManager {
	
	/**Connexion qui sera copié par les bots*/
	private Connexion con;
	/**Liste des bots*/
	private ArrayList<Bot> botList;
	/**Nombre maximal de bots*/
	static final int maxBot = 10;
	
	/**
	 * Constructeur de BotManager
	 * @param c : Connexion à copier par les bots
	 */
	public BotManager(Connexion c){
		con=c;
		botList = new ArrayList<Bot>();
	}
	
	/**
	 * Menu permettant de créer et supprimer des bots
	 */
	public void botMenu(){
		Scanner scn = new Scanner(System.in);
		String s = new String();
		
		while(true){
			System.out.println("Nombre de bots : "+botList.size());
			System.out.println("Entrez 'b' pour ajouter un bot");
			System.out.println("Entrez 'a' pour ajouter le maximum de bot");
			System.out.println("Entrez 'm' pour enlever un bot");
			System.out.println("Entrez 'q' pour quitter");
			s=scn.nextLine();
			System.out.println(s);
			
			if(s.matches("b")){
				addBot();
			}
			else if(s.matches("a")){
				addAllBot();
			}
			else if(s.matches("m")){
				removeBot(0);
			}
			else if(s.matches("q")){
				removeAllBot();
				break;
			}
			else{
				System.out.println("Option inconnue");
			}
			
		}
	}
	
	/**
	 * Crée un bot et l'ajoute à la liste si celle-ci n'est pas pleine
	 */
	public void addBot(){
		if(botList.size()<maxBot){
			Bot b = new Bot(new Connexion(con));
			botList.add(b);
			b.run();
		}else{
			System.out.println("Liste pleine");
		}
	}
	
	/**
	 * Supprime le bot demandé
	 * @param id : Numéro du bot dans la liste
	 */
	public void removeBot(int id){
		botList.get(id).interrupt();
		botList.remove(id);
	}
	
	/**
	 * Ajoute le maximum de bot possible
	 */
	public void addAllBot(){
		for(int i=0;i<maxBot;i++){
			addBot();
		}
	}
	
	/**
	 * Supprime tout les bots
	 */
	public void removeAllBot(){
		while(!botList.isEmpty()){
			removeBot(0);
		}
	}
	
}
