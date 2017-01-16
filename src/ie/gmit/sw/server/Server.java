package ie.gmit.sw.server;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.concurrent.*;
import ie.gmit.sw.request.*;

public class Server extends Thread{

	Socket ssocket; // ssocket = server socket
	String path;
	BlockingQueue<Request> queue;
	ObjectOutputStream out;
	ObjectInputStream in;
	Request r = new Request();
	
	 Server(Socket s, BlockingQueue<Request> queue){
		this.ssocket = s;
		this.queue = queue;
	}
	 public Server(){
		 
	 }

	// Save me from having to retype it - "we're nice and lazy here, folks"
	public void streamSetup() throws IOException{
		out = new ObjectOutputStream(ssocket.getOutputStream());
		out.flush();
		in = new ObjectInputStream(ssocket.getInputStream());
	}
	
	public void run(){
		 try {
			 // set the command to one - insert into blocking queue
			queue.put(new Request("1", ssocket.getInetAddress().getHostName(), new Date()));
		
		 } catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("ERROR - QUEUE - SERVER");
		}
		 
		 /* Run method/menu that gets/sets the command for specific methods
		  * Methods are either called, or the connection is added to the queue
		  */
		 
		 try {
			streamSetup();
			Request r = null;
			
			do{
				do{
					r = (Request)in.readObject();
					Thread.sleep(1000); // puts the thread to sleep so other threads can use processing time
					
					switch(r.getCommand()){
					case "1":
						sendMessage("Connected");
						queue.add(new Request("1", ssocket.getInetAddress().getHostName(), new Date()));
						break;
						
					case "2":
						sendList();
						break;
						
					case "3":
						download();
						break;
						
						default:
							System.out.println("error");
							break;
					}
				}while(r == null);
				// continues running while the socket is not closed
			}while(!ssocket.isClosed());
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ERROR - OUTPUTSTREAM - SERVER");
		}

	 }
	
	private void sendMessage(String msg){
		/* Messages are sent out to the user along an output stream
		 * messages are written in by the server and are wrapped in a "msg" object
		 * after they are sent, the buffer is flushed
		 */
		try{
			out.writeObject(msg); // sends message to the user
			out.flush(); // flushes the buffer
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* Goes to the root of the directory
	 * finds all the files in the list and adds them to an array
	 * the files are printed out
	 */
	private void sendList(){
		
		File f = new File(".");
		File[] fList = f.listFiles(); // an array for storing the names of the files/folders
		
		String files = "";
		
		/* runs through all the files
		 * essentially, keep counting while the folder is not empty
		 * print out all the file names, size = amount of folders/files
		 */
		for(int i = 0; i < fList.length; i++){
			files += (fList[i].getName()) + " "; 
		}
		sendMessage(files); // sends name of files/folders to the user
	}
	
	private void download() throws FileNotFoundException, InterruptedException{
		
		/* file name is entered through message and is sent to the server
		 * the server checks to see if the file entered (f) exists and if it is indeed a file 
		 * Text is read in from the input stream using the buffered reader
		 * the file is read and sent to the user - where it will be wrote out and saved, essentially "downloaded"
		 * on the client side, if no message saying "NO FILE" is sent, the file is created and written
		 * the name entered is the name of the file when it is saved
		*/
		
		String message = null;
		
		do{
			try {
				message = (String)in.readObject();
				Thread.sleep(1000);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}while(message == null);
		
		File f = new File(message);
		
		if(f.exists()&&f.isFile()){
			// couldn't get it to work properly - file was being created, but was blank
		}
		else{
			sendMessage("NO FILE");
		}
		
		Download d = new Download("3", ssocket.getInetAddress().getHostName(), new Date());
		d.setFilename(message);
		queue.put(d);	
	}	
}