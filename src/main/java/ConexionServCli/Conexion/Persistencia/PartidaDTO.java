package ConexionServCli.Conexion.Persistencia;

import java.util.Map;

public class PartidaDTO {
    private Map<String, UnidadDTO> unidades;
    private String equipoActual;

    public PartidaDTO() {}
    public PartidaDTO(Map<String, UnidadDTO> unidades, String equipoActual) {
        this.unidades = unidades;
        this.equipoActual = equipoActual;
    }

    public Map<String, UnidadDTO> getUnidades() { return unidades; }
    public void setUnidades(Map<String, UnidadDTO> unidades) { this.unidades = unidades; }
    public String getEquipoActual() { return equipoActual; }
    public void setEquipoActual(String equipoActual) { this.equipoActual = equipoActual; }
}
