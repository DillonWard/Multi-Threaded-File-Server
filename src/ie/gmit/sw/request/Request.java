package ie.gmit.sw.request;

import java.io.Serializable;
import java.util.Date;


public class Request implements Serializable{
	private static final long serialVersionUID = 1L;
	private String command;
	private String host;
	private Date d;
	
	public Request(String command, String host, Date date){
		setCommand(command);
		setHost(host);
		setDate(date);
	}
	
	public Request(){
		
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	
	public Date getDate() {
		return d;
	}

	public void setDate(Date d) {
		this.d = d;
	}
}