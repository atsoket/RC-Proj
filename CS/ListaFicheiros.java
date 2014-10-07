import java.io.*; 
import java.net.*; 

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ListaFicheiros { 
        ArrayList<String> _listaFicheiros;
        public ListaFicheiros(){
                _listaFicheiros = new ArrayList<String>();
        }
        
        public void addFicheiro( String fn){
                _listaFicheiros.add(fn);
        }
        
        public String getFicheiros(){
                String _resposta = "";
                int dim = _listaFicheiros.size(); /*Poupa tempo*/
                for (int i = 0; i < dim; i++) {
                        _resposta += Integer.toString(i + 1);
                        _resposta += " ";
			_resposta += _listaFicheiros.get(i);
			_resposta += "\n";
		}
		return _resposta;
        }

        
}

