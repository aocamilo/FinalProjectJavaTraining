package co.com.s4n.semillero.ejercicio.dominio.servicios;

import co.com.s4n.semillero.ejercicio.dominio.entidades.Dron;
import io.vavr.collection.List;
import io.vavr.control.Try;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.stream.Stream;

public class Servicios{

    public static Try<Stream<String>> leerArchivo(String fn){
        Try<Stream<String>> streamTry = Try.of(()-> Files.lines((Paths.get(fn))));
        return (!streamTry.isEmpty())? streamTry:Try.failure(new Exception("Archivo no existe"));

    }

    public static void escribirArchivo(String s, String fn){
        String[] lines = new String[]{"Hola", "Funciono"};
        Try<PrintWriter> pw = Try.of(() -> new PrintWriter(fn));
        if (pw.isSuccess()){
            pw.get().println(s);
            pw.get().close();
        }
    }

    public static String organizarEscrituraEnArchivo(List<List<Dron>> list){
        String[] s= {"== Reporte de entregas == \n"};
        list.forEach(x-> {
            x.forEach(dron -> {
                s[0] += "(" + dron.getX()+", "+ dron.getY() + ") dirección " + dron.getDir()+"\n";
            });

            s[0] += "== Fin de ruta == \n";
        });

        return s[0];
    }

}
