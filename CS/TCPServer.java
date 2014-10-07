import java.io.*;
import java.net.*;
class TCPServer {    
        ServerSocket welcomeSocket;
        DataInputStream inFromClient;
        DataOutputStream outToClient;

        public TCPServer(int porto) throws IOException{
                welcomeSocket = new ServerSocket(porto);
        }

    public String getPalavra(){
        String _resposta = "";
        char lixo;
        
        try{
            for(int i = 0; i < 1024; i++){
                lixo = (char)inFromClient.readByte();

                if((byte)lixo == 10)
                    break;
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

	public void emEspera( ListaFicheiros listaConteudos ) throws IOException{
	        String clientSentence;
	        String capitalizedSentence; 
	       
	  //  try{
        
           /* inFromClient = new DataOutputStream(clientSocket.getOutputStream());
            inFromClient.writeBytes(_pedido);*/
      //      inFromClient = new DataInputStream( clientSocket.getInputStream()); 
            
     //   }catch(IOException ex){
      //              System.out.println("Problema TCPClient.java:71");
     //   }


		while(true){           

	                 
			Socket connectionSocket = welcomeSocket.accept();

            inFromClient = new DataInputStream( connectionSocket.getInputStream()); 
       
            String _comando = new String( getPalavra() );

            if( _comando.equals("REQ") ){
                System.out.println(" ahahahah ");
            }else if( _comando.equals("UPR") ){
                String _nomeFicheiro = new String( getPalavra() );
                
                if( listaConteudos.procura(_nomeFicheiro) ){
                    
                    outToClient = new DataOutputStream( connectionSocket.getOutputStream() );
                    outToClient.writeBytes("AWC ok");

                }
                /*
                try{
                
                    FileOutputStream fos = new FileOutputStream(_mensagem[1]);
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    
                    for(int k=0; k<tamanhoFicheiro; k++)
                        bos.write( inFromServer.readByte() );
                        
                    bos.close();
                    
                    char lie = (char)inFromServer.readByte();
                
                    if( lie != '\n')
                        System.out.println("Mensagem sem \\n no final");
                    
                }catch(FileNotFoundException fnf){
                    System.out.println("Problema a criar Ficheiro\n" + "Problema TCPClient.java:97");
                }catch(IOException ex){
                            System.out.println("Problema TCPClient.java:71");
                }*/
            }
           

		          
		}       
	}

    /* public void emEspera(ListaFicheiros listaConteudos) throws IOException{

        String[] _mensagem = mensagem.split(" ");  
              
        if( _mensagem[0].equals("retrieve") )            
            recebeFicheiro(mensagem);  
                      
        else if( _mensagem[0].equals("upload") )        
            enviaFicheiro(mensagem);     
                   
        else        
            System.out.println("Comando não reconhecido");
            
        

        clientSocket.close();
   
    }*/
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


























