package com.example.droneclashserver.service;

import com.example.droneclashserver.model.*;
import LogicaNegocio.Clases.ControlJuego.MotorJuego;
import LogicaNegocio.Clases.ControlJuego.Partida;
import LogicaNegocio.Clases.ClasesAuxiliares.Posicion;
import LogicaNegocio.Clases.ObjetosJuego.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameService {

    private MotorJuego motorJuego;
    private static final int FILAS = 40;
    private static final int COLUMNAS = 40;

    public GameService() {
        initializeGame();
    }

    private void initializeGame() {
        motorJuego = new MotorJuego();
        Partida partida = motorJuego.getPartidaActual();

        Jugador jugadorA = new Jugador("playerA", "Player A");

        // PortaDrones Aereo en celda (5, 5)
        Posicion posPorta = new Posicion(5, 5);
        PortaDronesAereo porta = new PortaDronesAereo(posPorta, jugadorA);
        partida.getTablero().colocarUnidad(porta, posPorta);
        partida.registrarUnidad(porta);

        // Dron Aereo en celda (7, 7)
        Posicion posDron = new Posicion(7, 7);
        DronAereo dron = new DronAereo(posDron, jugadorA);
        partida.getTablero().colocarUnidad(dron, posDron);
        partida.registrarUnidad(dron);
    }

    public GameState processAction(GameAction action) {
        switch (action.getAction()) {
            case "moveDrone":
                boolean droneMoved = motorJuego.procesarMoverDron(
                        action.getDroneId(),
                        action.getTargetX(),
                        action.getTargetY()
                );
                System.out.println(droneMoved ? "Dron movido" : "Movimiento de dron rechazado");
                break;

            case "movePortaDrone":
                boolean portaMoved = motorJuego.procesarMoverPortaDrones(
                        action.getPortaDroneId(),
                        action.getTargetX(),
                        action.getTargetY()
                );
                System.out.println(portaMoved ? "PortaDron movido" : "Movimiento de portadron rechazado");
                break;

            default:
                System.out.println("Accion no implementada: " + action.getAction());
        }

        return getGameState();
    }

    public GameState getGameState() {
        return convertToGameState();
    }

    private GameState convertToGameState() {
        GameState gs = new GameState();
        Partida partida = motorJuego.getPartidaActual();

        gs.setTurn(partida.getTurno());
        gs.setGameState("playing");
        gs.setWinner(null);

        List<DroneData> drones = new ArrayList<>();
        List<PortaDroneData> portaDrones = new ArrayList<>();
        List<CeldaData> celdasOcupadas = new ArrayList<>();

        for (Unidad u : partida.getUnidadesPorId().values()) {
            if (u instanceof Dron) {
                Dron d = (Dron) u;
                DroneData dd = new DroneData();
                dd.setId(d.getId());
                dd.setPortaDroneTeam("A");
                dd.setCargo(d instanceof DronAereo ? "bomb" : "missile");
                dd.setX(d.getPosicion().getX());
                dd.setY(d.getPosicion().getY());
                dd.setFuel(d.getCombustibleActual());
                dd.setVisionRange(d.getVisionRango());
                dd.setActive(true);
                drones.add(dd);

                celdasOcupadas.add(new CeldaData(
                        d.getPosicion().getY(), d.getPosicion().getX(),
                        true, d.getId()));

            } else if (u instanceof PortaDrones) {
                PortaDrones pd = (PortaDrones) u;
                PortaDroneData pdd = new PortaDroneData();
                pdd.setId(pd.getId());
                pdd.setTeam("A");
                pdd.setType(pd instanceof PortaDronesAereo ? "aerial" : "naval");
                pdd.setX(pd.getPosicion().getX());
                pdd.setY(pd.getPosicion().getY());
                pdd.setHealth(pd.getVida());
                pdd.setVisionRange(pd.getVisionRango());
                portaDrones.add(pdd);

                celdasOcupadas.add(new CeldaData(
                        pd.getPosicion().getY(), pd.getPosicion().getX(),
                        true, pd.getId()));
            }
        }

        gs.setDrones(drones);
        gs.setPortaDrones(portaDrones);
        gs.setTablero(new TableroData(FILAS, COLUMNAS, celdasOcupadas));

        if (!drones.isEmpty()) {
            gs.setActiveUnitId(drones.get(0).getId());
        }

        return gs;
    }
}