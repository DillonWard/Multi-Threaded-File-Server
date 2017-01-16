package ie.gmit.sw.client.config;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser implements Parseator{		
	
	Config conf = new Config(); // Create an instance of Config so values can be assigned as they are read in
	
	public XMLParser(Config conf){
		this.conf = conf;
	}

	public void parser() throws Throwable{

		/* Read in the XML file, get values/elements by their tags
		 *  Compare the tags in the file and assign them accordingly
		 */
		
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(conf.getXmlFile());
		NodeList nList = doc.getElementsByTagName("client-config");
		Element root = doc.getDocumentElement();
		NodeList nl = root.getChildNodes();

		for (int i = 0; i < nl.getLength(); i++) {

			Node nNode = nList.item(i);
				
			if (nNode instanceof Element) {
				Element eElement = (Element) nNode;
				conf.setUser(eElement.getAttribute("username"));
								
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					// 	Set values based on their element tags
					conf.setHost(eElement.getElementsByTagName("server-host").item(0).getTextContent());
					// Set the port, parse it to an INT
					conf.setPort(Integer.parseInt(eElement.getElementsByTagName("server-port").item(0).getTextContent()));		
					conf.setDir(eElement.getElementsByTagName("download-dir").item(0).getTextContent());	
				}
				
			}
		}
	 }
}
