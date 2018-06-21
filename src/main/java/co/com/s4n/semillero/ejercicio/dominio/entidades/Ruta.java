package co.com.s4n.semillero.ejercicio.dominio.entidades;

import io.vavr.collection.List;

public class Ruta {
    private List<String> ruta;

    public Ruta(String[] s){
        this.ruta = List.of(s);
    }

    public List<String> getRuta(){
        return ruta;
    }
}
