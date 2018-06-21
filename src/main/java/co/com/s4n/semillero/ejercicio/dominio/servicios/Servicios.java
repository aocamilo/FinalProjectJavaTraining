package co.com.s4n.semillero.ejercicio.dominio.servicios;

import io.vavr.control.Try;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class Servicios{

    public static Try<Stream<String>> leerArchivo(String fn){
        Try<Stream<String>> streamTry = Try.of(()-> Files.lines((Paths.get(fn))));
        return (!streamTry.isEmpty())? streamTry:Try.failure(new Exception("Archivo no existe"));

    }

    public static Try<String> escribirArchivo(String s, String fn) throws IOException {

        Try<BufferedWriter> br = Try.of(()->Files.newBufferedWriter(Paths.get(fn), Charset.defaultCharset(), StandardOpenOption.CREATE));

        if(br.isSuccess()){
            br.get().write(s);
            return Try.success("Se accedio al archivo correctamente");
        }else {
            return Try.failure(new Exception("No se pudo acceder al archivo"));
        }

    }

}
