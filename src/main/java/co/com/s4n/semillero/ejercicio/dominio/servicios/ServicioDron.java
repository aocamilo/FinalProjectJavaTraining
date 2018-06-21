package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Entrega;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Ruta;
import co.com.s4n.semillero.ejercicio.dominio.vo.Accion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Posicion;
import io.vavr.Tuple2;
import io.vavr.control.Try;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ServicioDron {

    public static Dron moverAdelante(Dron a){
        switch (a.getDir()){
            case Norte: return new Dron(a.getX(), a.getY()+1, a.getDir());
            case Este: return new Dron(a.getX()+1, a.getY(), a.getDir());
            case Oeste: return new Dron(a.getX()-1, a.getY(), a.getDir());
            case Sur: return new Dron(a.getX(), a.getY()-1, a.getDir());
        }

        return a;
    }

    public static Dron girarIzquierda(Dron a){
        switch (a.getDir()){
            case Norte: return new Dron(a.getX(), a.getY(), Posicion.Oeste);
            case Este: return new Dron(a.getX(), a.getY(), Posicion.Norte);
            case Oeste: return new Dron(a.getX(), a.getY(), Posicion.Sur);
            case Sur: return new Dron(a.getX(), a.getY(), Posicion.Este);
        }

        return a;
    }

    public static Dron girarDerecha(Dron a){
        switch (a.getDir()){
            case Norte: return new Dron(a.getX(), a.getY(), Posicion.Este);
            case Este: return new Dron(a.getX(), a.getY(), Posicion.Sur);
            case Oeste: return new Dron(a.getX(), a.getY(), Posicion.Norte);
            case Sur: return new Dron(a.getX(), a.getY(), Posicion.Oeste);
        }

        return a;
    }

    /*public static Dron determinarAcciones(String s, Dron a){
        Stream<String> aOperar = Stream.of(s.split(""));
        List<Dron> res= new ArrayList<Dron>();
        res.add(a);

        final int [] indice = {0};
        aOperar.forEach(x -> {
            res.add(ejecutarAcciones(x, res.get(indice[0])));
            indice[0]++;
        });

        return res.get(res.size()-1);
    }*/

    public static Dron ejecutarAcciones(String s, Dron d){
        switch (s){
            case "A": return moverAdelante(d);
            case "I": return girarIzquierda(d);
            case "D": return girarDerecha(d);
        }

        return d;
    }

    public static io.vavr.collection.List<Entrega> asignarEntregas(Ruta r){
        return r.getRuta();
    }

    public static io.vavr.collection.List<Dron> entregarRuta(Tuple2<io.vavr.collection.List<Entrega>, Dron> t){
        io.vavr.collection.List<Entrega> entregas= t._1;
        List<Dron> res= new ArrayList<Dron>();
        res.add(t._2);
        final int [] indice = {0};
        entregas.forEach(x-> {
            res.add(determinarAcciones(x, res.get(indice[0])));
            indice[0]++;
        });
        return res.stream().collect(io.vavr.collection.List.collector()).tail();

    }

    public static Dron determinarAcciones(Entrega e, Dron d){
        io.vavr.collection.List<String> listaString = e.accions()
                .map(a -> String.valueOf(a));
        Dron dron = foldAcciones(listaString, d);
        return dron;

    }
    
    public static Dron foldAcciones(io.vavr.collection.List<String>s, Dron a){
        String s1 = a.toString();
        String folded = s.fold(s1, (x, y) -> operarDron(x, y));
        String[] split = folded.split("/");
        Posicion pos = null;
        Try<Posicion> posicion = determinarPosicion(split[2]);
        if (posicion.isSuccess()){
            pos = posicion.get();
        }
        Dron d = new Dron(Integer.parseInt(split[0]), Integer.parseInt(split[1]), pos);
        return d;
    }

    public static String operarDron (String s, String s2){
        String[] split = s.split("/");
        Posicion pos = null;
        Try<Posicion> posicion = determinarPosicion(split[2]);
        if (posicion.isSuccess()){
            pos = posicion.get();
        }
        Dron d = new Dron(Integer.parseInt(split[0]), Integer.parseInt(split[1]), pos);
        return ejecutarAcciones(s2, d).toString();
    }

    public static Try<Posicion> determinarPosicion(String a){
        switch (a){
            case "Norte": return Try.of(()-> Posicion.Norte);
            case "Sur": return Try.of(()-> Posicion.Sur);
            case "Este": return Try.of(()-> Posicion.Este);
            case "Oeste": return Try.of(()-> Posicion.Oeste);
            default: return Try.failure(new Exception("Posicion Incorrecta"));
        }
    }
}
