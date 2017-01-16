package ie.gmit.sw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import ie.gmit.sw.client.config.Config;
import ie.gmit.sw.request.Request;

public class ServerRunner {
		public static void main(String[] args) throws IOException {
			
			ServerSocket server = new ServerSocket(7777);
			
			BlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(10);
			
			while(true){
				Socket socket = server.accept();
				Server serv = new Server(socket, queue);
				serv.start();
			}
		}
}