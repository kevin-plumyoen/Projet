
public class Bot extends Thread{

	static char charList[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','R','S','T','U','V','W','X','Y','Z'};
	static int nbBot = 0;
	private Connexion con;
	private String name;
	
	public Bot(Connexion c){
		con = new Connexion(c);
		name = "Bot"+nbBot;
		nbBot++;
	}
	
	public void run() {
		
		init();
		while(!isInterrupted()){
			try{
				play();
				Thread.sleep(10);
			}
			catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
		}
		disconnect();
	}
	
	private void play(){
		if(startGame()){
			System.out.println(name+" : Partie Lancée");
		}
		else return;
		
		if(winGame()){
			System.out.println(getStat());
		}
		else{
			quit();
			return;
		}
		
	}
	
	private void init(){
		con.initSocket();
		connect();
	}
	
	private void connect(){
		con.write("CON "+con.getPlayer());
		if(!con.read().contains("ACK")){
			System.out.println("Erreur "+name+" : Impossible de se connecter au compte");
			return;
		}
	}

	private boolean startGame(){
		con.write("READY");
		
		if(!con.read().contains("START")){
			System.out.println("Erreur "+name+" : Impossible de lancer le jeu");
			return false;
		}
		
		return true;
	}
	
	private boolean winGame(){
		boolean win=false;
		
		long startingTime = System.currentTimeMillis();
		
		int charId[] = {0,0,0,0,0};
		char msg[] =  new char[5];
		
		char c_rep=0;;
		String s_rep = new String();
		
		while(!win){
			if(System.currentTimeMillis()-startingTime>60000) return false;
			
			for(int i = 0; i<5;i++){
				msg[i]=charList[charId[i]];
			}
			//System.out.println(msg);
			con.write("REP "+charArrayToString(msg));
			
			s_rep=con.read();
			if(s_rep.contains("GOOD")) win=true;
			else if(s_rep.contains("BAD")){
				s_rep = s_rep.split(" ")[1];
				for(int i = 0;i<5;i++){
					c_rep=s_rep.charAt(i);
					if(charList[charId[i]]!=c_rep){
						charId[i]++;
					}
				}
			}
		}
		
		return true;
	}
	
	private void quit(){
		con.write("QUIT");
	}
	
	private void disconnect(){
		con.write("DECON");
		con.closeConnexion();
	}
	
	private String getStat(){
		String rep[] = con.read().split(" ");
		return name+" : Votre score est maintenant de "+rep[3]+" et vous êtes rang "+rep[4];
	}
	
	private String charArrayToString(char c[]){
		String s = new String();
		
		for(int i = 0; i<c.length;i++){
			s+=c[i];
		}
		
		return s;
	}

}
