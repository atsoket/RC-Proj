import java.io.*;
import java.net.*;
import java.util.ArrayList;


class TCPServer {    

        ArrayList<Socket> _listaSockets = new ArrayList<Socket>();
        ArrayList<DataOutputStream> _listaOut = new ArrayList<DataOutputStream>();
        ArrayList<DataInputStream> _listaIn = new ArrayList<DataInputStream>();

        Socket welcomeSocket;
        DataInputStream inFromClient;
        DataOutputStream outToClient;

        public TCPServer(Socket socket) throws IOException{
                welcomeSocket = socket;
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

	public void emEspera( ListaFicheiros listaConteudos, ArrayList<String> listaSS ) throws IOException{
	       		

           
            inFromClient = new DataInputStream( welcomeSocket.getInputStream()); 
       
            String _comando = new String( getPalavra() );

            if( _comando.equals("REQ") ){
                outToClient = new DataOutputStream( welcomeSocket.getOutputStream() );
                outToClient.writeBytes("ERR\n");
                System.out.println("O pedido retrieve deve ser efectuado para o SS");
            }else if( _comando.equals("UPR") ){
                String _nomeFicheiro = new String( getPalavra() );

                
                if( listaConteudos.procura(_nomeFicheiro) ){
                    outToClient = new DataOutputStream( welcomeSocket.getOutputStream() );
                    _comando = "AWR new\n";
                    outToClient.writeBytes(_comando);
                    /*UPC size data*/
                    _comando = new String( getPalavra() );
               
                    if( _comando.equals("UPC") ){
       
                        _comando = new String( getPalavra() );
                        int tamanhoFicheiro = Integer.parseInt(_comando);
                 
                        try{

                            FileOutputStream fos = new FileOutputStream(_nomeFicheiro);
                            BufferedOutputStream bos = new BufferedOutputStream(fos);
                            
        
                            for(int j=0; j<listaSS.size(); j++){
                                String[] aux = listaSS.get(j).split(" ");
                                _listaSockets.add(new Socket(aux[0] , Integer.parseInt(aux[1])));
                                _listaOut.add( new DataOutputStream( _listaSockets.get(j).getOutputStream() ) );
                                _listaOut.get(j).writeBytes("UPS " + _nomeFicheiro + " " + tamanhoFicheiro + " ");
                                
                            }
                            System.out.println(" Pos envio");

                            for(int k=0; k<tamanhoFicheiro; k++){
                                byte _lixo = inFromClient.readByte();
                                for(int j=0; j<listaSS.size(); j++){
                                    _listaOut.get(j).write( _lixo );
                                }
                                bos.write(  _lixo );
                            }
                            System.out.println(" Pre get respostas");

                            for(int j=0; j<listaSS.size(); j++){
                                    _listaOut.get(j).write("\n".getBytes());
                                    _listaIn.add( new DataInputStream( _listaSockets.get(j).getInputStream() ) );
                                    System.out.println( (String)_listaIn.get(j).readLine() );
                            }
                           
                            System.out.println(" Pos get respostas");

                            char lie = (char)inFromClient.readByte();

                            if( lie != '\n'){
                                System.out.println("Mensagem sem \\n no final");
                                outToClient.writeBytes("ERR\n");
                            }else{
                                 bos.close();
                                 listaConteudos.addFicheiro(_nomeFicheiro);
                                 /*ENVIAR OS FICHEIROS PÓ SS*/
                                 System.out.println("Tamanho que o ficheiro deverá ter: " + tamanhoFicheiro
						+ " bytes.");
			
				
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
                     outToClient = new DataOutputStream( welcomeSocket.getOutputStream() );
                    outToClient.writeBytes("AWR dup\n");
                    System.out.println("Ficheiro " + _nomeFicheiro + " em duplicado ");
                }                
            }
    }
    

} 


























