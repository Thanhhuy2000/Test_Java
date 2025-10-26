package model;

public class Dinosaur {
    private String name;
    private int health;
    private int dangerLevel;

    public Dinosaur(String name, int health, int dangerLevel) {
        this.name = name;
        this.health = health;
        this.dangerLevel = dangerLevel;
    }

    public boolean isStable() {
        if(this.health > 50 && this.dangerLevel < 5) {
            return true;
        }
        return false;
    }

    public String getName() { return name; }
    public int getHealth() { return health; }
    public void setHealth(int health) { this.health = health; }
    public int getDangerLevel() { return dangerLevel; }
    public void setDangerLevel(int level) { this.dangerLevel = level; }
}
