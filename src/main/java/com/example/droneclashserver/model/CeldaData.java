package com.example.droneclashserver.model;

public class CeldaData {
    private int fila;
    private int columna;
    private boolean ocupada;
    private String unidadId;

    public CeldaData() {}

    public CeldaData(int fila, int columna, boolean ocupada, String
            unidadId) {
        this.fila = fila;
        this.columna = columna;
        this.ocupada = ocupada;
        this.unidadId = unidadId;
    }

    public int getFila() { return fila; }
    public void setFila(int fila) { this.fila = fila; }

    public int getColumna() { return columna; }
    public void setColumna(int columna) { this.columna = columna; }

    public boolean isOcupada() { return ocupada; }
    public void setOcupada(boolean ocupada) { this.ocupada = ocupada;
    }

    public String getUnidadId() { return unidadId; }
    public void setUnidadId(String unidadId) { this.unidadId =
            unidadId; }
}