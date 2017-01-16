package ie.gmit.sw.client;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;
import java.util.Scanner;
import ie.gmit.sw.client.config.Config;
import ie.gmit.sw.client.config.XMLParser;
import ie.gmit.sw.request.Request;

public class ClientRunner {
	public static void main(String[] args) throws Throwable {
			
		Config conf = new Config(); // creates instance of Class
		UI ui = new UI();
		XMLParser parser = new XMLParser(conf); 
		parser.parser(); // Parses the XML file
		Scanner scan = new Scanner(System.in);
		int option;
		/* the values that were parsed in are initialised
		 * set to local variables using .get
		 */
		int port = conf.getPort();
		String host = conf.getHost();
		String dir = conf.getDir();
		Socket socket = null;
		ObjectOutputStream out = null;
	 	ObjectInputStream in = null;
	 	boolean connected = false;
	 	
		while(ui.isActive()){
			ui.menu();
			String message = null;	
			option = scan.nextInt();
			
			
			switch(option){
			
			case 1:
				ui.connecting();
				socket = new Socket(host, port);
				out = new ObjectOutputStream(socket.getOutputStream());
				out.flush();
				in = new ObjectInputStream(socket.getInputStream());
				connected = true;
				Request req = new Request("1", host, new Date());
				out.writeObject(req);
				
				
				do{
					message = (String)in.readObject();
				}while(message == null);
				
				break;
			
			case 2:
				ui.files();
				if(connected){
					req = new Request("2",host, new Date());
					out.writeObject(req);
					out.flush();
					do{
						message = (String)in.readObject();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}while (message == null);
					
					System.out.println(message);
				}
				else{
					System.out.println("No connection established - Enter 1");
				}

				break;
			
			case 3:
				ui.download();
				
				if(connected){
					req = new Request("3", host, new Date());
					out.writeObject(req);
					out.flush();
					scan.nextLine();
					String f = scan.nextLine();
					
					out.writeObject(f);
					out.flush();
					
					do{
						message = (String)in.readObject();
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}while(message == null);
					
					/* if the message sent from the server is not "NO FILE"
					 * Objects are then written out
					 * the directory is read in and used as well as the file name
					 */
					
					if(message != "NO FILE"){
						new File(dir).mkdir();
						
						PrintWriter pw = new PrintWriter(dir + "/" + f);
					}
					
					else{
						System.out.println(message);
					}
				}
				else{
					// just so the user knows to connect to the server before trying to use a command
					System.out.println("No connection established - Enter 1");
					
				}
				break;
				
			case 4:
				ui.quit();
				System.exit(0);
				// Scanner/Sockets are closed
				socket.close();
				scan.close();
				break;
				
				default:
					System.out.println("Re Enter [1 - 4");
					break;
			}
		}
	}
}