package co.com.s4n.semillero.ejercicio.dominio.entidades;

import co.com.s4n.semillero.ejercicio.dominio.vo.Posicion;

public class Dron {

    private int x;
    private int y;
    private Posicion dir;

    public Dron(){
        x= 0;
        y= 0;
        dir = Posicion.Norte;
    }

    public Dron(int x, int y, Posicion dir){
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Posicion getDir(){
        return dir;
    }

    public String toString(){
        return (x+"/"+y+"/"+ dir);
    }

}
