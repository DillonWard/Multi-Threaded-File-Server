package ie.gmit.sw.client.config;

public class Config {

	private String user;
	private String host;
	private int port;
	private String dir;
	private final String xmlFile = "ClientConfig.xml";

	public Config(){
		
	}

	public String getXmlFile() {
		return xmlFile;
	}

	public String getUser() {
		return user;
	}
	 
	public void setUser(String user) {
		this.user = user;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}
}