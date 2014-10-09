import java.io.*; 
import java.net.*; 
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader; 
//teste2
//teste brunogh

public class CS{

 static final String _erro = "Servidor invocado indevidamente\nPara o invocar deverá escrever: java CS [-p _porto]\nPrograma Terminado...\n\n";
 static ArrayList<String> _listaSS = new ArrayList<String>();
 
 static ServerSocket CSSocket;

        private static class threadUDP implements Runnable {
                int _porto;
                ListaFicheiros _listaConteudos;
                       
                public threadUDP(int porto, ListaFicheiros listaConteudos){
                        _porto = porto;
                        _listaConteudos = listaConteudos;
                }
                
                public void run() {
                        try{
                                UDPServer _udpserv = new UDPServer(_porto);
                                _udpserv.emEspera(_listaConteudos, _listaSS);
                        
                        }catch (SocketException e) {
                                System.out.println("Problema no UDPServer _udpserv = new UDPServer(porto);");
                        }catch(IOException ex){
                                System.out.println (ex.toString());
                                System.out.println("Problema no _udpserv.emEspera(_listaConteudos);");
                        }
                }
        }
        
        private static class threadTCP extends Thread {
                ListaFicheiros _listaConteudos;
                Socket _socket;
              
        
                public threadTCP(ListaFicheiros listaConteudos, Socket socket){
                        _listaConteudos = listaConteudos;
                        _socket = socket;
                        
                }
                
                public void run() {
                        try{
                                TCPServer _welcomeSocket = new TCPServer(_socket);
                                _welcomeSocket.emEspera( _listaConteudos, _listaSS);
                        
                        }catch (SocketException e) {
                                System.out.println("Problema no TCPServer _udpserv = new UDPServer(porto);"); /*TCP.java em falta*/
                        }catch(IOException ex){
                                System.out.println (ex.toString());
                                System.out.println("Problema no _udpserv.emEspera(_listaConteudos);");
                        }
                        this.stop();
                }
        }
        
        
        
        
    public static void readFile() throws IOException {
		try {
			BufferedReader in = new BufferedReader(new FileReader("listaSS.txt"));
			String aux;
			

			for (aux = in.readLine(); aux != null; aux = in.readLine()) {
			    System.out.println(aux);
				_listaSS.add(aux);
     		}
			in.close();
		} catch (FileNotFoundException e) {
			System.err.println("+===================================+\n|Ficheiro com os SS's não encontrado|\n|Programa Abortado                  |\n+===================================+\n\n");
			System.exit(1);
		}

	}
        
        private static void correServidor(int porto) throws IOException, SocketException{
        
                boolean listening = true;
                
                ListaFicheiros _listaConteudos = new ListaFicheiros();
                readFile();
                
                Thread t = new Thread(new threadUDP( porto, _listaConteudos));
                t.start();
                
                
                /*Thread t1 = new Thread(new threadTCP( porto, _listaConteudos));
                t1.start();*/
                
                try {
			    CSSocket = new ServerSocket(porto);
		        } catch (IOException e) {
			        System.err.println("O porto escollhido já está em uso");
		        }
		        
		        while (listening) {
			        Socket socket;
			        try {
				        socket = CSSocket.accept();
				        if (socket.isConnected()) {
				        
				            threadTCP thread = new threadTCP(_listaConteudos, socket);
					        thread.start();
					    }
			        } catch (IOException e) {
				        System.err.println("erro de IO");
			        }
		        }
                
        }
    
        public static void main(String args[]) throws Exception{
             
		    int _porto=58006;
		    
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
		    correServidor(_porto);
		    
       }
} 
