package LogicaNegocio.Clases.ControlJuego;

import java.util.Timer;
import java.util.TimerTask;

/**
 * RelojJuego - Controla el tiempo de cada turno en la partida.
 * Usa un Timer de Java que corre en un hilo daemon (no bloquea el cierre de la app).
 * Cada segundo descuenta el tiempo restante, y al llegar a 0 ejecuta un callback
 * para cambiar de turno automáticamente.
 */
public class RelojJuego {

    // Tiempo por defecto de cada turno en segundos
    private static final int TIEMPO_TURNO_POR_DEFECTO = 30;

    // Tiempo configurado para cada turno (puede cambiar si se usa el constructor con parámetro)
    private int tiempoTurnoPorDefecto;

    // Segundos que quedan en el turno actual
    private int tiempoRestante;

    // Timer de Java que ejecuta la tarea periódica cada 1 segundo
    private Timer timer;

    // Indica si el reloj está corriendo
    private boolean enMarcha;

    // Acción a ejecutar cuando el tiempo llega a 0 (ej: cambiar turno)
    private Runnable alAgotarse;

    /**
     * Constructor por defecto: 30 segundos por turno.
     */
    public RelojJuego() {
        this.tiempoTurnoPorDefecto = TIEMPO_TURNO_POR_DEFECTO;
        this.tiempoRestante = tiempoTurnoPorDefecto;
        this.enMarcha = false;
    }

    /**
     * Constructor con tiempo personalizado.
     * @param segundosPorTurno duración de cada turno en segundos.
     */
    public RelojJuego(int segundosPorTurno) {
        this.tiempoTurnoPorDefecto = segundosPorTurno;
        this.tiempoRestante = tiempoTurnoPorDefecto;
        this.enMarcha = false;
    }

    /**
     * Configura el callback que se ejecuta cuando el tiempo llega a 0.
     * Esto permite que MotorJuego pase su método CambiarTurno() como acción.
     */
    public void setAlAgotarse(Runnable alAgotarse) {
        this.alAgotarse = alAgotarse;
    }

    /**
     * Inicia el conteo regresivo.
     * Crea un TimerTask que cada 1 segundo descuenta tiempoRestante.
     * Si ya está en marcha, no hace nada (evita duplicar timers).
     */
    public void iniciar() {
        if (enMarcha) {
            return;
        }
        enMarcha = true;
        // daemon=true para que el timer no impida cerrar la aplicación
        timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (tiempoRestante > 0) {
                    tiempoRestante--;
                } else {
                    detener();
                    // Ejecuta la acción configurada (ej: cambiar turno)
                    if (alAgotarse != null) {
                        alAgotarse.run();
                    }
                }
            }
        }, 1000, 1000); // espera 1s antes de empezar, repite cada 1s
    }

    /**
     * Detiene el reloj. Cancela el timer y libera recursos.
     */
    public void detener() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        enMarcha = false;
    }

    /**
     * Reinicia el reloj: resetea el tiempo al valor por defecto y arranca de nuevo.
     * Se usa cada vez que cambia el turno.
     */
    public void reiniciar() {
        detener();
        tiempoRestante = tiempoTurnoPorDefecto;
        iniciar();
    }

    // --- Getters ---

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public boolean isEnMarcha() {
        return enMarcha;
    }
}

