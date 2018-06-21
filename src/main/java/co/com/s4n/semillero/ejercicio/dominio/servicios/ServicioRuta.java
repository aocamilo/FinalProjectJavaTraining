package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Entrega;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Ruta;
import io.vavr.collection.List;

import java.util.ArrayList;

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


    public static List<Ruta> partirRutas(List<Entrega> entregas){
        Dron d = new Dron();
        Entrega[] acc = new Entrega[d.getCapacidad()];
        java.util.List<Ruta> res= new ArrayList<Ruta>();
        for (int i = 0; i< entregas.size(); i=i+3){
            for (int j=0; j < d.getCapacidad(); j++){
                if (i+j <entregas.size()){
                    acc[j] = entregas.get(j+i);
                }else{
                    acc[j] = new Entrega(List.of());
                }
            }
            res.add((new Ruta(List.of(acc))));
        }
        return res.stream().collect(List.collector());
    }

}
