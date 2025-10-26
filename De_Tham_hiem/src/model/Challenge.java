package model;

public class Challenge {
    private String name;
    private int difficulty; // 0 - 10
    private int traesureValue; // 0 - 100

    public Challenge(String name, int difficulty, int traesureValue) {
        this.name = name;
        this.difficulty = difficulty;
        this.traesureValue = traesureValue;
    }

    public boolean isOvercome(int tmp) {
        if(tmp > this.difficulty) {
            return true;
        }
        else {
            return false;
        }
    }

    public String getName() { return name; }
    public int getDifficulty() { return difficulty; }
    public int getTraesureValue() { return traesureValue; }
}
