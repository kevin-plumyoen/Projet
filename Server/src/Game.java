import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Random;

public class Game implements Runnable{
	
	private String mot = "";
	private boolean run = true;
	
	
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private String in;
	private String out;
	
	private Server s;
	private Socket sock;
	private Player p;
	
	String sec = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
 	
	public Game(Server serv, Socket client, Player j) {
		this.s=serv;
		this.sock = client;
		this.p = j;
		
		
		try {
			writer = new PrintWriter(sock.getOutputStream());
			reader = new BufferedReader (new InputStreamReader (sock.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		
		Random rd = new Random();
		
		for(int i=0; i<5; i++) {
			int l =((rd.nextInt(26))+1);
			mot += sec.charAt(l);
		}
		System.out.println(mot);	
	}
	
	public void run() {
		long debut = System.currentTimeMillis();
		boolean abandon = false;
		do {
			System.out.println("attente d'une reponse");
			try {
				in = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("une reponse 1");
			in.toUpperCase();
			String[] subIn = in.split(" ", 2);
			if(subIn[0].contains("REP")) {				
				System.out.println("une reponse 2");
				if(in.contains(mot)) {
					writer.println("GOOD");
					writer.flush();
					System.out.println("correct");
					this.run = false;
				}
				else {
					String bad = "BAD ";
					for(int i =0;i<subIn[1].length();i++) {
						char rep = subIn[1].charAt(i);
						char bon = mot.charAt(i);
						if(rep==bon) {
							bad += rep;
						}
						else {
							if(mot.contains(""+rep)) {
								bad += "%";
							}
							else {
								bad += "?";
							}
						}
					}
					writer.println(bad);
					writer.flush();
				}
			}
			else {
				writer.println("ERR "+in);
				writer.flush();
				System.out.println(in);
			}
		}while(run);
		long fin = System.currentTimeMillis();
		
		// mis à jour des statistiques du joueur
		//incrémentation du nombre de jeu
		this.p.setNbGames(p.getNbGames()+1);
		//point de la partie et score max
		int temps = (int) (fin-debut)/1000;
		if(abandon) {
			this.p.addToTotalScore(-5);
			this.p.setLastScore(-5);
		}
		else if(temps<60) {
			this.p.addToTotalScore(10);
			this.p.setLastScore(10);
		}
		else if(temps<180) {
			this.p.addToTotalScore(5);
			this.p.setLastScore(5);
		}
		else if(temps<300) {
			this.p.addToTotalScore(2);
			this.p.setLastScore(2);
		}
		else {
			this.p.addToTotalScore(1);
			this.p.setLastScore(1);
		}
		//changement du rang
		s.majRank();
		writer.println("STAT " + p.getNbGames() + " " + p.getLastScore() + " " + p.getTotalScore() + " " + p.getRank());
		writer.flush();
		
	}
	
	public static void main(String[] args){
		Server s = null;
		Socket so = null;
		Player p = new Player("paul", "jacque",s);
		
		Game g = new Game(s,so,p);
		
	}

}
