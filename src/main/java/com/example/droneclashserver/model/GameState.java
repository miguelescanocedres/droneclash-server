package com.example.droneclashserver.model;

import java.util.List;

public class GameState {
    private int turn;
    private String activeUnitId;
    private String gameState; // "waiting" | "playing" | "finished"
    private String winner;
    private List<PortaDroneData> portaDrones;
    private List<DroneData> drones;

    // Getters and Setters
    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public String getActiveUnitId() {
        return activeUnitId;
    }

    public void setActiveUnitId(String activeUnitId) {
        this.activeUnitId = activeUnitId;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public List<PortaDroneData> getPortaDrones() {
        return portaDrones;
    }

    public void setPortaDrones(List<PortaDroneData> portaDrones) {
        this.portaDrones = portaDrones;
    }

    public List<DroneData> getDrones() {
        return drones;
    }

    public void setDrones(List<DroneData> drones) {
        this.drones = drones;
    }

    private TableroData tablero;

    public TableroData getTablero() {
        return tablero;
    }

    public void setTablero(TableroData tablero) {
        this.tablero = tablero;
    }
}
