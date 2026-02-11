package LogicaNegocio.Clases.ClasesAuxiliares;

public class Posicion {
    private int x;
    private int y;

    public Posicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

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

