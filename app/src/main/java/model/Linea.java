package model;

public class Linea {

    private String  nombre;
        private String    horario;


    public Linea() {
    }

    public Linea( String nombre, String horario) {

        this.nombre = nombre;
        this.horario = horario;
    }


    public String getNombre() {
        return nombre;
    }

    public String getHorario() {
        return horario;
    }



}
