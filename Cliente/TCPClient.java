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
                    System.out.println("Problema no TCP");
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
                            if(st.equals("ok"))
                                return;
                            else
                            System.out.println("shit");
			            }
                    
                }catch(FileNotFoundException fnf){
                    System.out.println("Problema a criar Ficheiro\n" + "Problema TCPClient.java:97");
                }catch(IOException ex){
                            System.out.println("Problema TCPClient.java:186" + ex);
                }
                
            
            }else if( st.equals("dup") ){
                System.out.println("Ficheiro Duplicado");
            }else{
                System.out.println("Má resposta do servidor");
            }
        }
        
    }   

    public void emEspera(String mensagem) throws IOException{

        String[] _mensagem = mensagem.split(" ");  
              
        if( _mensagem[0].equals("retrieve") )            
            recebeFicheiro(mensagem);  
                      
        else if( _mensagem[0].equals("upload") )        
            enviaFicheiro(mensagem);     
                   
        else        
            System.out.println("Comando não reconhecido");
            
        

        clientSocket.close();
   
    }
}


/* CÓDIGO EXEMPLO
 while (true) {
    
      Socket sock = servsock.accept();
      byte[] mybytearray = new byte[(int) myFile.length()];
      
      
      System.out.println("tamanho server: " + (int) myFile.length()); //ESTÁ CERTO FDX
      
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
      
      bis.read(mybytearray, 0, mybytearray.length);
      
      OutputStream os = sock.getOutputStream();
      
      os.write(mybytearray, 0, mybytearray.length);
      
      os.flush();
      
      sock.close();
*/
/* OUTRO

FileWriter out = new FileWriter("test.txt");
			  BufferedWriter bufWriter = new BufferedWriter(out);
		   
			  //Step 1 read length
			  int nb = input.readInt();
			  System.out.println("Read Length"+ nb);
			  byte[] digit = new byte[nb];
			  //Step 2 read byte
			   System.out.println("Writing.......");
			  for(int i = 0; i < nb; i++)
				digit[i] = input.readByte();
			  
			   String st = new String(digit);
			  bufWriter.append(st);
			   bufWriter.close();
				System.out.println ("receive from : " + 
				clientSocket.getInetAddress() + ":" +
				clientSocket.getPort() + " message - " + st);
			  
			  //Step 1 send length
			  output.writeInt(st.length());
			  //Step 2 send length
			  output.writeBytes(st); // UTF is a string encoding
		  //  output.writeUTF(data); 
			} 
			catch(EOFException e) {
			System.out.println("EOF:"+e.getMessage()); } 
			catch(IOException e) {
			System.out.println("IO:"+e.getMessage());}  
   
			finally { 
			  try { 
				  clientSocket.close();
			  }
			  catch (IOException e){}
			}

*/
