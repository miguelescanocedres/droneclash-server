

package ConexionServCli.DTO;

public class DatosHud {
    private String turnoActual;
    private int municionDron;
    private String equipoDron;
    private int vidaPortaDron;
    private int movimientosDron;
    private int movimientosPortaDron;



    public DatosHud(String turnoActual, int municionDron, String equipoDron) {
        this.turnoActual = turnoActual;
        this.municionDron = municionDron;
        this.equipoDron = equipoDron;
    }

    public DatosHud(String turnoActual, int municionDron, String equipoDron,int vidaPortaDron, int movimientosDron, int movimientosPortaDron) {
        this.turnoActual = turnoActual;
        this.municionDron = municionDron;
        this.equipoDron = equipoDron;
        this.vidaPortaDron = vidaPortaDron;
        this.movimientosDron = movimientosDron;
        this.movimientosPortaDron = movimientosPortaDron;

    }

    public String getTurnoActual() { return turnoActual; }
    public void setTurnoActual(String turnoActual) { this.turnoActual = turnoActual; }
    public int getMunicionDron() { return municionDron; }
    public void setMunicionDron(int municionDron) { this.municionDron = municionDron; }
    public String getEquipoDron() { return equipoDron; }
    public void setEquipoDron(String equipoDron) { this.equipoDron = equipoDron; }
    public int getVidaPortaDron() {return vidaPortaDron;}
    public void setVidaPortaDron(int vidaPortaDron) {this.vidaPortaDron = vidaPortaDron;}
    public int getMovimientosPortaDron() { return movimientosPortaDron; }
    public void setMovimientosPortaDron(int movimientosPortaDron) { this.movimientosPortaDron = movimientosPortaDron; }
}