package ConexionServCli.DTO;

import java.util.List;

/**
 * DTO que representa los datos del tablero para el cliente.
 */
public class DatosTablero {
    private int filas;
    private int columnas;
    private List<DatosCelda> celdasOcupadas;

    public DatosTablero(int filas, int columnas, List<DatosCelda> celdasOcupadas) {
        this.filas = filas;
        this.columnas = columnas;
        this.celdasOcupadas = celdasOcupadas;
    }

    // Getters y Setters
    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public List<DatosCelda> getCeldasOcupadas() {
        return celdasOcupadas;
    }

    public void setCeldasOcupadas(List<DatosCelda> celdasOcupadas) {
        this.celdasOcupadas = celdasOcupadas;
    }
}
