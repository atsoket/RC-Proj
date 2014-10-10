import java.io.*; 
import java.net.*; 
import java.lang.*;
import java.util.ArrayList;
public class UDPServer { 
    DatagramSocket serverSocket;
   // ListaFicheiros _listaConteudos;
    int _porto;
    
        public UDPServer(int porto)throws SocketException{
                _porto = porto;
                serverSocket = new DatagramSocket(porto);
        }

        public void emEspera(ListaFicheiros listaConteudos, ArrayList<String> listaSS) throws IOException{
             byte[] receiveData = new byte[1024];
             byte[] sendData = new byte[1024];
            // _listaConteudos = listaConteudos;
             
             System.out.println(InetAddress.getLocalHost());
             
             while(true){
                   DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length); 
                   
                 
                   serverSocket.receive(receivePacket);
                   
                   
                   
                   String sentence = new String( receivePacket.getData()); //tem de ser maiusculas

                   InetAddress IPAddress = receivePacket.getAddress();
                   int port = receivePacket.getPort();
                   
                   
               System.out.println("+==============================+");
               System.out.printf("| Pedido: LST                  |\n");
               System.out.printf("| Origem: %-21s|\n", IPAddress);
               System.out.printf("| Porto: %-22d|\n", port);
               System.out.println("+==============================+");
                   
                   if( sentence.contains("LST\n")){
                       /*AWL ip porta num  f1 f2*/
                       
                       String resposta = "AWL ";
                        String[] aux = listaSS.get(0).split(" ");
                       resposta += aux[0];/*Aqui*/
                       resposta += " ";
                       resposta += Integer.parseInt(aux[1]);/*aqui*/
                       resposta += " ";
                       resposta += listaConteudos.getNumFicheiros();
                       if( listaConteudos.getNumFicheiros() == 0)
                            resposta = "EOF\n";
                       else{
                           resposta += listaConteudos.getFicheirosLista(); /*já mete o espaço*/
                           resposta += "\n";
                       }
                       sendData = resposta.getBytes();
                       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                       serverSocket.send(sendPacket);
                   }else{
                        String resposta = "ERR\n";
                        sendData = resposta.getBytes();
                       DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                       serverSocket.send(sendPacket);
                   }    
                        
                   
                   
                }
       }
}










