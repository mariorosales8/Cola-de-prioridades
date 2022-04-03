
public class ColaDePrioridad<T extends Comparable<T>> {
    private class Nodo{
        public T elemento;
        public Nodo siguiente;
        public Nodo anterior;
        public int seguros;
        public int iguales;

        public Nodo(T elemento){
            this.elemento =elemento;
        }

        public Nodo(T elemento, Nodo anterior, Nodo siguiente){
            this.elemento = elemento;
            this.siguiente = siguiente;
            this.anterior = anterior;
        }
    }

    private Nodo inicio;
    private Nodo fin;
    private int longitud;


    public ColaDePrioridad() {
        this.inicio = null;
        this.fin = null;
    }

    public boolean estaVacia() {
        return inicio == null;
    }

    public void meter(T elemento){
        if(estaVacia()){
            inicio = new Nodo(elemento);
            fin = inicio;
            longitud++;
        }else{
            if(elemento.compareTo(inicio.elemento) > 0){
                inicio = new Nodo(elemento, null, inicio);
                inicio.siguiente.anterior = inicio;
                inicio.seguros = inicio.siguiente.seguros + 1;
                longitud++;
            }else if(elemento.compareTo(inicio.elemento) < 0){
                fin = new Nodo(elemento, fin, null);
                fin.anterior.siguiente = fin;
                inicio.seguros++;
                longitud++;
            }else{
                inicio.iguales++;
                inicio.seguros++;
                longitud++;
            }
        }
    }

    public T sacar(){
        if(estaVacia())
            return null;
        T aux = inicio.elemento;
        longitud--;
        if(inicio.iguales > 0){
            inicio.iguales--;
            inicio.seguros--;
        }else if(longitud == 0){
            inicio = null;
            fin = null;
        }else{
            inicio = inicio.siguiente;
            inicio.anterior.siguiente = null;
            inicio.anterior = null;
            
            int i = 0;
            Nodo finReal = null;
            boolean finCambiante = true;
            while(longitud-1 - inicio.seguros > i){
                if(fin.elemento.compareTo(inicio.elemento) < 0){
                    if(finCambiante){
                        finReal = fin;
                        finCambiante = false;
                    }
                    i++;
                    fin = fin.anterior;
                }else if(fin.elemento.compareTo(inicio.elemento) > 0){
                    //El mayor es fin
                    fin.anterior.siguiente = fin.siguiente;
                    if(fin.siguiente != null)
                        fin.siguiente.anterior = fin.anterior;
                    inicio.anterior = fin;
                    fin.siguiente = inicio;
                    fin = fin.anterior; //Cambio de fin
                    inicio.anterior.anterior = null;
                    inicio = inicio.anterior; //Cambio de inicio
                    inicio.seguros = inicio.siguiente.seguros + 1;
                }else{
                    Nodo borrar = fin;
                    fin = fin.anterior;
                    fin.siguiente = borrar.siguiente;
                    if(fin.siguiente != null)
                        fin.siguiente.anterior = fin;
                    borrar.anterior = null;
                    borrar.siguiente = null;
                    inicio.iguales++;
                    inicio.seguros++;
                }
            }
            if(!finCambiante)
                fin = finReal;
            inicio.seguros += i;
        }
        return aux;
    }

    public int getLongitud(){
        return longitud;
    }

    public T mostrar(){
        return inicio.elemento;
    }

    @Override public String toString(){
        String retorno = "";
        Nodo aux = inicio;
        int i = 0;
        while(aux != null){
            retorno = aux.elemento + " " + retorno;
            if(aux.iguales > i)
                i++;
            else{
                aux = aux.siguiente;
                i = 0;
            }
        }
        return retorno;
    }

    public void print(){
        System.out.println(toString());
    }


}
