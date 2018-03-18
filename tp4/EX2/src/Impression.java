
public class Impression implements Runnable{
	
	/** nomDoc String : Nom du document */
	private String nomDoc;
	/** nbPages int : Nombre de pages du document */
	private int nbPages;
	
	
	/** Constructueur avec informations minimales
	 * @param nbPages int : nombre de pages
	 * */
	public Impression(int nbPages){
		//Par défaut le nom du document est "Document san nom"
		this.nomDoc = "Document sans nom";
		this.nbPages = nbPages;
	}
	
	/** Constructueur avec toutes les informations  
	 * @param nom String : Nom du document
	 * @param nbPages int : nombre de pages
	 * */
	public Impression(String nom,int nbPages){
		this.nomDoc = nom;
		this.nbPages = nbPages;
	}
	
	
	
	/** Méthode run de l'impression*/
	@Override
	public void run() {
		//synchronisation pour que tous un documents soit imprimer avant qu'une autre impression ne commence
		synchronized(System.out){
			try{
				//impression des n pages du document
				for(int i=1;i<=nbPages;i++){
					System.out.println(nomDoc+" "+i+"/"+nbPages);
				}
			}catch(Exception e){
				System.out.println(e);
			}
		}
		
	}

}
