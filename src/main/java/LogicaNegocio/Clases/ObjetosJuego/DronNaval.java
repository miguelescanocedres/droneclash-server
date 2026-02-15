package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.ReglasJuego;
import LogicaNegocio.Enums.TipoArma;

public class DronNaval extends Dron {

    public DronNaval(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        tipoArma = TipoArma.MISIL;
        municion = 2;
        visionRango = (int) ((int) ReglasJuego.VisionBase / ReglasJuego.FOVMisil);
    }

}
