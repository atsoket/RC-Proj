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
