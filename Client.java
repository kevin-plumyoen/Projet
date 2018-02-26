import java.io.IOException;
import java.net.UnknownHostException;

import java.net.InetAddress;
import java.net.Socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;


public class Client {

    public static void main(String[] args) {

        Socket socket;
	BufferedReader in;
	PrintWriter out;
        try {
	//demande d'ouverture d'une connexion sur le serveur local et le numero de port 60000
         socket = new Socket(args[0],60000);

	//attente d'une reponse - lecture
	in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
	String message_distant = in.readLine();
	System.out.println("message :"+ message_distant);

	//reponse par politesse
	out = new PrintWriter(socket.getOutputStream());
	if(message_distant.contains("Bonjour")==0){
		out.println("oh le serveur!");
	}else{out.println("impoli!");}
	out.flush();

	//attente d'une reponse - lecture
	in = new BufferedReader (new InputStreamReader (socket.getInputStream()));
	message_distant = in.readLine();
	System.out.println("message :"+ message_distant);

        //fermeture de la connexion
	socket.close();

        }catch (UnknownHostException e) {
		 e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
