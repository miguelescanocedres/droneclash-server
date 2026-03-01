package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Enums.TipoEquipo;

public class Jugador {
    private String id;
    private String nombre;
    private TipoEquipo equipo; //Le agregue esto que nos habiamos olvidado jeje

    public Jugador() {}

    public Jugador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public TipoEquipo getEquipo() { return equipo; }
    public void setEquipo(TipoEquipo equipo) { this.equipo = equipo; }
}
