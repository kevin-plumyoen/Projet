import java.io.IOException;
import java.util.Scanner;

public class Game {
	private Connexion con;
	private Scanner scn;
	
	private String in;
	
	static private String allowedLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	public Game(Connexion c,Scanner s){
		scn = s;
		con = c;
		
		startGame();
	}
	
	private void startGame(){
		String rep = "";
		boolean correct = false;
		boolean good =false;
		int nbTries=0;
		
		
		while(!good){
			scn.nextLine();
			correct = false;
			nbTries++;
			while(!correct){
				System.out.print("Entrez votre réponse : ");
				rep = scn.nextLine();
				
				if(rep.length()!=5 || !checkAnswer(rep)){
					System.out.println("Format de réponse incorrecte");
				}
				else{
					correct = true;
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
			else if(in.contains("BAD")){
				System.out.println(in.split(" ",2)[1]);
				System.out.println("Raté, réessayez");
			}
			else{
				System.out.println(in);
			}
		}
		
		try {
			in = con.reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(in.contains("STAT")){
			String in_spl[] =  in.split(" ",5);
			System.out.println("Vos statistiques : \nNombre de Parties :"+in_spl[1]+"\nScore de la derniere partie :"+in_spl[2]+"\nScore total :"+in_spl[3]+"\nRang :"+in_spl[4]);
		}
		
	}
	
	private boolean checkAnswer(String ans){
		boolean in;
		for(int i=0;i<5;i++){
			in = false;
			for(int j=0;j<allowedLetters.length();j++){
				if(ans.charAt(i)==allowedLetters.charAt(j))
					in=true;
			}
			if(in==false){
				System.out.println("Wrong Answer");
				return false;
			}
		}
		System.out.println("Right Answer");
		return true;
	}
	
}
