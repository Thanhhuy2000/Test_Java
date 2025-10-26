package model;

public class Obstacle {
    private String name;
    private int difficulty;
    private int damage;

    public Obstacle(String name, int difficulty, int damage) {
        this.name = name;
        this.difficulty = difficulty;
        this.damage = damage;
    }

    public String getName() { return name; }
    public int getDifficulty() { return difficulty; }
    public int getDamage() { return damage; }

    public boolean isOvercome(Racer racer) {
        return racer.canNavigate(this);
    }
} 