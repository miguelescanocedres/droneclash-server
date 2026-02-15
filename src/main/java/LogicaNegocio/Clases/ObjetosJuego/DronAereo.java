package LogicaNegocio.Clases.ObjetosJuego;

import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.ReglasJuego;
import LogicaNegocio.Enums.TipoArma;

public class DronAereo extends Dron {

    public DronAereo(Posicion posicion, Jugador propietario) {
        super(posicion, propietario);
        tipoArma = TipoArma.BOMBA;
        municion = 1;
        visionRango = ReglasJuego.VisionBase;
    }



}