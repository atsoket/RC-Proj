import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader; 

public class testaComandos{

        String _comandoOriginal;
        String _comandoCortado;
        String[] _comandoArgs;
        BufferedReader _comandoBuffer;
        
        public void setComandos(String comandoOriginal, String comandoCortado){
                _comandoOriginal = comandoOriginal;
                _comandoCortado = comandoCortado;
        }
        
        public String getTipo(){
                return _comandoArgs[0];
        }
        
        public int getTipoJava6(){
                if(_comandoArgs[0].equals("list") && _comandoArgs.length == 1)
                        return 1;
                else if(_comandoArgs[0].equals("retrieve") && _comandoArgs.length == 2 )
                        return 2;
                else if(_comandoArgs[0].equals("upload") && _comandoArgs.length == 2)
                        return 3;
                else if(_comandoArgs[0].equals("exit") && _comandoArgs.length == 1)
                        return 4;         
                return -1;               
        }
        
        public String[] getArgs(){
                return _comandoArgs;
        }
        
        private void leTeclado(){
        
                _comandoBuffer =  new BufferedReader(new InputStreamReader(System.in));
        
                try{
                        _comandoOriginal = _comandoBuffer.readLine();
                }catch(IOException ex){
                        System.out.println("ERR: Problema na leitura do comando.");
                }
        
        }
        
        public boolean testaEspaco(){
        
           int tamanhoOriginal;
           _comandoCortado = _comandoOriginal.trim().replaceAll("\\s+", " ");
            
           for (tamanhoOriginal = 0; tamanhoOriginal < _comandoOriginal.length(); tamanhoOriginal++){
                  if( _comandoOriginal.charAt(tamanhoOriginal) == '\n') 
                       break;
           }
           
                                
           if( _comandoCortado.length() != tamanhoOriginal ){
                _comandoOriginal = _comandoCortado = null;
			    _comandoBuffer = null;
			    return false;
	       }
	        
	        _comandoOriginal = _comandoOriginal.trim().replaceAll("\n", "");
	        _comandoArgs = _comandoOriginal.split(" ");
	        return true;
        
        }
                
        public boolean testa(){
        
            leTeclado();
            
            if( !testaEspaco() ){
                    return false;                
            }	        
	        return true;
	    }
	       

}
