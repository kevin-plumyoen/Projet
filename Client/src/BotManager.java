import java.util.ArrayList;

public class BotManager {
	
	private Connexion con;
	private ArrayList<Bot> botList;
	static final int maxBot = 10;
	
	public BotManager(Connexion c){
		con=c;
		botList = new ArrayList<Bot>();
	}
	
	public void startWin(){
		
	}
	
	public void addBot(){
		Bot b = new Bot();
		botList.add(b);
		b.run();
	}
	
	public void removeBot(int id){
		
	}
	
	public void addAllBot(){
		for(int i=0;i<maxBot;i++){
			addBot();
		}
	}
	
	public void removeAllBot(){

	}
	
}
