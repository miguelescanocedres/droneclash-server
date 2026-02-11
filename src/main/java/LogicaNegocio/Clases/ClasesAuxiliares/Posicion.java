package LogicaNegocio.Clases.ClasesAuxiliares;

public class Posicion {
    private int x;
    private int y;

    public void DesplazarX (int cantidad){
        x += cantidad;
    }
    public void Desplazary (int cantidad){
        y += cantidad;
    }
    public  void DesplazarXY(int cantidadX, int cantidadY){
        this.DesplazarX(cantidadX);
        this.Desplazary(cantidadY);
    }
}
