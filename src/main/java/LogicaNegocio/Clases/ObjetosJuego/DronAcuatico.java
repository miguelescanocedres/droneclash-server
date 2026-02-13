package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.ReglasJuego;
import LogicaNegocio.Enums.TipoArma;

public class DronAcuatico extends Dron{

    public DronAcuatico(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        tipoArma = TipoArma.MISIL;
        municion = 2;
        visionRango = (int) ((int) ReglasJuego.VisionBase / ReglasJuego.FOVMisil);
    }
}
