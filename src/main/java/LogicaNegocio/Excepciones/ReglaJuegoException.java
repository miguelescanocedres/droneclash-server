package LogicaNegocio.Excepciones;


public class ReglaJuegoException extends Exception {

    public ReglaJuegoException(String mensaje) {
        // Llama al constructor de la clase base (Exception) para guardar el mensaje de error.
        super(mensaje);
    }
}
