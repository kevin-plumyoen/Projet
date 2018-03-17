public class Game {
	
	private UI ui;
	private Connexion con;
	private int nbTries;
	
	public Game(Connexion c,UI u){
		con=c;
		ui=u;
		nbTries=0;
	}
	
	public void play(){
		boolean good=false;
		String rep = "";
		
		while(!good){
			rep = ui.gameInput();
			if(rep.contains("PARTIR")){
				quit();
				return;
			}
			nbTries++;
			con.write("REP "+rep);

			rep=con.read();
			if(rep.contains("GOOD")){
				good=true;
				System.out.println("Vous avez gagné en "+nbTries+" coups !!!");
			}
			else if(rep.contains("BAD")){
				System.out.println(countGoodLetters(rep.split(" ",2)[1])+" "+rep.split(" ",2)[1].replaceAll("%","?"));
			}
			else{
				System.err.println(rep);
			}
		}
		
		displayResult();
	}
	
	public void displayResult(){
		System.out.println("Lecture de vos statistiques...\n");
		String in = con.read();
		if(in.contains("STAT")){
			String in_spl[] =  in.split(" ",5);
			System.out.println("Vos statistiques : \nNombre de Parties :"+in_spl[1]+"\nScore de la derniere partie :"+in_spl[2]+"\nScore total :"+in_spl[3]+"\nRang :"+in_spl[4]);
		}else{
			System.err.println("Erreur : Impossible d'afficher les résultats");
		}
	}
	
	public void quit(){
		con.write("QUIT");
	}
	
	private int countGoodLetters(String ans){
		int gl = 0;
		for(int i=0;i<5;i++){
			if(ans.charAt(i)=='%'){
				gl++;
			}
		}
		return gl;
	}
	
}
