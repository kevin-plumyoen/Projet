import java.util.ArrayList;


public class TestMonThread{
	
	
	/** ArrayList de Tache du type MonThread */
	private ArrayList<MonThread> list;
	
	
	/** Constructeur, par d�fault la list de Taches est vide */
	public TestMonThread(){
		list = new ArrayList<MonThread>();
	}
	
	
	/** M�thode de test de la classe 
	 * @param args String[], argument de la fonction, dans ce cas le nombre de Tache � executer
	 *  */
	public static void main(String[] args){
		
		//Initialisation de la classe TestMonThread
		TestMonThread test = new TestMonThread();
		
		//transformation de l'argument d'entr�s de type String en entier 
		int nbThread = Integer.parseInt(args[0]);
		
		//Cr�tion du nombre de Taches demander
		for(int i=0;i<nbThread;i++){
			test.list.add(new MonThread("Thread"+i));
		}
		
		//D�marage des Taches
		for(int i=0;i<nbThread;i++){
			test.list.get(i).start();
		}
		
		
		//attente de la fin d'execution de toutes les Taches
		for(int i=0;i<nbThread;i++){
			try {
				test.list.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Annonce de la fin d'execution de toutes les taches
		System.out.println("Tout les Threads fini");
		
		
	}
}

