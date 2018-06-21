package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Entrega;
import co.com.s4n.semillero.ejercicio.dominio.vo.Accion;
import io.vavr.collection.List;
import io.vavr.control.Try;


public class ServicioEntrega {
    public static Entrega convertirLineaAEntrega(List<String> lista){
        List<Accion> acciones = lista.map(m -> string2Accion(m))
                .filter(x -> x.isSuccess())
                .map(y -> y.get());
        Entrega e = new Entrega(acciones);
        return e;
    }

    public static Try<Accion> string2Accion(String s){
        switch (s){
            case "A": return Try.of(()->Accion.A);
            case "I": return Try.of(()->Accion.I);
            case "D": return Try.of(()->Accion.D);
            default: return Try.failure(new Exception("Cadena con caracteres incorrectos"));
        }
    }
}
