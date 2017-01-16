package ie.gmit.sw.request;

import java.util.Date;

public class Download extends Request{
	
	private static final long serialVersionUID = 1L;
	private String fileName;
	
	public Download(String command, String host, Date d) {
		super(command, host, d);
	}

	public String getFilename() {
		return fileName;
	}

	public void setFilename(String filename) {
		this.fileName = filename;
	}
}