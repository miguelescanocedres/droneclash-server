

package ConexionServCli.DTO;

public class DatosHud {
    private String turnoActual;
    private int municionDron;
    private String equipoDron;


    public DatosHud(String turnoActual, int municionDron, String equipoDron) {
        this.turnoActual = turnoActual;
        this.municionDron = municionDron;
        this.equipoDron = equipoDron;
    }

    public String getTurnoActual() { return turnoActual; }
    public void setTurnoActual(String turnoActual) { this.turnoActual = turnoActual; }
    public int getMunicionDron() { return municionDron; }
    public void setMunicionDron(int municionDron) { this.municionDron = municionDron; }
    public String getEquipoDron() { return equipoDron; }
    public void setEquipoDron(String equipoDron) { this.equipoDron = equipoDron; }
}