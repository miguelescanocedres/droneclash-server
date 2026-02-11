package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Enums.EstadoUnidad;

public abstract class PortaDrones extends Unidad {
    protected int vida;

    public PortaDrones(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        combustibleMaximo = 4;
        recargaPorTurno = 2;
        combustibleActual = combustibleMaximo;
        estado = EstadoUnidad.EN_VUELO;
    }

    public void RecibirImpacto() {
        vida--;
        if (vida <= 0) {
            estado = EstadoUnidad.DESTRUIDO;
        }
    }

    public boolean EstaDestruido() {
        return estado == EstadoUnidad.DESTRUIDO;
    }

    public int getVida() { return vida; }
}
