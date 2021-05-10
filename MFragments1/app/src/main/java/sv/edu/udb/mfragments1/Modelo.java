package sv.edu.udb.mfragments1;


import java.io.Serializable;

public class Modelo implements Serializable {
    private String nombre;
    private int horas;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
}
