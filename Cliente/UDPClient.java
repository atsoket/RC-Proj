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

    public String[] emEspera(String mensagem){
       
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[mensagem.getBytes().length];
        String[] _aRetornar = {"", ""};
        sendData = mensagem.getBytes();  
        
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, _porto);
        try{
            clientSocket.send(sendPacket);        
        }catch(IOException ex){
                    System.out.println("Problema a enviar ficheiros");
        }
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        
        try{
            clientSocket.receive(receivePacket);
        }catch(IOException ex){
                    System.out.println("Problema a receber");
        }
        
        String modifiedSentence = new String(receivePacket.getData());
        System.out.print("FROM SERVER:" + modifiedSentence);     
        
        testaComandos comandos = new testaComandos();
        comandos.setComandos(modifiedSentence, modifiedSentence);      
        
        /*AWL ip porta num  f1 f2*/
        if( comandos.testaEspaco() ){
                int nr=0, i = 0;
                String[] vectorResposta = comandos.getArgs();
                if( vectorResposta.length == 1)
                    System.out.println("Não existem ficheiros disponíveis nos SS's");
               if(vectorResposta.length > 3){
                    i = Integer.parseInt(vectorResposta[3]);
                    _aRetornar[0] = vectorResposta[1];
                    _aRetornar[1] = vectorResposta[2];
                    if( i == ( vectorResposta.length - 4 ) )			
                        while(i>0){
                            System.out.println( ++nr + " - " + vectorResposta[4+nr-1]);
                            i--;
                        }
                }
        }else{
               System.out.println("ERR: A resposta ao list está mal formatada");
        }
        
            
            clientSocket.close();
            return _aRetornar;
        
    }
}
