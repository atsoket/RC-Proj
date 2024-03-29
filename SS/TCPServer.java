import java.io.*;
import java.net.*;
class TCPServer {    
        Socket welcomeSocket;
        DataInputStream inFromClient;
        DataOutputStream outToClient;
        boolean BARRAN=false;

        public TCPServer(Socket socket) throws IOException{
                welcomeSocket = socket;
        }

    public String getPalavra(){
        String _resposta = "";
        char lixo;
        
        try{
            for(int i = 0; i < 1024; i++){
                lixo = (char)inFromClient.readByte();

                if((byte)lixo == 10){
                    BARRAN=true;
                    break;
                }
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

     public byte[] readFile(String filename) throws IOException {
		try {
			File file = new File(filename);
			byte[] fileData = new byte[(int) file.length()];
			DataInputStream dis = new DataInputStream(new FileInputStream(file));
			dis.readFully(fileData);
			dis.close();

			System.out.println("Ficheiro carregado");
			return fileData;

		} catch (FileNotFoundException e) {
			System.err.println("O ficheiro não se encontra no SS");
			return "nok".getBytes();
		}

	}

	public void emEspera() throws IOException{
	
	        String _nomeFicheiro;
	        
	        try{   
	 		    inFromClient = new DataInputStream( welcomeSocket.getInputStream()); 
            }catch(IOException ex){
                            System.err.println("Problema a criar o DataInputStream");
                            return;
            }
            
            String _comando = new String( getPalavra() );
            
            

            if( _comando.equals("REQ") ){
                try {
                    
                    
                    
				    outToClient = new DataOutputStream( welcomeSocket.getOutputStream() );
                    _nomeFicheiro = new String( getPalavra() );  
                    
               
                
                    System.out.println("+================================+");
                    System.out.printf("| Pedido: REQ                    |\n");
                    System.out.printf("| Origem: %-23s|\n", welcomeSocket.getInetAddress().getHostAddress());
                    System.out.printf("| Porto: %-24d|\n", welcomeSocket.getPort());
                    System.out.printf("| Ficheiro: %-21s|\n", _nomeFicheiro);
                    System.out.println("+================================+");
                
                    if( BARRAN){
				
						String pedido = _nomeFicheiro + " "	+ welcomeSocket.getInetAddress().getHostAddress()+ welcomeSocket.getPort();
						//System.out.println(pedido);
						String toSend = "REP ";
						byte[] tofile = readFile(_nomeFicheiro);
						if (!(new String(tofile).equals("nok"))) {
							outToClient.write((toSend + "ok " + tofile.length + " ").getBytes());
							outToClient.write(tofile);
							outToClient.write("\n".getBytes());
							System.out.println("Ficheiro enviado.");
						} else {
							outToClient.write((toSend + "nok\n").getBytes());
						}

						outToClient.flush();
						}
						welcomeSocket.close();
				        outToClient.close();
				        inFromClient.close();
					}catch (IOException e) {
				        System.err.println("Erro de I/O.");
			        }
				

				
			 
            }else if( _comando.equals("UPS") ){
                
                _nomeFicheiro = new String( getPalavra() );       
                   
                try{   
                    outToClient = new DataOutputStream( welcomeSocket.getOutputStream() );
                }catch(IOException ex){
                            System.err.println("Problema a criar o DataOutputStream");
                            return;
            }
                
                _comando = new String( getPalavra() );
                        
      
				    System.out.println("+================================+");
                    System.out.printf("| Pedido: UPS                    |\n");
                    System.out.printf("| Origem: %-23s|\n", welcomeSocket.getInetAddress().getHostAddress());
                    System.out.printf("| Porto: %-24d|\n", welcomeSocket.getPort());
                    System.out.printf("| ficheiro: %-21s|\n", _nomeFicheiro);
                    System.out.println("+================================+");
				        
                        try{
                        
                            int tamanhoFicheiro = Integer.parseInt(_comando);
                        
                            FileOutputStream fos = new FileOutputStream(_nomeFicheiro);
                            BufferedOutputStream bos = new BufferedOutputStream(fos);

                            for(int k=0; k<tamanhoFicheiro; k++)
                                bos.write( inFromClient.readByte() );

                            

                            char lie = (char)inFromClient.readByte();

                            if( lie != '\n'){
                                System.out.println("Mensagem sem \\n no final");
                                outToClient.writeBytes("ERR\n");
                                outToClient.close();
                                inFromClient.close();
                            }else{
                                 bos.close();
                                 outToClient.writeBytes("AWS ok\n");
                                 outToClient.close();
                                 inFromClient.close();
                                 System.out.println("Ficheiro recebido com sucesso");
                            }

                        }catch (NumberFormatException e) {
					        System.err.println("Tamanho do ficheiro não é um inteiro");
					        System.exit(1);
				        }catch(FileNotFoundException fnf){
                            outToClient.writeBytes("AWS nok\n");
                            System.out.println("Problema a criar Ficheiro\n" + "Problema TCPServer.java:89");
                           
                        }catch(EOFException eoef){
                            System.err.println("O stream do cliente foi interrompido, como tal o ficheiro não foi adicionado");
                        }catch(IOException ex){
                            outToClient.writeBytes("AWS nok\n");
                            System.out.println("Problema TCPServer.java:91");
                            
                        }               
            }
       
   }	
}


























