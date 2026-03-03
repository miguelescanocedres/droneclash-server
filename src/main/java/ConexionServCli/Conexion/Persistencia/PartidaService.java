package ConexionServCli.Conexion.Persistencia;


import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ControlJuego.Equipo;
import LogicaNegocio.Clases.ControlJuego.Partida;
import LogicaNegocio.Clases.ObjetosJuego.*;
import LogicaNegocio.Enums.EstadoUnidad;
import LogicaNegocio.Enums.TipoEquipo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PartidaService {

    private final PartidaDAO partidaDAO = new PartidaDAO();
    private final ObjectMapper mapper = new ObjectMapper();

    public long guardarPartida(Partida partida) throws Exception {

        Map<String, UnidadDTO> unidadesDTO = new HashMap<>();

        for (var entry : partida.getUnidadesPorId().entrySet()) {

            Unidad u = entry.getValue();

            UnidadDTO dto = new UnidadDTO();
            dto.setTipoClase(u.getClass().getSimpleName());
            dto.setId(u.getId());
            if(u instanceof PortaDrones) {
                PortaDrones port =  (PortaDrones) u;
                dto.setVida(port.getVida());
            }
            dto.setX(u.getPosicion().getX());
            dto.setY(u.getPosicion().getY());

            unidadesDTO.put(entry.getKey(), dto);
        }

        PartidaDTO saveDTO = new PartidaDTO(
                unidadesDTO,
                partida.getReloj().getEquipoActual().name()
        );

        String json = mapper.writeValueAsString(saveDTO);

        return partidaDAO.guardar(json.getBytes(StandardCharsets.UTF_8));
    }

    public Partida cargarPartida(long id) throws Exception {

        byte[] data = partidaDAO.cargar(id);

        String json = new String(data, StandardCharsets.UTF_8);

        PartidaDTO dto = mapper.readValue(json, PartidaDTO.class);

        return fromDTO(dto);
    }


    public PartidaDTO toDTO(Partida partida) {
        Map<String, UnidadDTO> map = new HashMap<>();

        for (Unidad u : partida.getUnidadesPorId().values()) {
            UnidadDTO dto = new UnidadDTO();
            dto.setTipoClase(u.getClass().getSimpleName());
            dto.setId(u.getId());
            dto.setEquipo(u.getEquipo().getTipoEquipo().name());
            dto.setX(u.getPosicion().getX());
            dto.setY(u.getPosicion().getY());
            dto.setEstado(u.getEstado().name());
            dto.setCombustibleActual(u.getCombustibleActual());

            if (u instanceof PortaDrones p) {
                dto.setVida(p.getVida());
            } else if (u instanceof Dron d) {
                dto.setMunicion(d.getMunicion());
            }

            map.put(dto.getId(), dto);
        }

        return new PartidaDTO(
                map,
                partida.getReloj().getEquipoActual().name()
        );
    }

    public Partida fromDTO(PartidaDTO dto) {

        Partida partida = new Partida(TipoEquipo.valueOf(dto.getEquipoActual()));

        for (UnidadDTO u : dto.getUnidades().values()) {

            TipoEquipo tipoEquipo = TipoEquipo.valueOf(u.getEquipo());
            Equipo equipoObj = (tipoEquipo == TipoEquipo.ROJO_AEREO)
                    ? partida.getEquipoRojo()
                    : partida.getEquipoAzul();

            Posicion pos = new Posicion(u.getX(), u.getY());
            Unidad unidad = crearUnidadPorTipo(u.getTipoClase(), pos, equipoObj);


            unidad.setIdPersistencia(u.getId());
            unidad.setEstado(EstadoUnidad.valueOf(u.getEstado()));
            unidad.setCombustibleActualPersistencia(u.getCombustibleActual());

            if (unidad instanceof PortaDrones p && u.getVida() != null) {
                p.setVidaPersistencia(u.getVida());
            }

            if (unidad instanceof Dron d && u.getMunicion() != null) {
                d.setMunicionPersistencia(u.getMunicion());
            }

            partida.registrarUnidad(unidad);
            partida.getTablero().colocarUnidad(unidad, pos);

            if (unidad instanceof PortaDrones p) {
                equipoObj.setPortaDrones(p);
            }
        }

        return partida;
    }

    private Unidad crearUnidadPorTipo(String tipoClase, Posicion pos, Equipo equipo) {
        return switch (tipoClase) {
            case "PortaDronesAereo" -> new PortaDronesAereo(pos, equipo);
            case "PortaDronesNaval" -> new PortaDronesNaval(pos, equipo);
            case "DronAereo"        -> new DronAereo(pos, equipo);
            case "DronNaval"        -> new DronNaval(pos, equipo);
            default -> throw new RuntimeException("Tipo de unidad desconocido: " + tipoClase);
        };
    }
}