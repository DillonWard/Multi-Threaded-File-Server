package ie.gmit.sw.client;

public class UI  {
	
	// Just a UI to make the ClientRunner look a bit tidier/space things out a bit more
	
	boolean cont;
	
	public UI(){
		this.cont = true;
	}
	
	public void menu(){
		System.out.println("1 - Connect to Server");
		System.out.println("2 - Print File Listing");
		System.out.println("3 - Download File");
		System.out.println("4 - Quit");
	}
	
	public void connecting(){
		System.out.println("Connecting to Server.");
	}
	
	public void files(){
		System.out.println("List of Files.");
	}
	
	public void download(){
		System.out.println("Downloading File from Server - Choose a File to download");
	}
	
	public boolean isActive(){
		return cont;
	}
	
	public void quit(){
		System.out.println("Quitting...");
		this.cont = false;
	}	
}
