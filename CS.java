import java.io.*; 
import java.net.*; 
import java.io.BufferedReader;
import java.io.InputStreamReader; 
//teste1

public class CS{

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
                                _udpserv.emEspera(_listaConteudos);
                        
                        }catch (SocketException e) {
                                System.out.println("Problema no UDPServer _udpserv = new UDPServer(porto);");
                        }catch(IOException ex){
                                System.out.println (ex.toString());
                                System.out.println("Problema no _udpserv.emEspera(_listaConteudos);");
                        }
                }
        }
        
        private static class threadTCP implements Runnable {
                int _porto;
                ListaFicheiros _listaConteudos;
        
                public threadTCP(int porto, ListaFicheiros listaConteudos){
                        _porto = porto;
                        _listaConteudos = listaConteudos;
                }
                
                public void run() {
                        try{
                                TCPServer _welcomeSocket = new TCPServer(_porto);
                                _welcomeSocket.emEspera();
                        
                        }catch (SocketException e) {
                                System.out.println("Problema no TCPServer _udpserv = new UDPServer(porto);"); /*TCP.java em falta*/
                        }catch(IOException ex){
                                System.out.println (ex.toString());
                                System.out.println("Problema no _udpserv.emEspera(_listaConteudos);");
                        }
                }
        }
        
        private static void correServidor(int porto) throws IOException, SocketException{
                
                ListaFicheiros _listaConteudos = new ListaFicheiros();
                
                
                
                Thread t = new Thread(new threadUDP( porto, _listaConteudos));
                Thread t1 = new Thread(new threadTCP( porto, _listaConteudos));
                
                t.start();
                t1.start();
                
        }
    
        public static void main(String args[]) throws Exception{
                if(args.length == 1){
                        System.out.println("ERR");
                }else{                       
                        if(args.length == 0)
                                correServidor(58006);                        
                        else if(args.length == 2)
                                correServidor(Integer.parseInt(args[2]));
                }
       }
} 
