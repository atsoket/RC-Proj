import java.io.*; 
import java.net.*; 
import java.io.BufferedReader;
import java.io.InputStreamReader; 
//teste2
//teste brunogh

public class SS{       
        
        static final String _erro = "Deverá ser invocado do seguinte modo: java ss [-p _porto]";
        static ServerSocket SSSocket;
        
        
        private static class threadTCP extends Thread {
                
                Socket _socket;
        
                public threadTCP(Socket socket){
                        _socket = socket;
                }
                
                public void run() {
                        try{
                                TCPServer _welcome = new TCPServer(_socket);
                                _welcome.emEspera();
                        
                        }catch (SocketException e) {
                                System.out.println("SS.java problema no socket"); /*TCP.java em falta*/
                        }catch(IOException ex){
                                System.out.println (ex.toString());
                                System.out.println("SS.java problema provavel no TCPServer");
                        }
                       this.stop();
                }
        }
        
       /* private static void correServidor(int porto) throws IOException, SocketException{
                
                ListaFicheiros _listaConteudos = new ListaFicheiros();
                
                Thread t = new Thread(new threadTCP( porto, _listaConteudos));
                
                t.start();
                
        }*/
    
        public static void main(String args[]) throws Exception{
        
        boolean listening = true;
        int _porto=59000;
        
            if (args.length > 2 || (args.length % 2) == 1) {
			    System.err.println(_erro);
			    System.exit(1);
		    }

		    if (args.length == 2) {
			    if (args[0].equals("-p")) { // Se o porto é especificado
				    try {
					    _porto = Integer.parseInt(args[1]);
				    } catch (NumberFormatException e) {
					    System.err.println("Não especificou um inteiro como porto");
					    System.exit(1);
				    }
			    } else {
				    System.err.println(_erro);
				    System.exit(1);
			    }
		    }

		    try {
			    SSSocket = new ServerSocket(_porto);
		    } catch (IOException e) {
			    System.err.println("O porto escollhido já está em uso\nPrograma abortado");
			    System.exit(1);
		    }
		        while (listening) {
			        Socket socket;
			        try {
				        socket = SSSocket.accept();
				        if (socket.isConnected()) {
				            
				            threadTCP thread = new threadTCP(socket);
					        thread.start();
					    }
			        } catch (IOException e) {
				        System.err.println("erro de IO");
			        }
		        }
           }
        } 
