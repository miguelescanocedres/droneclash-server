package LogicaNegocio.Excepciones;

/**
 * Una excepción personalizada para representar cualquier violación
 * de las reglas del juego durante el procesamiento de una acción.
 */
public class ReglaJuegoException extends Exception {

    public ReglaJuegoException(String mensaje) {
        // Llama al constructor de la clase base (Exception) para guardar el mensaje de error.
        super(mensaje);
    }
}
