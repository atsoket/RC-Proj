import java.io.*;
import java.net.*;
//Bora bruno


class TCPClient{

    int _porto;
    int _cenas;
    String _ipServidor;
    DataOutputStream outToServer;
  // BufferedReader inFromServer;
    Socket clientSocket;
    
    public TCPClient(int porto, String ipServidor)throws SocketException, java.net.UnknownHostException{
            _ipServidor = ipServidor;
            _porto = porto;
            try{
                    System.out.println("Vou-me ligar ao "+ _ipServidor + " porto " + _porto);
                    clientSocket = new Socket(_ipServidor , _porto);
            }catch(IOException ex){
                    System.out.println (ex.toString());
                    System.out.println("Problema no _udpserv.emEspera(_listaConteudos);");
            }
    }
    
    

    public void emEspera(String mensagem) throws IOException{

        String[] _mensagem = mensagem.split(" ");
        String _pedido = "";

        
        if( _mensagem[0].equals("retrieve") ){
            _pedido += "REQ ";
            _pedido += _mensagem[1];
            _pedido += "\n";
            
        }
        
        if( _mensagem[0].equals("upload") ){
            _pedido += "UPR ";
            _pedido += _mensagem[1];
            _pedido += "\n";
        }
        System.out.println("Li: ");
        
        

        

	    
        
        
        outToServer = new DataOutputStream(clientSocket.getOutputStream());
      // inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        
        outToServer.writeBytes(_pedido);
        
        
        
         DataInputStream inFromServer = new DataInputStream( clientSocket.getInputStream()); 
      /*  int lixo = inFromServer.read();
         System.out.println("Li: " + lixo);*/
         byte[] digit = new byte[20];
         
       for(int i = 0; i < 20; i++)
				digit[i] = inFromServer.readByte();
		  
			  String st = new String(digit);
			  
			  System.out.println("Received: "+ st); 
         

        
        /*String modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);*/
        clientSocket.close();
      
   
    }
}
