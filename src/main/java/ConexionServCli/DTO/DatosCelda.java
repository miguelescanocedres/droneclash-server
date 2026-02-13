package ConexionServCli.DTO;

/**
 * DTO que representa una celda ocupada en el tablero.
 */
public class DatosCelda {
    private int fila;
    private int columna;
    private boolean ocupada;
    private String idUnidad;

    public DatosCelda(int fila, int columna, boolean ocupada, String idUnidad) {
        this.fila = fila;
        this.columna = columna;
        this.ocupada = ocupada;
        this.idUnidad = idUnidad;
    }

    // Getters y Setters
    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String getIdUnidad() {
        return idUnidad;
    }

    public void setIdUnidad(String idUnidad) {
        this.idUnidad = idUnidad;
    }
}
