package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;
import LogicaNegocio.Enums.EstadoUnidad;

import java.util.UUID;

public abstract class Unidad {
    protected String id;
    protected Posicion posicion;
    protected Equipo equipo;
    protected EstadoUnidad estado;
    protected int visionRango;
    protected int combustibleMaximo;
    protected int combustibleActual;
    protected int recargaPorTurno;

    public Unidad(Posicion posicion, Equipo equipo) {
        this.id = UUID.randomUUID().toString();
        this.posicion = posicion;
        this.equipo =   equipo;
    }

    public void RecargarTurno() {
        combustibleActual = Math.min(combustibleActual + recargaPorTurno, combustibleMaximo);
    }

    public void ConsumirCombustible() {
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
    public Equipo getEquipo() { return equipo; }
    public EstadoUnidad getEstado() { return estado; }
    public void setEstado(EstadoUnidad estado) { this.estado = estado; }
    public int getVisionRango() { return visionRango; }
    public int getCombustibleActual() { return combustibleActual; }
    public int getCombustibleMaximo() { return combustibleMaximo; }
}