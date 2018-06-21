package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Entrega;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Ruta;
import io.vavr.Tuple;
import io.vavr.Tuple3;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.control.Try;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioEntrega.convertirLineaAEntrega;


public class ServicioRuta {

    public static List<Entrega> linea2Entrega(List<String> lineas){
        List<Entrega> entregas = lineas.map(s -> partirLinea(s))
                .map(y -> convertirLineaAEntrega(y));
        return entregas;
    }

    public static List<String> partirLinea(String s){
        String[] split = s.split("");
        return List.of(split);
    }



    public static List<String> partirRutas(List<String> a){
        List<String> l2 = a.pop().append("0");
        List<String> l3 = l2.pop().append("0");

        Map<Integer, List<String>> tuple2s = a.groupBy(s -> 3   );

        String[] s = new String[a.size()];

        for (int i = 0 ; i < a.size(); i=i+3){
            s[i]= a.get(i)+";"+l2.get(i)+";"+l3.get(i);
        }

        List<String> res = Stream.of(s).filter(x -> x != null).collect(List.collector());

        System.out.println(tuple2s);

        return res;

    }

    public static void asignarRutas(){}

}
