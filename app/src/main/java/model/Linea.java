package model;

public class Linea {

    private String  nombre;
        private String    horario;
        private String ruta;


    public Linea() {
    }

    public Linea( String nombre, String horario, String ruta) {

        this.nombre = nombre;
        this.horario = horario;
        this.ruta=ruta;
    }


    public String getNombre() {
        return nombre;
    }

    public String getHorario() {
        return horario;
    }

    public String getRuta() {
        return ruta;
    }



}
