package com.example.droneclashserver.model;

public class GameAction {
    private String action; // "moveDrone" | "movePortaDrone" | "launchDrone" | "fire" | "nextTurn" | "surrender" | "requestState"
    private String playerId;
    private String droneId;
    private Integer targetX;
    private Integer targetY;
    private String portaDroneId;
    private String cargo; // "bomb" | "missile"

    // Getters and Setters
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getDroneId() {
        return droneId;
    }

    public void setDroneId(String droneId) {
        this.droneId = droneId;
    }

    public Integer getTargetX() {
        return targetX;
    }

    public void setTargetX(Integer targetX) {
        this.targetX = targetX;
    }

    public Integer getTargetY() {
        return targetY;
    }

    public void setTargetY(Integer targetY) {
        this.targetY = targetY;
    }

    public String getPortaDroneId() {
        return portaDroneId;
    }

    public void setPortaDroneId(String portaDroneId) {
        this.portaDroneId = portaDroneId;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
