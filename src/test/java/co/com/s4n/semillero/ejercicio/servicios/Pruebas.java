package co.com.s4n.semillero.ejercicio.servicios;


import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import co.com.s4n.semillero.ejercicio.dominio.entidades.Entrega;
import co.com.s4n.semillero.ejercicio.dominio.vo.Accion;
import co.com.s4n.semillero.ejercicio.dominio.vo.Posicion;
import io.vavr.collection.List;
import io.vavr.concurrent.Future;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Stream;

import static co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioEntrega.convertirLineaAEntrega;
import static co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioEntrega.string2Accion;
import static co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioRuta.linea2Entrega;
import static co.com.s4n.semillero.ejercicio.dominio.servicios.Servicios.escribirArchivo;
import static co.com.s4n.semillero.ejercicio.dominio.servicios.Servicios.leerArchivo;
import static co.com.s4n.semillero.ejercicio.dominio.servicios.ServicioDron.*;
import static io.vavr.control.Try.success;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(JUnitPlatform.class)
public class Pruebas {
    @Test
    public void pruebaDron(){
        Future<Dron> dron = Future.of(()-> new Dron());

        Future<Dron> dronR = dron
                .map(x -> moverAdelante(x))
                .map(x -> moverAdelante(x))
                .map(x -> moverAdelante(x))
                .map(x -> moverAdelante(x))
                .map(x -> girarIzquierda(x))
                .map(x -> moverAdelante(x))
                .map(x -> moverAdelante(x))
                .map(x -> girarDerecha(x));

        Future<Dron> dronR2 = dronR
                .map(x -> girarDerecha(x))
                .map(x -> girarDerecha(x))
                .map(x -> moverAdelante(x))
                .map(x -> girarIzquierda(x))
                .map(x -> moverAdelante(x))
                .map(x -> girarDerecha(x));

        Future<Dron> dronR3 = dronR2
                .map(x -> moverAdelante(x))
                .map(x -> moverAdelante(x))
                .map(x -> girarIzquierda(x))
                .map(x -> moverAdelante(x))
                .map(x -> girarDerecha(x))
                .map(x -> moverAdelante(x))
                .map(x -> girarDerecha(x));
        // Dron1
        assertEquals(dronR.getOrElse(new Dron()).getX(), -2);
        assertEquals(dronR.getOrElse(new Dron()).getY(), 4);
        assertEquals(dronR.getOrElse(new Dron()).getDir(), Posicion.Norte);
        // Dron2
        assertEquals(dronR2.getOrElse(new Dron()).getX(), -1);
        assertEquals(dronR2.getOrElse(new Dron()).getY(), 3);
        assertEquals(dronR2.getOrElse(new Dron()).getDir(), Posicion.Sur);
        // Dron3
        assertEquals(dronR3.getOrElse(new Dron()).getX(), 0);
        assertEquals(dronR3.getOrElse(new Dron()).getY(), 0);
        assertEquals(dronR3.getOrElse(new Dron()).getDir(), Posicion.Oeste);

    }

    @Test
    public void pruebaLeerArchivo(){
        Try<Stream<String>> streams = leerArchivo("./src/main/resources/in.txt");
        io.vavr.collection.List<String> leido = streams.get().collect(io.vavr.collection.List.collector());
    }

    @Test
    public void pruebaEscribirArchivo() throws IOException {
        Try<String> res = escribirArchivo("== Reporte de entregas ==", "./src/main/resources/out.txt");
        System.out.println(res.get());
    }

    @Test
    public void pruebaDeterminarAcciones(){
        Dron d = new Dron();
        Dron dronR = determinarAcciones("AAAAIAAD", d);
        Dron dronR2 = determinarAcciones("DDAIAD", dronR);
        Dron dronR3 = determinarAcciones("AAIADAD", dronR2);
        assertTrue(dronR.toString().equals("-2/4/Norte"));
        assertTrue(dronR2.toString().equals("-1/3/Sur"));
        assertTrue(dronR3.toString().equals("0/0/Oeste"));
    }

    @Test
    public void pruebaAFold(){
        io.vavr.collection.List<String> rutas = io.vavr.collection.List.of("A", "A", "A", "A", "I", "A", "A", "D");
        Dron d = new Dron();

        Dron ensayo = ensayo(rutas, d);

        System.out.println("("+ ensayo.getX()+", "+ ensayo.getY()+") posición "+ensayo.getDir());

    }

    @Test
    public void pruebaAEntrega(){
        Entrega e = new Entrega(List.of(Accion.A, Accion.A, Accion.A, Accion.A, Accion.I));
        String expected = List.of("A", "A", "A", "A", "I").toString();
        assertEquals(e.toString(), expected);
    }

    @Test
    public void pruebaAString2Accion(){
        Try<Accion> p1 = string2Accion("A");
        Try<Accion> p2 = string2Accion("*");

        assertEquals(p1, success(Accion.A));
        assertTrue(p2.isFailure());
    }

    @Test
    public void pruebaLinea2Entrega(){
        Entrega expected = new Entrega(List.of(Accion.A, Accion.A, Accion.A, Accion.A, Accion.I));
        List<String> l = List.of("A", "A", "A", "A", "I");
        Entrega entrega = convertirLineaAEntrega(l);
        assertEquals(expected.toString(), entrega.toString());
    }

    @Test
    public void convertirEntregas(){
        Try<Stream<String>> streams = leerArchivo("./src/main/resources/in.txt");
        io.vavr.collection.List<String> leido = streams.get().collect(io.vavr.collection.List.collector());

        List<Entrega> entregas = linea2Entrega(leido);

        System.out.println(entregas);
    }

}
