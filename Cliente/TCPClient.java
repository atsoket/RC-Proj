import java.io.*;
import java.net.*;
//Bora bruno


class TCPClient{

    int _porto;
    int _cenas;
    String _ipServidor;
    DataOutputStream outToServer;
    Socket clientSocket;
    
    public TCPClient(int porto, String ipServidor)throws SocketException, java.net.UnknownHostException{
            _ipServidor = ipServidor;
            _porto = porto;
            try{
                    clientSocket = new Socket(_ipServidor , _porto);
            }catch(IOException ex){
                    System.out.println (ex.toString());
                    System.out.println("Problema no _udpserv.emEspera(_listaConteudos);");
            }
    }
    
    

    public void emEspera(String mensagem) throws IOException{

    String sentence;
    String modifiedSentence;
    System.out.println ("mensagem: " + mensagem);
    

      DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
      BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
  
      
      outToServer.writeBytes(mensagem);
      modifiedSentence = inFromServer.readLine();
      System.out.println("FROM SERVER: " + modifiedSentence);
      clientSocket.close();
      
   
    }
}
