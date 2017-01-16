# Multi-Threaded-File-Server
## Project Overview
The following project is a Multi-Threaded File Server that runs locally, takes input from the user, and can display the contents of a directory and/or download a file. The project is to be as abstract as possible. This readme is a walkthrough of each class and it's functionalities, namely:
1. ClientRunner
2. XMLParser
3. Request
4. Server
5. ServerRunner

### ClientRunner
> By default, the client is not connected - if an option is entered while the connection is not up, an error message will be sent to the user telling them to first connect.
> Once either of the choices are picked, there is a request sent to the server which sends the command chosen [1 - 3], the Host which is Parsed in from an XML File and the date.


The ClientRunner class is the where the user sends messages to the server through input. There is a UI class used that calls Sysouts that are prepared in methods just to keep it nice and tidy.
There are 4 options that the user can choose from.
`Connection
List Files
Download Files
Exit`

#### Connection
When this is called, the socket is set up - taking in the parameters of the host and the port which are parsed in from an XML file. The Input/Output stream is then initialized so that the server and client can communicate - a stream for connection.
The Request is then send to the request method and initialized, where it will be set and called.

#### List Files
Once this method is called, at first the message from the server is sent which displays the list of files, after which the thread is put to sleep. How this works will be fully fleshed out later.

#### Download
Once this method is called, the user is prompted to enter the name of a file of which they would like to download - files names can be seen from requesting the list of files. The thread is put to sleep after reading in, then IF the message received from the server is not "NO FILE" there will be a new file created in the directory of the program - essentially it will be a copy/paste of the file already read in.

#### Quit
Simply exits the program while closing the sockets and scanner.


### XMLParser
The XML Parser method is used to take in the contents of the "ClientConfig.xml" file which contains the details needed to connect the server to the client and keep the server running. At first, a DocumentBuilderFactory is called - which is used for parsing DOM objects from an XML document. The XML File is parsed by getting it's name from the Config class:
`Document doc = dBuilder.parse(conf.getXmlFile());`
The file is looped through to get each element, and as they are they are set to variables that can be used within the application:
`conf.setHost(eElement.getElementsByTagName("server-host").item(0).getTextContent());`


### Request 
The request class is the means of communication of methods to the server. The command is sent, along with the Host (which is parsed) and the date for logging.

`public Request(String command, String host, Date date){
		setCommand(command);
		setHost(host);
		setDate(date);
	}`
	The request is used for adding Requests to a Blocking Queue array list.


### Server
The server is where the main running method is, where the command is read in from the user and actions are taken accordingly. Messages are sent back and forth between the server and client, which act as controllers and cause certain actions to happen, messages are read in and certain things may or may not occur. 
