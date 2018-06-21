package co.com.s4n.semillero.ejercicio.dominio.entidades;

import co.com.s4n.semillero.ejercicio.dominio.vo.Accion;
import io.vavr.collection.List;


import java.util.stream.Stream;

public class Entrega {
    List<Accion> accions;

    public Entrega(List<Accion> l){
        this.accions = l;
    }

    public String toString(){
        return String.valueOf(accions);
    }
}
