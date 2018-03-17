
public class Client implements Runnable{

	private Connexion con;
	private UI ui;
	
	public Client(){
		con = new Connexion();
		ui = new UI();
	}
	
	public void launch(){
		ui.hostMenu(con);
		ui.playerIdMenu(con);
	}
	
	public void run(){
		GameManager g = null;
		BotManager b = null;
		int i;
		boolean loop=true;
		launch();
		
		while(loop){
			if(!con.isConnected())
				if(!con.initSocket()) continue;
			
			if((i=ui.mainMenu(con))>0){
				switch(i){
					case 1 :
						g=new GameManager(con,ui);
						g.startGame();
						break;
					case 2 :
						g=new GameManager(con,ui);
						g.startGame();
						break;
					case 5 :
						loop = false;
						break;
					case 6 :
						b = new BotManager(con);
						b.startWin();
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
	
	 public static void main(String[] args) {
		 Client c = new Client();		
		 c.run();
	 } 

}