
/** Classe definissant un joueur */
public class Player {
	
	/**Identite du joueur*/
	/**String nom : nom du joueur*/
	private String nom;
	/**String prenom : prÃ©nom du joueur*/
	private String prenom;
	/**int nextId : Id du prochain joueur cree*/
	private static int nextId=0;
	/**int id : id du joueur*/
	private final int id;
	/**server associé*/
	private static Server s;
	
	/**Statistiques du joueur*/
	/**int nbGames : nombre de parties joue*/
	private int nbGames;
	/**int lastScore : score de la derniere partie*/
	private int lastScore;
	/**int totalScore : score cumule de toutes les parties jouees*/
	private int totalScore;
	/**int rank : classement du joueur*/
	private int rank;
	
	/**constructeur de la classe Player
	 * 
	 * @param nom String : nom du joueur
	 * @param prenom String : prenom du joueur
	 */
	public Player(String nom, String prenom, Server s){
		this.s = s;
		this.nom=nom;
		this.prenom=prenom;
		
		id=nextId;
		nextId++;
		
		//Initialisation des statistiques
		nbGames=0;
		lastScore=0;
		totalScore=0;
		//Le classement du joueur est initialiser avec le nombre de joueur inscrit sur le server
		rank=this.s.getPlayerList().size();
	}

	/** Accesseur de l'attribut nbGames*/
	public int getNbGames() {
		return nbGames;
	}
	
	/** Mutateur pour nbGames
	 * 
	 * @param nbGames int
	 */
	public void setNbGames(int nbGames) {
		this.nbGames = nbGames;
	}

	/** Accesseur de l'attribut lastScore*/
	public int getLastScore() {
		return lastScore;
	}

	/**Mutateur pour lastScore
	 * 
	 * @param lastScore int
	 */
	public void setLastScore(int lastScore) {
		this.lastScore = lastScore;
	}

	/** Accesseur de l'attribut totalScore*/
	public int getTotalScore() {
		return totalScore;
	}

	/** Methode d'addition au score total 
	 * 
	 * @param score int
	 */
	public void addToTotalScore(int score) {
		this.totalScore += score;
	}

	/** Accesseur de l'attribut rank*/
	public int getRank() {
		return rank;
	}
	
	/** Mutateur pour rank
	 * 
	 * @param rank int
	 */
	public void setRank(int rank) {
		this.rank = rank;
	}

	/** Accesseur de l'attribut nom*/
	public String getNom() {
		return nom;
	}

	/** Accesseur de l'attribut prenom*/
	public String getPrenom() {
		return prenom;
	}
	
	/** Accesseur de l'attribut id*/
	public int getId() {
		return id;
	}
	
	/** Fonction qui retourne l'identité de joueur*/
	public String toString() {
		String out = ""+this.nom+" "+this.prenom+" id :"+this.id;
		return out;
	}
	
}
