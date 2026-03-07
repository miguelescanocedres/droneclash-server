package ConexionServCli.Conexion;

import ConexionServCli.DTO.*;
import LogicaNegocio.Excepciones.ReglaJuegoException;
import ServJuego.ServicioJuego;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ConexionServCli.DTO.RespuestaEquipos;
import LogicaNegocio.Enums.TipoEquipo;

import javax.swing.plaf.multi.MultiLabelUI;


@RestController
@RequestMapping("/api/juego")
@CrossOrigin(origins = "*")

public class ControladorJuego {
    // Get, obtiene la partida del juego, el front va a llamar a este metido cada 1 o 2 segundos.
    // Es la puerta de entrada desde el front el front va a pedir
    // Get......./estado y se ejecuta lo de abajo, el estado de la partida para devolver
    @GetMapping("/estado")
    public EstadoJuego obtenerEstado(@RequestParam(required = false) String equipo) {
        TipoEquipo tipo = (equipo != null) ? TipoEquipo.valueOf(equipo) : null;
        return ServicioJuego.obtenerEstadoJuego(tipo);
    }


    // POD, el front envia la accion de juego y este la valida
    @PostMapping("/accion")
    public ResponseEntity<?> recibirAccion(@RequestBody AccionJuego accion) {
        System.out.println("Procesando acción: " + accion.getAccion());
        try {
            //    Si tiene éxito, devuelve el nuevo estado.
            EstadoJuego nuevoEstado = ServicioJuego.procesarAccion(accion);
            // Devolvemos el estado con un código  OK.
            return ResponseEntity.ok(nuevoEstado);
        } catch (ReglaJuegoException e) {
            // Si servidor no valida la accion, aca atrapa el error y lo lanza
            //Error
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/jugador")
    public ResponseEntity<?> unirseJugador(@RequestBody SolicitudUnirseJugador solicitud) {
        try {
            RespuestaUnirseJugador respuesta = ServicioJuego.unirJugador(solicitud);
            return ResponseEntity.ok(respuesta);
        } catch (ReglaJuegoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/iniciar")
    public ResponseEntity<?> iniciarPartida() {
        try {
            EstadoJuego estado = ServicioJuego.iniciarPartida();
            return ResponseEntity.ok(estado);
        } catch (ReglaJuegoException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/resultado")
    public ResponseEntity<?> obtenerResultado() {
        EstadoJuego estado = ServicioJuego.obtenerEstadoJuego(null);
        String estadoPartida = estado.getEstadoPartida();
        if (!estadoPartida.equals("FINALIZADA") && !estadoPartida.equals("EMPATE")) {
            return ResponseEntity.badRequest().body("La partida aún no ha terminado.");
        }
        String ganador = estado.getGanador() != null ? estado.getGanador() : "EMPATE";
        return ResponseEntity.ok(java.util.Map.of(
                "estadoPartida", estadoPartida,
                "ganador", ganador
        ));
    }

    @GetMapping("/equipos")
    public ResponseEntity<RespuestaEquipos> obtenerEquipos() {
        return ResponseEntity.ok(ServicioJuego.obtenerEquipos());
    }

    @GetMapping("/hud")
    public ResponseEntity<?> obtenerDatosHud(@RequestParam String idDron) {
        try {

            DatosHud datos = ServicioJuego.obtenerDatosHud(idDron);
            return ResponseEntity.ok(datos);
        } catch (ReglaJuegoException e) {

            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/combate")
    public ResponseEntity<EventoCombate> obtenerUltimoEventoCombate() {
        EventoCombate evento = ServicioJuego.obtenerUltimoEventoCombate();
        if (evento != null) {
            return ResponseEntity.ok(evento);
        } else {
            return ResponseEntity.noContent().build();
        }

    }

    @GetMapping("/guardar")
    public ResponseEntity<PersistenciaResponse> GuardarPartida(@RequestParam String idJugador){
        boolean exito;
        long id = 0;
        String msg;
        try {
            id = ServicioJuego.guardarPartida(idJugador);
            msg = "Partida guardada correctamente con id " + id;
            exito = true;
        }catch(ReglaJuegoException e){
            msg = e.getMessage();
            exito = false;
        }
        PersistenciaResponse response = new PersistenciaResponse(msg,exito,id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/cargar")
    public ResponseEntity<PersistenciaResponse> CargarPartida(@RequestParam long idPartida){
        String msg;
        boolean exito;
        try {
            ServicioJuego.cargarPartida(idPartida);
            msg = "Partida cargada con exito";
            exito = true;
        }catch(ReglaJuegoException e){
            msg = e.getMessage();
            exito = false;
        }
        PersistenciaResponse response = new PersistenciaResponse(msg,exito,idPartida);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reiniciar")
    public ResponseEntity<Boolean> reiniciarEstadoPartida() {
        ServicioJuego.ReiniciarPartida();
        return ResponseEntity.ok(true);
    }
}







