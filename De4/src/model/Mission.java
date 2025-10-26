package model;

import exception.InvalidException;

import javax.naming.InvalidNameException;

public class Mission {
    private int id;
    private String name;
    private int durability;
    private int timeRemaining;
    private int sciencePoint;

    public Mission(int id, String name, int timeRemaining) throws InvalidException {
        this.id = id;
        this.name = name;
        this.durability = 0;
        this.timeRemaining = timeRemaining;
        if (timeRemaining < 0) {
            throw new InvalidException("Invalid time remaining");
        }
        this.sciencePoint = 0;
    }

    public boolean isCompleted() {
        return timeRemaining <= 0;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public int getDurability() { return durability; }
    public void setDurability(int durability) { this.durability = durability; }
    public int getTimeRemaining() { return timeRemaining; }
    public void setTimeRemaining(int timeRemaining) { this.timeRemaining = timeRemaining; }
    public int getSciencePoint() { return sciencePoint; }
    public void setSciencePoint(int sciencePoint) { this.sciencePoint = sciencePoint; }
}
