package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;
import LogicaNegocio.Clases.ControlJuego.ReglasJuego;
import LogicaNegocio.Enums.TipoArma;

public class DronNaval extends Dron {

    public DronNaval(Posicion posicion, Equipo equipo) {
        super(posicion, equipo);
        tipoArma = TipoArma.MISIL;
        municion = 2;
        visionRango = (int) ((int) ReglasJuego.VisionBase / ReglasJuego.FOVMisil);
    }

}
