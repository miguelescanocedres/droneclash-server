package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Enums.EstadoUnidad;
import LogicaNegocio.Enums.TipoEquipo;

import java.util.UUID;

public abstract class Unidad {
    protected String id;
    protected Posicion posicion;
    protected Jugador propietario;
    protected EstadoUnidad estado;
    protected int visionRango;
    protected int combustibleMaximo;// El maximo y recarga por turno, no cambia segun el tipo de unidad? Yo lo definira en las clases hijos, idem vision pero eso ya se definio
    protected int combustibleActual;
    protected int recargaPorTurno;

    public Unidad(Posicion posicion, Jugador propietario) {
        this.id = UUID.randomUUID().toString();
        this.posicion = posicion;
        this.propietario = propietario;
    }

    public void RecargarTurno() {
        combustibleActual = Math.min(combustibleActual + recargaPorTurno, combustibleMaximo);
    }

    public void ConsumirPunto() {
        if (combustibleActual > 0) {
            combustibleActual--;
        }
    }

    public boolean TieneEnergia() {
        return combustibleActual > 0;
    }

    // Getters y Setters
    public String getId() { return id; }
    public Posicion getPosicion() { return posicion; }
    public void setPosicion(Posicion posicion) { this.posicion = posicion; }
    public Jugador getPropietario() { return propietario; }
    public EstadoUnidad getEstado() { return estado; }
    public void setEstado(EstadoUnidad estado) { this.estado = estado; }
    public int getVisionRango() { return visionRango; }
    public int getCombustibleActual() { return combustibleActual; }
    public int getCombustibleMaximo() { return combustibleMaximo; }
}