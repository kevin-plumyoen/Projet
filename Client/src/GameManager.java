
public class GameManager {
	private UI ui;
	private Connexion con;
	private Game g;
	
	public GameManager(Connexion c,UI u){
		con=c;
		ui=u;
	}
	
	public void startGame(){
		int i;
		boolean loop=true;
		
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
	
	public boolean ready(){
		con.write("READY");
		String rep = con.read();
		if(rep.contains("START")) return true;
		else return false;
	}
	
	public void disconnexion(){
		con.write("DECON");
		con.closeConnexion();
	}
}
