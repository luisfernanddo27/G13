package PRACTICA;

public class Paquete<T extends Comparable<T>> {

    private String codigo;
    private double peso;
    private T      prioridad;
    private int    valorizacion;
    private String zona;

    public Paquete(String codigo, double peso, T prioridad,
                   int valorizacion, String zona) {
        this.codigo       = codigo;
        this.peso         = peso;
        this.prioridad    = prioridad;
        this.valorizacion = valorizacion;
        this.zona         = zona;
    }

    public String getCodigo()       { return codigo; }
    public double getPeso()         { return peso; }
    public T      getPrioridad()    { return prioridad; }
    public int    getValorizacion() { return valorizacion; }
    public String getZona()         { return zona; }

    public int getPrioridadNumerica() {
        if (prioridad instanceof String) {
            switch (((String) prioridad).trim()) {
                case "Alta":  return 3;
                case "Media": return 2;
                case "Baja":  return 1;
                default:      return 0;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return String.format(
            "Paquete[codigo=%-8s | zona=%-2s | peso=%5.1f kg | prioridad=%-8s | valor=%d]",
            codigo, zona, peso, prioridad, valorizacion
        );
    }
}