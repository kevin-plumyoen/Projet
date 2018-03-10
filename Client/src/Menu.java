import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Menu {
	private Game g;
	private Connexion con;
	private Scanner scn;
	
	private String nom;
	private String prenom;
	
	private String in;
	
	private boolean connected = false;
	private boolean loop = true;
	
	public Menu(Connexion c) {
		scn = new Scanner(System.in);
		con = c;
		try{
			con.writer = new PrintWriter(con.sock.getOutputStream());
			con.reader = new BufferedReader (new InputStreamReader (con.sock.getInputStream()));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		while(loop){
			mainMenu();
			if(connected)
				gameMenu();
		}
	}
	
	public Menu(Connexion c, Scanner s){
		scn = s;
		con = c;
		try{
			con.writer = new PrintWriter(con.sock.getOutputStream());
			con.reader = new BufferedReader (new InputStreamReader (con.sock.getInputStream()));
		}catch(IOException e){
				e.printStackTrace();
		}
		
		while(loop){
			//System.out.println("LOOP :"+loop+" "+connected);
			mainMenu();
			if(connected)
				gameMenu();
		}
	}
	
	public void mainMenu(){
		while(!connected){
			System.out.println(" /////////// MENU /////////// ");
			System.out.println("[1] Connexion");
			System.out.println("[2] Inscription");
			System.out.println("[3] Quitter");

			int rep= scn.nextInt();
			scn.nextLine();
			if(rep==1){
				//Send Request
				System.out.print("Nom : ");
				nom = scn.nextLine();
				System.out.print("Prenom : ");
				prenom = scn.nextLine();
				con.writer.println("CON "+nom+" "+prenom);
				con.writer.flush();
				
				//Read Response
				try {
					in = con.reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//Check ACK
				if(in.contains("ACK")){
					connected=true;
					System.out.println("Connexion établie");
				}
				else{
					System.out.println(in);
				}
			}
			
			else if(rep==2){
				//Send Request
				System.out.print("Nom : ");
				nom = scn.nextLine();
				System.out.print("Prenom : ");
				prenom = scn.nextLine();
				con.writer.println("INS "+nom+" "+prenom);
				con.writer.flush();
				
				//Read Response
				try {
					in = con.reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//Check ACK
				if(in.contains("ACK")){
					connected=true;
					System.out.println("Connexion établie");
				}
				else{
					System.out.println(in);
				}
			}
			
			else if(rep==3){
				loop = false;
				con.writer.println("QUIT");
				con.writer.flush();
				return;
			}
			else{
				System.out.println("Option Inconnue : Ressayez");
			}
		}
	}
	
	private void gameMenu(){
		while(true){
			System.out.println(" /////////// JEU /////////// ");
			System.out.println("[1] Lancer le jeu");
			System.out.println("[2] Quitter");
			
			int rep= scn.nextInt();
			if(rep==1){
				//Send READY State
				con.writer.println("READY");
				con.writer.flush();
				
				//Read Response
				try {
					in = con.reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//Check START
				if(in.contains("START")){
					g = new Game(con,scn);
				}
				else{
					System.out.println(in);
				}
			}
			
			else if(rep==2){
				connected = false;
				con.writer.println("DECON");
				con.writer.flush();
				return;
			}
			else if(rep==42){
				System.out.println("Quel score voulez-vous gagner ?");
				int score = scn.nextInt();
				System.out.println("Test1");
				con.writer.println("CHEAT "+score);
				con.writer.flush();
				System.out.println("Test2");
				
				//Read Response
				try {
					in = con.reader.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//Check ACK
				if(in.contains("ACK")){
					System.out.println("Félicitation !!! Vous êtes un tricheur !\nVous êtes maintenant rang "+in.split(" ",2)[1]);
				}
				else{
					System.out.println(in);
				}
			}
			else{
				System.out.println("Option Inconnue : Ressayez");
			}
		}
	}
}