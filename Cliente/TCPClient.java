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
    
    public TCPClient(int porto, String ipServidor){
            _ipServidor = ipServidor;
            _porto = porto;
            try{
                    //System.out.println("Vou-me ligar ao "+ _ipServidor + " porto " + _porto);
                    clientSocket = new Socket(_ipServidor , _porto);
                    System.out.println("+================================+");
                     System.out.printf("| Ligação: TCP                   |\n");
                   System.out.printf("| Destino: %-22s|\n", _ipServidor);
                   System.out.printf("| Porto: %-24d|\n", _porto);
                   System.out.println("+================================+");
                    
            }catch (ConnectException e) {
                    System.err.println("A ligação ao CS/SS falhou, verifique os dados e execute o comando mais tarde");
            }catch (UnknownHostException e) {
                    System.err.println("Host desconhecido, tente executar o comando mais tarde");
            }catch(IOException ex){
                    System.out.println (ex.toString());
                    System.out.println("Problema a criar TCP");
            }
    }
    
    public String getPalavra(){
        String _resposta = "";
        char lixo;
        
        try{
            for(int i = 0; i < 1024; i++){
                lixo = (char)inFromServer.readByte();

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
    
    public void recebeFicheiro(String mensagem){
        
        String[] _mensagem = mensagem.split(" ");
        String _pedido = "";
        
        _pedido += "REQ ";
        _pedido += _mensagem[1];
        _pedido += "\n";
            
            
        try{
        
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(_pedido);
            inFromServer = new DataInputStream( clientSocket.getInputStream()); 
            
        }catch(IOException ex){
                    System.out.println("Problema TCPClient.java:71");
        }
        
        
        
        /*REP ok 175971 DATA*/
        String st = new String( getPalavra() );

        if(st.equals("REP")){

            st = new String( getPalavra() );
            if(st.equals("ok")){

                st = new String( getPalavra() );
                int tamanhoFicheiro = Integer.parseInt(st);
                
                
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
                }
                
                
                
                
            }else{
                System.out.println("O ficheiro não foi encontrado no SS");
            }
        }
    }
    
    public void enviaFicheiro(String mensagem){
    
        String[] _mensagem = mensagem.split(" ");
        String _pedido = "";
        _pedido = "UPR " + _mensagem[1] + "\n";
        
        try{
        
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            outToServer.writeBytes(_pedido);
            inFromServer = new DataInputStream( clientSocket.getInputStream()); 
            
            
        }catch(IOException ex){
                    System.out.println("Problema TCPClient.java:142");
        }

        String st = new String( getPalavra() );
        if(st.equals("AWR")){
            st = new String( getPalavra() );
            if(st.equals("new")){                
                try{                   
                    	File file = new File(_mensagem[1]);
			            byte[] fileData = new byte[(int) file.length()];
			            DataInputStream dis = new DataInputStream(new FileInputStream(file));
			            dis.readFully(fileData);
			            dis.close();
                        outToServer.write(("UPC " + fileData.length + " ").getBytes());
						outToServer.write(fileData);
						outToServer.write("\n".getBytes());

                        outToServer.flush();

			          
			            st = new String( getPalavra() );
			            if(st.equals("AWC")){
			                st = new String( getPalavra() );
                            if(st.equals("ok")){
                                System.out.println("Ficheiro enviado com sucesso");
                                return;
                            }else
                            System.err.println("Existiu um problema do lado do CS\nPor favor volte a fazer upload.");
			            }
                    
                }catch(FileNotFoundException fnf){
                    System.out.println("Ficheiro não encontrado\n");
                }catch(IOException ex){
                            System.out.println("Problema TCPClient.java:186" + ex);
                }
     
                
            }else if( st.equals("dup") ){
                System.out.println("Ficheiro Duplicado");
            }else{
                System.out.println("Má resposta do servidor");
            }
        }
        
        try{
         outToServer.close();
         inFromServer.close();
         clientSocket.close();
         }catch(IOException ex){
                            System.out.println("Problema a fechar");
                }
    }   

    public void emEspera(String mensagem) throws IOException{

        String[] _mensagem = mensagem.split(" ");  
        
        if( clientSocket == null){
            return;
        }
              
        if( _mensagem[0].equals("retrieve") )            
            recebeFicheiro(mensagem);  
                      
        else if( _mensagem[0].equals("upload") )        
            enviaFicheiro(mensagem);     
                   
        else        
            System.out.println("Comando não reconhecido");
            
        

        clientSocket.close();
   
    }
}

