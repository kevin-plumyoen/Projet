import java.util.Random;

public class MonThread extends Thread{
	
	/** String : Nom de la taches */
	private String nom;
	/** Entier : compteur du nombre d'ex�cution */
	private int compteur;
	/** parametre permetant de rendre le temps d'attente al�atoire */
	private static Random rand = new Random();
	
	
	/** Constructeur de la classe MonThread 
	 * @param nom String, nom de la tache
	 * */
	public MonThread(String nom){
		this.nom=nom;
		/* Le compteur est initialiser � 0*/
		this.compteur=0;
	}
	
	
	/** M�thode qui ex�cute 10 fois l'�nonc� et se met en pose entre 0 et 1 seconde entre chaque boucle */
	public void run(){
		try{
			//Initialisation de la boucle
			for(int i=0;i<10;i++){
				//execution r�curente demander
				System.out.println(this.nom);
				System.out.println(this.compteur);
				//Incr�mentation du compteur
				++compteur;
				
				//Pause al�atoire entre 0 et 1 seconde
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
