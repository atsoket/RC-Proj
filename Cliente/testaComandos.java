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
        
            _comandoCortado = _comandoOriginal.trim().replaceAll("\\s+", " ");
                
            if( _comandoCortado.length() != _comandoOriginal.length() ){
             System.out.println("\n" + _comandoCortado.length() + "\n" + _comandoOriginal.length());
			    _comandoOriginal = _comandoCortado = null;
			    _comandoBuffer = null;
			    return false;
	        }
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
