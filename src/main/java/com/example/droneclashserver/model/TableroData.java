package com.example.droneclashserver.model;

import java.util.List;

public class TableroData {
    private int filas;
    private int columnas;
    private List<CeldaData> celdasOcupadas;

    public TableroData() {}

    public TableroData(int filas, int columnas, List<CeldaData>
            celdasOcupadas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdasOcupadas = celdasOcupadas;
    }

    public int getFilas() { return filas; }
    public void setFilas(int filas) { this.filas = filas; }

    public int getColumnas() { return columnas; }
    public void setColumnas(int columnas) { this.columnas = columnas;
    }

    public List<CeldaData> getCeldasOcupadas() { return
            celdasOcupadas; }
    public void setCeldasOcupadas(List<CeldaData> celdasOcupadas) {
        this.celdasOcupadas = celdasOcupadas; }
}
