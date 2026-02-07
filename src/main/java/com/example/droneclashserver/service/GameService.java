package com.example.droneclashserver.service;

import com.example.droneclashserver.model.DroneData;
import com.example.droneclashserver.model.GameAction;
import com.example.droneclashserver.model.GameState;
import com.example.droneclashserver.model.PortaDroneData;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GameService {

    private GameState gameState;

    public GameService() {
        initializeGame();
    }

    private void initializeGame() {
        gameState = new GameState();
        gameState.setTurn(1);
        gameState.setGameState("playing");
        gameState.setWinner(null);

        List<PortaDroneData> portaDrones = new ArrayList<>();
        PortaDroneData portaDrone = new PortaDroneData();
        portaDrone.setId(UUID.randomUUID().toString());
        portaDrone.setTeam("A");
        portaDrone.setType("aerial");
        portaDrone.setX(100);
        portaDrone.setY(100);
        portaDrone.setHealth(100);
        portaDrone.setVisionRange(50);
        portaDrones.add(portaDrone);
        gameState.setPortaDrones(portaDrones);

        List<DroneData> drones = new ArrayList<>();
        DroneData drone = new DroneData();
        drone.setId(UUID.randomUUID().toString());
        drone.setPortaDroneTeam("A");
        drone.setCargo("bomb");
        drone.setX(150);
        drone.setY(150);
        drone.setFuel(100);
        drone.setVisionRange(30);
        drone.setActive(true);
        drones.add(drone);
        gameState.setDrones(drones);

        gameState.setActiveUnitId(drone.getId());
    }

    public GameState getGameState() {
        return gameState;
    }

    public GameState processAction(GameAction action) {
        // For now, just log the action and return the current state
        System.out.println("Processing action: " + action.getAction());
        // In a real implementation, you would modify the gameState based on the action
        return gameState;
    }
}
