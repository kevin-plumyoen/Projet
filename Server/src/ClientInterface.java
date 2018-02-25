import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientInterface implements Runnable {
	private Socket sock;
	private PrintWriter writer = null;
	private BufferedReader reader = null;
	private String in;
	private String out;
	
	private Server s;
	private boolean connected = false;
	
	public ClientInterface(Socket client, Server s) {
		sock = client;
		this.s=s;
	}

	@Override
	public void run() {		
		try {
			writer = new PrintWriter(sock.getOutputStream());
			reader = new BufferedReader (new InputStreamReader (sock.getInputStream()));
		   

			//Ã‰tape de connexion ou d'inscription 
			do{
				System.out.println("read");
				in = reader.readLine();
				System.out.println("fin read");
				String[] subIn = in.split(" ", 3);
				if(in.contains("INS")){				
					s.addPlayer(new Player(subIn[1],subIn[2]));
					writer.println("ACK");
					writer.flush();
					this.connected=true;
					System.out.println("Inscription Joueur");
				}
				else if(in.contains("CON")){
					if(s.searchUser(subIn[1],subIn[2])){
						writer.println("ACK");
						writer.flush();
						this.connected=true;
						System.out.println("Connexion Joueur");
					}
					else{
						writer.println("ERR");
						writer.flush();
					}
				}
			}while(!connected);			
			//Mise en attente
			do{
				System.out.println("connecté ^^");
				in = reader.readLine();
				if(in.contains("QUIT")){
					connected=false;
					System.out.println("Fermeture InterfaceClient");
				}
				else if(in.contains("READY")) {
					writer.println("START");
					writer.flush();
					System.out.println("start");
				}
			}while(connected);
			
			this.sock.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
