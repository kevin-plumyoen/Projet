import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestion implements Runnable{
	
	private Server serv;
	
	public Gestion(Server s) {
		serv = s;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		boolean run = true;
		System.out.println("gestion");
		do {
			System.out.println("Gestion du serveur :");
			System.out.println("[1] to close the server");
			System.out.println("[2] to reset player list (erase scores to)");
			System.out.println("[3] to reset scores");
			
			Scanner sc = new Scanner(System.in);
			int choix = sc.nextInt();
			switch (choix) {
			case 1 :
				serv.fermeture(1);
				System.out.println("Server fermé");
				run =false;
				break;
			case 2 :
				serv.setPlayerList(new ArrayList<Player>());
				break;
			case 3 :
				for(Player p : serv.getPlayerList()) {
					p.setLastScore(0);
					p.addToTotalScore(-(p.getTotalScore()));
				}
				break;
			}
		}while(run);
	}

}
