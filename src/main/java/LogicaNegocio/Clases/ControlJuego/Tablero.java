package LogicaNegocio.Clases.ControlJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.Unidad;

public class Tablero {
    public static final int FILAS = 40;
    public static final int COLUMNAS = 40;
    private Celda[][] grilla;

    public Tablero() {
        grilla = new Celda[FILAS][COLUMNAS];
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                grilla[i][j] = new Celda(i, j);
            }
        }
    }

    public Celda getCelda(int fila, int columna) {
        if (!estaDentroDelTablero(fila, columna)) {
            return null;
        }
        return grilla[fila][columna];
    }

    public boolean estaDentroDelTablero(int fila, int columna) {
        return fila >= 0 && fila < FILAS && columna >= 0 && columna < COLUMNAS;
    }

    public boolean esPosicionValida(Posicion pos) {
        return estaDentroDelTablero(pos.getX(), pos.getY())
                && !grilla[pos.getX()][pos.getY()].estaOcupada();
    }

    public boolean colocarUnidad(Unidad unidad, Posicion pos) {
        if (!esPosicionValida(pos)) {
            return false;
        }
        grilla[pos.getX()][pos.getY()].colocarUnidad(unidad);
        return true;
    }

    public void moverUnidad(Unidad unidad, Posicion origen, Posicion destino) {
        grilla[origen.getX()][origen.getY()].removerUnidad();
        grilla[destino.getX()][destino.getY()].colocarUnidad(unidad);
    }

    public Celda[][] getGrilla() { return grilla; }
}
