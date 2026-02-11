package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ObjetosJuego.Unidad;

public class Celda {
    private int fila;
    private int columna;
    private boolean ocupada;
    private Unidad unidad;

    public Celda(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
        this.ocupada = false;
        this.unidad = null;
    }

    public int getFila() { return fila; }
    public int getColumna() { return columna; }

    public boolean estaOcupada() { return ocupada; }
-
    public Unidad getUnidad() { return unidad; }

    public void colocarUnidad(Unidad unidad) {
        this.unidad = unidad;
        this.ocupada = true;
    }

    public void removerUnidad() {
        this.unidad = null;
        this.ocupada = false;
    }
}