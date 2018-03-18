import java.util.Random;

public class MonThread extends Thread{
	
	/** String : Nom de la taches */
	private String nom;
	/** Entier : compteur du nombre d'exécution */
	private int compteur;
	/** parametre permetant de rendre le temps d'attente aléatoire */
	private static Random rand = new Random();
	
	
	/** Constructeur de la classe MonThread 
	 * @param nom String, nom de la tache
	 * */
	public MonThread(String nom){
		this.nom=nom;
		/* Le compteur est initialiser à 0*/
		this.compteur=0;
	}
	
	
	/** Méthode qui exécute 10 fois l'énoncé et se met en pose entre 0 et 1 seconde entre chaque boucle */
	public void run(){
		try{
			//Initialisation de la boucle
			for(int i=0;i<10;i++){
				//execution récurente demander
				System.out.println(this.nom);
				System.out.println(this.compteur);
				//Incrémentation du compteur
				++compteur;
				
				//Pause aléatoire entre 0 et 1 seconde
				try{Thread.sleep(rand.nextInt(1001));}
				catch(Exception e){System.out.println(e);};
			}
			
			//Indicaton de fin de la tache
			System.out.println(this.nom+" stop");
		}catch(Exception e){
			System.out.println(e);
		}
	}
}
