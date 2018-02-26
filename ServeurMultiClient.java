import java.io.IOException;
import java.net.UnknownHostException;

import java.net.Socket;
import java.net.ServerSocket;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;

public class ServeurMultiClient{

    public static void main(String[] args) {
        ServerSocket socket;
        try {
        socket = new ServerSocket(60000);
        Thread t = new Thread(new Service(socket));
        t.start();
        System.out.println("J'attends des connexions mais pas trop!");
	} catch (IOException e) {e.printStackTrace();}
    }
}

class Service implements Runnable{
	
	private ServerSocket socketserveur  ;
        private Socket s ;

	private static int nbrclient = 0;
	private static final int nbMaxClient =5;

        public Service(ServerSocket socket){
            socketserveur = socket;
        }
     
        public void run(){
	try {

	    PrintWriter out;
	    BufferedReader in;
        
            System.out.println("Serveur en attente");
	   
	    while(nbrclient<nbMaxClient){

 	       // écoute d'un service entrant -association socket client et socket serveur.		
               s = socketserveur.accept(); 
	       String sonIp=s.getInetAddress().toString();
	       nbrclient ++;	
               System.out.println("le client "+nbrclient+" s'est connecté :"+ sonIp);
	      
	    	// envoi d'un message par politesse - ecriture
	   	out = new PrintWriter(s.getOutputStream());
	   	Thread.sleep(1000);
	   	out.println("Bonjour "+sonIp+", quel est votre nom?");
	   	out.flush();

	   	//attente d'une reponse - lecture
		Thread.sleep(1000); 
	   	in = new BufferedReader (new InputStreamReader (s.getInputStream()));
	   	String nomRecu = in.readLine();
	   	System.out.println("message :"+ nomRecu);    

		//reponse par politesse
		out = new PrintWriter(s.getOutputStream());
		if(nomRecu!=null){
			if(nomRecu.contains("Bonjour")){
				out.println("merci"+nomRecu+", bye!");
			}else{out.println("impoli, bye!");}
			out.flush();
		}
	
           	//cloture de la connexion avec le service entrant
	   	Thread.sleep(1000);
	   	s.close();
           	System.out.println("déconnexion du client numero "+nbrclient+" d'ip "+sonIp); 
	
	   } 
		// écoute d'un service entrant -association socket client et socket serveur.		
               s = socketserveur.accept(); 
	       nbrclient ++;	
               System.out.println("le client "+nbrclient+" s'est connecté !"); 
			
		// envoi d'un message de trop de connexions
	   	out = new PrintWriter(s.getOutputStream());
	   	Thread.sleep(1000);
	   	out.println("Trop de clients, débordé, désolé!");
	   	out.flush();

           	//cloture de la connexion avec le service entrant
	   	s.close();
           	//cloture de l'ecoute
           	socketserveur.close();   
	}	
	catch (IOException e) {e.printStackTrace();}            
        catch (Exception e) {e.printStackTrace();}
    }

}
