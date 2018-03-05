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
	
	public Menu(Connexion c) {
		scn = new Scanner(System.in);
		con = c;
		try{
			con.writer = new PrintWriter(con.sock.getOutputStream());
			con.reader = new BufferedReader (new InputStreamReader (con.sock.getInputStream()));
		}catch(IOException e){
			e.printStackTrace();
		}
		
		while(true){
			mainMenu();
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
		
		while(true){
			mainMenu();
			gameMenu();
		}
	}
	
	public void mainMenu(){
		while(!connected){
			System.out.println(" /////////// MENU /////////// ");
			System.out.println("[1] Connexion");
			System.out.println("[2] Inscription");
			System.out.println("[3] Quitter");
			
			String rep= scn.nextLine();
			if(rep.matches("1")){
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
			
			if(rep.matches("2")){
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
			
			if(rep.matches("3")){
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
			
			String rep= scn.nextLine();
			if(rep.matches("1")){
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
			
			if(rep.matches("2")){
				continue;
			}
			else{
				System.out.println("Option Inconnue : Ressayez");
			}
		}
	}
	
}
