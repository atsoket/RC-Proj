import java.io.*; 
import java.net.*; 
import java.lang.*;

public class UDPServer { 
    DatagramSocket serverSocket;
    ListaFicheiros _listaConteudos;
    
        public UDPServer(int porto)throws SocketException{
                serverSocket = new DatagramSocket(porto);
        }

        public void emEspera(ListaFicheiros listaConteudos) throws IOException{
             byte[] receiveData = new byte[1024];
             byte[] sendData = new byte[1024];
             _listaConteudos = listaConteudos;
             
             System.out.println(InetAddress.getLocalHost());
             
             while(true){
                   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
                   
                 
                   serverSocket.receive(receivePacket);
                   
                   
                   
                   String sentence = new String( receivePacket.getData()); //tem de ser maiusculas

                   InetAddress IPAddress = receivePacket.getAddress();
                   int port = receivePacket.getPort();
                   
                   System.out.println("RECEIVED: " + sentence + " FROM: " + receivePacket.getAddress().getHostAddress()+ " PORT: " + port);
                   
                   if( sentence.contains("LST\n")){
                       /*AWL ip porta num  f1 f2*/
                       
                       String resposta = "AWL 192.0.1.60 59000 2 f1 f2\n";
                       /*resposta += sserver.getIP();
                       resposta += sserver.getPorto();   //BORA BRUNO
                       resposta += getFicheirosNum();
                       resposta += getFicheirosLista();*/
                       sendData = resposta.getBytes();
                       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                       serverSocket.send(sendPacket);
                   }
                        
                   
                   
                }
       }
}










