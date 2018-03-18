import java.util.Scanner;


public class MonThread extends Thread{
	
	/** compteur int : compteur de cycle */
	private int compteur;
	
	/** Constructeur de la classe */
	public MonThread(){
		//Le compteur est initialiser à 0
		compteur=0;
	}
	
	
	/** Méthode run */
	public void run(){
		//Tant que le Thread n'est pas interrompu faire :
		while(!isInterrupted()){
			//afficher le compteur
			System.out.print(compteur);
			//Pause de 1 seconde
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("Interruption");
				Thread.currentThread().interrupt();
			}
			//incrémentation compteur
			compteur++;
		}
	}
	
	/** Méthode de test et interuption possible */
	public static void main(String[] args){
		
		//Initialisation de MonThread
		MonThread t = new MonThread();
		t.start();
		
		//Détection de la saisie d'un caractère
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter a number: ");
		int n = reader.nextInt();
		reader.close();
		//Interuption de la fonction run de t
		t.interrupt();
	}
}
