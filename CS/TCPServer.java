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
	       		
            boolean tudoOK=true;
           
            inFromClient = new DataInputStream( welcomeSocket.getInputStream()); 
       
            String _comando = new String( getPalavra() );

            if( _comando.equals("REQ") ){
                outToClient = new DataOutputStream( welcomeSocket.getOutputStream() );
                outToClient.writeBytes("ERR\n");
                System.out.println("O pedido retrieve deve ser efectuado para o SS");
            }else if( _comando.equals("UPR") ){
                 
                String _nomeFicheiro = new String( getPalavra() );

                               
               System.out.println("+================================+");
               System.out.printf("| Pedido: UPR                    |\n");
               System.out.printf("| Origem: %-23s|\n", welcomeSocket.getInetAddress().getHostAddress());
               System.out.printf("| Porto: %-24d|\n", welcomeSocket.getPort());
               System.out.printf("| Ficheiro: %-21s|\n", _nomeFicheiro);
               System.out.println("+================================+");
                
                if( listaConteudos.procura(_nomeFicheiro) ){
                    outToClient = new DataOutputStream( welcomeSocket.getOutputStream() );
                    _comando = "AWR new\n";
                    outToClient.writeBytes(_comando);
                    
                    _comando = new String( getPalavra() );
               
                    if( _comando.equals("UPC") ){
       
                        _comando = new String( getPalavra() );
                        int tamanhoFicheiro = Integer.parseInt(_comando);
                 
                        try{

                           // FileOutputStream fos = new FileOutputStream(_nomeFicheiro);
                          //  BufferedOutputStream bos = new BufferedOutputStream(fos);
                            
        
                            for(int j=0; j<listaSS.size(); j++){
                                String[] aux = listaSS.get(j).split(" ");
                                _listaSockets.add(new Socket(aux[0] , Integer.parseInt(aux[1])));
                                _listaOut.add( new DataOutputStream( _listaSockets.get(j).getOutputStream() ) );
                                _listaOut.get(j).writeBytes("UPS " + _nomeFicheiro + " " + tamanhoFicheiro + " ");
                                
                            }
                            

                            for(int k=0; k<tamanhoFicheiro; k++){
                                byte _lixo = inFromClient.readByte();
                                for(int j=0; j<listaSS.size(); j++){
                                    _listaOut.get(j).write( _lixo );
                                }
                                //bos.write(  _lixo );
                            }
                            

                            for(int j=0; j<listaSS.size(); j++){
                                    _listaOut.get(j).write("\n".getBytes());
                                    _listaIn.add( new DataInputStream( _listaSockets.get(j).getInputStream() ) );
                                    //System.out.println( (String)_listaIn.get(j).readLine() );
                                   String cenas = _listaIn.get(j).readLine();
                                   if( !cenas.contains("AWS ok"))
                                       tudoOK = false;
                                       
                                        
                            }
                           
                           
                            char lie = (char)inFromClient.readByte();

                            if( lie != '\n'){
                                System.out.println("Mensagem sem \\n no final");
                                outToClient.writeBytes("ERR\n");
                            }else{
                                
                                 
                                 /*ENVIAR OS FICHEIROS PÓ SS*/
                                 
			
				                 if(tudoOK){
                                    outToClient.writeBytes("AWC ok\n");
                                    listaConteudos.addFicheiro(_nomeFicheiro);
                                    System.out.println("Ficheiro recebido com sucesso");
                                 }
                                 
                            }

                        }catch(FileNotFoundException fnf){
                            System.out.println("Problema a criar Ficheiro\n" + "Problema TCPServer.java:89");
                        }catch (ConnectException e) {
                            outToClient.writeBytes("AWC nok\n");
                            System.err.println("Por favor lance o(s) SS(s), visto que a(a) ligação(ões) foi/foram negada(s)");
                        }catch(EOFException eoef){
                            System.err.println("O stream do cliente foi interrompido, como tal o ficheiro não foi adicionado");
                        }catch(IOException ex){
                            System.out.println("Problema TCPServer.java:91" + ex);
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


























