package co.com.s4n.semillero.ejercicio.dominio.entidades;

import io.vavr.collection.List;

public class Ruta {
    private List<Entrega> ruta;

    public Ruta(List<Entrega> entregas){
        this.ruta = entregas;
    }

    public List<Entrega> getRuta(){ return ruta; }
}
