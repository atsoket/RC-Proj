import java.io.*;
import java.net.*;

class UDPClient{

    int _porto;
    DatagramSocket clientSocket;
    InetAddress IPAddress;
    
    public UDPClient(int porto, String servidor)throws SocketException, java.net.UnknownHostException{
            clientSocket = new DatagramSocket();
	        IPAddress = InetAddress.getByName(servidor);
            _porto = porto;
    }

    public String[] emEspera(String mensagem) throws IOException{
       
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[mensagem.getBytes().length];
        String[] _aRetornar = {"", ""};
        sendData = mensagem.getBytes();  
        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, _porto);
        clientSocket.send(sendPacket);        
        
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        
        String modifiedSentence = new String(receivePacket.getData());
        System.out.print("FROM SERVER:" + modifiedSentence);     
        
        testaComandos comandos = new testaComandos();
        comandos.setComandos(modifiedSentence, modifiedSentence);      
        
        /*AWL ip porta num  f1 f2*/
        if( comandos.testaEspaco() ){
                int nr=0, i;
                String[] vectorResposta = comandos.getArgs();
                i = Integer.parseInt(vectorResposta[3]);
                _aRetornar[0] = vectorResposta[1];
                _aRetornar[1] = vectorResposta[2];
                if( i == ( vectorResposta.length - 4 ) )			
                    while(i>0){
                        System.out.println( ++nr + " - " + vectorResposta[4+nr-1]);
                        i--;
                    }
                
        }else{
               System.out.println("ERR");
        }
        
            
            clientSocket.close();
            return _aRetornar;
        
    }
}
