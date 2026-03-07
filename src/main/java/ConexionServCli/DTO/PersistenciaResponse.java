package ConexionServCli.DTO;

public class PersistenciaResponse {
    private String msg;
    private Boolean exito;
    private long idPartida;

    public PersistenciaResponse(String msg, Boolean exito,long idPartida) {
        this.msg = msg;
        this.exito = exito;
        this.idPartida = idPartida;
    }
}
