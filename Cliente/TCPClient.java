import java.io.*;
import java.net.*;
//Bora bruno


class TCPClient{

    int _porto;
    int _cenas;
    String _ipServidor;
    DataOutputStream outToServer;
    DataInputStream inFromServer;
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
    
    public String getPalavra(){
        String _resposta = "";
        char lixo;
        
        try{
            for(int i = 0; i < 1024; i++){
                lixo = (char)inFromServer.readByte();
	            if( lixo != ' ')
	                _resposta += lixo;
	            else
	                break;
	        }
        }catch(IOException ex){
                    System.out.println("Problema no getPalavra()");
            }
            
            return _resposta;
    }
    
    public byte[] leXBytes(int numBytes){
        byte[] _resposta = new byte[numBytes];
        
        try{
            for(int i = 0; i < numBytes; i++)
	            _resposta[i] = inFromServer.readByte();
	    }catch(IOException ex){
                    System.out.println("Problema no readByte");
            }
	        
        return _resposta;
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

        outToServer = new DataOutputStream(clientSocket.getOutputStream());
        outToServer.writeBytes(_pedido);
        
        inFromServer = new DataInputStream( clientSocket.getInputStream()); 
        
        /*REP ok 175971 DATA*/
        String st = new String( getPalavra() );

        if(st.equals("REP")){
            st = new String( getPalavra() );
            if(st.equals("ok")){

                st = new String( getPalavra() );
                int tamanhoFicheiro = Integer.parseInt(st);
                FileOutputStream fos = new FileOutputStream(_mensagem[1]);
                BufferedOutputStream bos = new BufferedOutputStream(fos);

                
                for(int k=0; k<tamanhoFicheiro; k++)
                    bos.write( inFromServer.readByte() );

                bos.close();
                
            }else{
                System.out.println("O ficheiro não foi encontrado no SS");
            }
        }
         
	     
         

        
        /*String modifiedSentence = inFromServer.readLine();
        System.out.println("FROM SERVER: " + modifiedSentence);*/
        clientSocket.close();
      
   
    }
}
