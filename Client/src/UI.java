import java.util.Scanner;

public class UI {
	
	static final private String allowedLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	
	private Scanner scn;
	private String in;
	
	public UI() {
		scn = new Scanner(System.in);
	}
	
	public UI(Scanner s){
		scn = s;
	}
	
	public void hostMenu(Connexion c){
		String host;
		String port;

		System.out.println("Serveur :");
		System.out.print("Host : ");
		host = scn.nextLine();
		System.out.print("Port : ");
		port = scn.nextLine();

		c.setConnexion(host,port);
	}
	
	public void playerIdMenu(Connexion c){
		String name;
		String surname;
		
		System.out.println("Compte :");
		System.out.print("Nom : ");
		name = scn.nextLine();
		System.out.print("Prenom : ");
		surname = scn.nextLine();
		
		c.setPlayer(name, surname);
	}
	
	public int mainMenu(Connexion c){
		System.out.println(" /////////// MENU /////////// ");
		System.out.println("[1] Connexion");
		System.out.println("[2] Inscription");
		System.out.println("[3] Changer Serveur");
		System.out.println("[4] Changer Compte");
		System.out.println("[5] Quitter");
		System.out.println("----------------------------------");
		System.out.println("[6] Auto-win Bot");
		
		int rep =  scn.nextInt();
		scn.nextLine();
		
		switch(rep){
		case 1:
			c.write("CON "+c.getPlayer());
			
			if((in=c.read()).contains("ACK")){
				System.out.println("Connexion établie");
				return 1;
			}
			else{
				System.err.println("Erreur : "+in);
				return -1;
			}
			
		case 2:
			c.write("INS "+c.getPlayer());
			
			if((in=c.read()).contains("ACK")){
				System.out.println("Connexion établie");
				return 2;
			}
			else{
				System.err.println("Erreur : "+in);
				return -2;
			}

		case 3:
			hostMenu(c);
			return 3;
			
		case 4:
			playerIdMenu(c);
			return 4;
			
		case 5:
			return 5;
			
		case 6:
			return 6;
			
		default:
			return -99;
			
		}
	}
	
	public int gameMenu(Connexion c){
		System.out.println(" /////////// JEU /////////// ");
		System.out.println("[1] Lancer le jeu");
		System.out.println("[2] Quitter");
		
		int rep =  scn.nextInt();
		scn.nextLine();
		
		switch(rep){
			case 1:
				return 1;
				
			case 2:
				return 2;
				
			default:
				return -99;
		}
	}
	
	public String gameInput(){
		String rep = null;
		boolean correct = false;

		correct = false;
		while(!correct){
			System.out.println("Tapez \"PARTIR\" pour quitter le jeu. !!! Perte de 5 points !!!");
			System.out.print("Entrez votre réponse : ");
			rep = scn.nextLine();

			if(rep.length()!=5 && !checkAnswer(rep) && !rep.matches("PARTIR")){
				System.out.println("Format de réponse incorrecte\n");
			}
			else{
				correct = true;
			}
		}

		return rep;
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
				//System.out.println("Wrong Answer");
				return false;
			}
		}
		//System.out.println("Right Answer");
		return true;
	}
}
