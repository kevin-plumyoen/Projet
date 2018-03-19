import java.util.ArrayList;
import java.util.Scanner;

public class BotManager {
	
	private Connexion con;
	private ArrayList<Bot> botList;
	static final int maxBot = 10;
	
	public BotManager(Connexion c){
		con=c;
		botList = new ArrayList<Bot>();
	}
	
	public void startWin(){
		Scanner scn = new Scanner(System.in);
		String s = new String();
		
		while(true){
			System.out.println("Nombre de bots : "+botList.size());
			System.out.println("Entrez 'b' pour ajouter un bot");
			System.out.println("Entrez 'a' pour ajouter le maximum de bot");
			System.out.println("Entrez 'm' pour enlever un bot");
			System.out.println("Entrez 'q' pour quitter");
			s=scn.nextLine();
			System.out.println(s);
			
			if(s.matches("b")){
				addBot();
			}
			else if(s.matches("a")){
				addAllBot();
			}
			else if(s.matches("m")){
				removeBot(0);
			}
			else if(s.matches("q")){
				removeAllBot();
				break;
			}
			else{
				System.out.println("Option inconnue");
			}
			
		}
	}
	
	public void addBot(){
		if(botList.size()<maxBot){
			Bot b = new Bot(new Connexion(con));
			botList.add(b);
			b.run();
		}else{
			System.out.println("Liste pleine");
		}
	}
	
	public void removeBot(int id){
		botList.get(id).interrupt();
		botList.remove(id);
	}
	
	public void addAllBot(){
		for(int i=0;i<maxBot;i++){
			addBot();
		}
	}
	
	public void removeAllBot(){
		while(!botList.isEmpty()){
			removeBot(0);
		}
	}
	
}
