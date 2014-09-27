import java.io.*;
import java.net.*;
class TCPServer {    
        ServerSocket welcomeSocket;
        
        public TCPServer(int porto) throws IOException{
                welcomeSocket = new ServerSocket(porto);
        }

	public void emEspera() throws IOException{
	        String clientSentence;
	        String capitalizedSentence;
	       
		//ServerSocket welcomeSocket = new ServerSocket(6789);
		while(true){           
	                 
			Socket connectionSocket = welcomeSocket.accept();
		    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));             	
			DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
      	    clientSentence = inFromClient.readLine();
      	    
			System.out.println("Received TCP: " + clientSentence);
			if( clientSentence.contains("UPR ")){
                System.out.println("pedido de ficheiro");          
            }
            
		    capitalizedSentence = clientSentence.toUpperCase() + '\n';
	        outToClient.writeBytes(capitalizedSentence);          
		}       
	} 
} 



/*
import java.io.*;
import java.net.*;
class TCPServer {    
	public static void main(String argv[]) throws Exception{ 
	        String clientSentence;
	        String capitalizedSentence;
		    ServerSocket welcomeSocket = new ServerSocket(58006);
		    
		    
		while(true){             
			Socket connectionSocket = welcomeSocket.accept();
		    BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));  
		    
      	   //* clientSentence = inFromClient.readLine();
      			System.out.println(inFromClient.readLine());
      			clientSentence = "WERFWEF";
      			
      		DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());	
			System.out.println("Received: " + clientSentence);
		        capitalizedSentence = clientSentence.toUpperCase() + '\n';
	                outToClient.writeBytes(capitalizedSentence);          
		}       
	} 
} 
*/


























