package ConexionServCli.DTO;

public class DatosJugador {
    private String id;
    private String nombre;

    public DatosJugador(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}

