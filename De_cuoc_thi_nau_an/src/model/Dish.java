package model;

public class Dish {
    private int id;
    private String name;
    private int quality; // 0-100
    private int prepTime;
    private int presentation; // 0-100
    private int score;
    private int bonus;

    public Dish(int id, String name, int prepTime, int bonus) {
        this.id = id;
        this.name = name;
        this.quality = 0;
        this.prepTime = prepTime;
        this.presentation = 0;
        this.score = 0;
        this.bonus = bonus;
    }

    public int calculateScore() {
        return this.score = (quality + presentation) / 2;
    }
    public boolean isCompleted() {
        return prepTime <= 0;
    }
    public void setQuality(int quality) {
        this.quality = quality;
    }
    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }
    public void setPresentation(int presentation) {
        this.presentation = presentation;
    }

    public int getId() { return id;}
    public String getName() { return name; }
    public int getQuality() { return quality; }
    public int getPrepTime() { return prepTime; }
    public int getPresentation() { return presentation; }
    public int getScore() { return score; }
    public int getBonus() { return bonus; }
}
