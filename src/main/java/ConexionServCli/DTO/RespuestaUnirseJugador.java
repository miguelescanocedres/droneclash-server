package ConexionServCli.DTO;

public class RespuestaUnirseJugador {
    private String idJugador;
    private String equipoAsignado;
    private String rolAsignado;
    private int jugadoresRojo;
    private int jugadoresAzul;

    public RespuestaUnirseJugador(String idJugador, String equipoAsignado,
                                   int jugadoresRojo,
                                  int jugadoresAzul) {
        this.idJugador = idJugador;
        this.equipoAsignado = equipoAsignado;
        this.jugadoresRojo = jugadoresRojo;
        this.jugadoresAzul = jugadoresAzul;
    }

    public String getIdJugador() { return idJugador; }
    public String getEquipoAsignado() { return equipoAsignado; }
    public String getRolAsignado() { return rolAsignado; }
    public int getJugadoresRojo() { return jugadoresRojo; }
    public int getJugadoresAzul() { return jugadoresAzul; }
}

