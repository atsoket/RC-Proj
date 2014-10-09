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
                outToClient = new DataOutputStream( connectionSocket.getOutputStream() );
                outToClient.writeBytes("ERR\n");
                System.out.println("O pedido retrieve deve ser efectuado para o SS");
            }else if( _comando.equals("UPR") ){
                String _nomeFicheiro = new String( getPalavra() );

                
                if( listaConteudos.procura(_nomeFicheiro) ){
                    outToClient = new DataOutputStream( connectionSocket.getOutputStream() );
                    _comando = "AWR new\n";
                    outToClient.writeBytes(_comando);
                    /*UPC size data*/
                    _comando = new String( getPalavra() );
                    System.out.println("resposta: " + _comando);
                    if( _comando.equals("UPC") ){
                        _comando = new String( getPalavra() );
                        int tamanhoFicheiro = Integer.parseInt(_comando);
                        
                        try{

                            FileOutputStream fos = new FileOutputStream(_nomeFicheiro);
                            BufferedOutputStream bos = new BufferedOutputStream(fos);

                            for(int k=0; k<tamanhoFicheiro; k++)
                            bos.write( inFromClient.readByte() );

                            

                            char lie = (char)inFromClient.readByte();

                            if( lie != '\n'){
                                System.out.println("Mensagem sem \\n no final");
                                outToClient.writeBytes("ERR\n");
                            }else{
                                 bos.close();
                                 listaConteudos.addFicheiro(_nomeFicheiro);
                                 /*ENVIAR OS FICHEIROS PÓ SS*/
                                 outToClient.writeBytes("AWC ok\n");
                                 System.out.println("Ficheiro recebido com sucesso");
                            }

                        }catch(FileNotFoundException fnf){
                        System.out.println("Problema a criar Ficheiro\n" + "Problema TCPServer.java:89");
                        }catch(IOException ex){
                        System.out.println("Problema TCPServer.java:91");
                        }
                    
                    }else{
                        System.out.println("O user não respondeu o UPC correcto");
                    }

                }else{
                     outToClient = new DataOutputStream( connectionSocket.getOutputStream() );
                    outToClient.writeBytes("AWR dup\n");
                    System.out.println("Ficheiro " + _nomeFicheiro + " em duplicado ");
                }
                
            }
           

		          
		}       
	}

} 


























