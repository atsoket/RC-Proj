import java.io.*; 
import java.net.*; 
import java.io.BufferedReader;
import java.io.InputStreamReader; 

//https://www.youtube.com/watch?v=hjpHv_ZOFWw 1:15:28

public class User{

       

       public final static int NG = 58006;
       private static String[] arrayIP;
       
       private static class threadUDP implements Runnable {
                int _porto;
		        String _servidor, _mensagem;
                        
                public threadUDP(String servidor, int porto, String mensagem){
                        _porto = porto;
                        _servidor = servidor;
                        _mensagem = mensagem;
                }
                
                public void run() {
                        try{
                                UDPClient _udpclient = new UDPClient(_porto, _servidor);
                                arrayIP = _udpclient.emEspera(_mensagem);
                        
                        }catch (SocketException e) {
                                System.out.println("Problema no UDPCLient _udpserv = new UDPServer(porto);");
                        }catch(IOException ex){
                                System.out.println (ex.toString());
                                System.out.println("Problema no _udpclientemEspera(_listaConteudos);");
                        }
                }
        }
        
        private static class threadTCP implements Runnable {
                int _porto;
		        String _servidor, _mensagem;
                
        
                public threadTCP(String servidor, int porto, String mensagem){
                        _porto = porto;
                        _servidor = servidor;
                        _mensagem = mensagem;
                }
                
                public void run() {
                        try{
                                TCPClient _TCPclient = new TCPClient(_porto, _servidor);
                                _TCPclient.emEspera(_mensagem);
                        
                        }catch (SocketException e) {
                                System.out.println("Problema no TCPCLient _TCPserv = new TCPServer(porto);");
                        }catch(IOException ex){
                                System.out.println (ex.toString());
                                System.out.println("Problema no _TCPclientemEspera(_listaConteudos);");
                        }
                }
}
        
        private static void correServidor(String servidor, int porto) throws IOException, SocketException{
                
                
                boolean acabou = false;
                testaComandos comandos = new testaComandos();
                String[] argumentos;
                
                int[] SS;
                
                while( !acabou ){
                        /*JAVA 7
                        if(comandos.testa()){ //COMANDOS VÁLIDOS
                                
                                switch (comandos.getTipo()) {
                                case "list":
                                    Thread udp = new Thread(new threadUDP(servidor, porto, "LST\n" ));
                                    udp.start();
                                    System.out.println("comando LST");
                                   
                                    break;
                                case "retrieve": 
                                    argumentos = comandos.getArgs();
                                    Thread tcpR = new Thread( new threadTCP(arrayIP[0], Integer.parseInt(arrayIP[1]), ("retrieve " + argumentos[1])));
                                    tcpR.start();
                                    System.out.println("comando REQ");
                                    //lançar TCP + args[1]
                                    break;
                                case "upload": 
                                    argumentos = comandos.getArgs();
                                    Thread tcpU = new Thread( new threadTCP(servidor, porto, argumentos[1]  ));
                                    tcpU.start();
                                    System.out.println("comando UPL");
                                    //lançar TCP + args[1]
                                    break;
                                case "exit":
                                    acabou = true;
                                    break;
                                default: 
                                    System.out.println("O comando não é reconhecido.");
                                    break;
                                }*/
                              if(comandos.testa()){ //COMANDOS VÁLIDOS
                                
                                switch (comandos.getTipoJava6()) {
                                case 1:
                                    Thread udp = new Thread(new threadUDP(servidor, porto, "LST\n" ));
                                    udp.start();
                                    System.out.println("comando LST");
                                   
                                    break;
                                case 2: 
                                    argumentos = comandos.getArgs();
                                    Thread tcpR = new Thread( new threadTCP(arrayIP[0], Integer.parseInt(arrayIP[1]), ("retrieve " + argumentos[1])));
                                    tcpR.start();
                                    System.out.println("comando REQ");
                                    //lançar TCP + args[1]
                                    break;
                                case 3: 
                                    argumentos = comandos.getArgs();
                                    Thread tcpU = new Thread( new threadTCP(servidor, porto, ("upload " +argumentos[1])  ));
                                    tcpU.start();
                                    /*String val = "";
                                    for( int z =200; z<250; z++){
                                        val = Integer.toString(z);
                                        val += ".jpg";
                                         tcpU = new Thread( new threadTCP(servidor, porto, ("upload " +val)  ));
                                    tcpU.start();
                                        
                                }*/
                                    //lançar TCP + args[1]
                                    break;
                                case 4:
                                    acabou = true;
                                    break;
                                default: 
                                    System.out.println("O comando não é reconhecido.");
                                    break;
                                }  
                        }else{//COMANDOS INVÁLIDOS
                                System.out.println("O comando introduzido contém espaços a mais!");
                        }      
                }
               
                     
       }
        
        private static boolean testaInteiro(String argumento){
                try { 
                        Integer.parseInt(argumento); 
                }catch(NumberFormatException e) {
                        System.out.println("O número da porta terá de ser um inteiro");
                        return false; 
                }
                return true;
        }
        
        
    // user -n tejo.ist.utl.pt -p 53543
        public static void main(String args[]) throws Exception{
                if(args.length == 1 || args.length == 3){
                        System.out.println("Argumento em falta");
                }else{                       
                        if(args.length == 0)
                                correServidor("localhost", NG);  
				//System.out.println(NG);                    
                        else if(args.length == 2){
                                if( args[0].equals("-n"))
                                        correServidor(args[1], NG);//Integer.parseInt(args[3])
                                else{
                                        System.out.println("Argumento mal formatado");
                                }
                        }else if(args.length == 4){
                                if( args[0].equals("-n") && args[2].equals("-p") && testaInteiro(args[3])){
                                        correServidor(args[1], Integer.parseInt(args[3]));
                                }else{
                                        System.out.println("Argumento(s) mal formatado(s)");
                                }
                        }else{
                                System.out.println("ERR");
                        }
                }
                
       }
} 
