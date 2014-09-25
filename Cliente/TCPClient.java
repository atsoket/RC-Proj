import java.io.*;
import java.net.*;
//Bora bruno


class TCPClient{

    int _porto;
    int _cenas;
    String _ipServidor;
    Socket clientSocket;
    
    public UDPClient(int porto, String ipServidor)throws SocketException, java.net.UnknownHostException{
            clientSocket = new DatagramSocket();
	        _ipServidor = ipServidor;
            _porto = porto;
    }

    public static void main(String args[]) throws IOException{

    String sentence;
    String modifiedSentence;

    while(true){  

      BufferedReader inFromUser = new BufferedReader( new InputStreamReader(System.in)); //MUDAR ESTA PORRA
      clientSocket = new Socket(_ipServidor , _porto);
      
      
      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      sentence = inFromUser.readLine();
      outToServer.writeBytes(sentence + '\n');
      modifiedSentence = inFromServer.readLine();
      System.out.println("FROM SERVER: " + modifiedSentence);
      clientSocket.close();
      
    }
    }
}
