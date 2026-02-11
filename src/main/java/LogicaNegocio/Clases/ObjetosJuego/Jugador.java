package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Enums.Rol;

public class Jugador {
    private String id;
    private String nombre;
    private Rol rol;

    public Jugador() {}

    public Jugador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
}