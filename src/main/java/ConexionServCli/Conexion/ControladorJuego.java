package ConexionServCli.Conexion;

import ConexionServCli.DTO.AccionJuego;
import ConexionServCli.DTO.EstadoJuego;
import LogicaNegocio.Excepciones.ReglaJuegoException;
import ServJuego.ServicioJuego;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/juego")
@CrossOrigin(origins = "*")

public class ControladorJuego {
        // Get, obtiene la partida del juego, el front va a llamar a este metido cada 1 o 2 segundos.
        // Es la puerta de entrada desde el front el front va a pedir
        // Get......./estado y se ejecuta lo de abajo, el estado de la partida para devolver
    @GetMapping("/estado")
    public EstadoJuego obtenerEstado(){
            return ServicioJuego.obtenerEstadoJuego();
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



        }

