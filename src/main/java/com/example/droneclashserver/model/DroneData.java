package com.example.droneclashserver.model;

public class DroneData {
    private String id;
    private String portaDroneTeam;
    private String cargo; // "bomb" | "missile"
    private int x;
    private int y;
    private int fuel;
    private int visionRange;
    private boolean isActive;

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPortaDroneTeam() {
        return portaDroneTeam;
    }

    public void setPortaDroneTeam(String portaDroneTeam) {
        this.portaDroneTeam = portaDroneTeam;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getVisionRange() {
        return visionRange;
    }

    public void setVisionRange(int visionRange) {
        this.visionRange = visionRange;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
