import java.io.IOException;
import java.util.Scanner;

public class Game {
	private Connexion con;
	private Scanner scn;
	
	private String in;
	
	public Game(Connexion c,Scanner s){
		scn = s;
		con = c;
		
		game();
	}
	
	private void game(){
		String rep = "";
		boolean correct = false;
		boolean good =false;
		int nbTries=0;
		
		
		while(!good){
			correct = false;
			nbTries++;
			while(!correct){
				System.out.print("Entrez votre réponse : ");
				rep = scn.nextLine();
				if(rep.length()!=5){
					System.out.println("Réponse Incorrecte : Il n'y a que 5 lettres");
				}
			}
			con.writer.println("REP "+rep);
			con.writer.flush();
			
			try {
				in = con.reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(in.contains("GOOD")){
				good=true;
				System.out.println("Vous avez gagné en "+nbTries+" coups !!!");
			}
			if(in.contains("BAD")){
				System.out.println("Raté, réessayez");
			}
			else{
				System.out.println(in);
			}
		}
	}
	
}
