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

        public boolean procura( String nomeFicheiro){
            int k, tamanho;
            tamanho = _listaFicheiros.size();
        
            if( tamanho == 0){
                return true;
            }else{

                for( k=0; k < tamanho ; k++){
                    if( _listaFicheiros.get(k).equals(nomeFicheiro) ){System.out.println("achou");
                        return false;}
                }

            }
            return true;

        }
        
        public int getNumFicheiros(){
            return _listaFicheiros.size();
        }
        
        public String getFicheirosLista(){
                String _resposta = "";
                int dim = _listaFicheiros.size(); /*Poupa tempo*/
                for (int i = 0; i < dim; i++) {
                    _resposta += " ";
                    _resposta += _listaFicheiros.get(i);
                }
		return _resposta;
        }

        
}

