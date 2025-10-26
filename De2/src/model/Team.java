package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Racer> racers;
    private int score;
    private List<Obstacle> passedObstacles;

    public Team(String name) {
        this.name = name;
        this.racers = new ArrayList<>();
        this.score = 0;
        this.passedObstacles = new ArrayList<>();
    }

    public void addRacer(Racer racer) {
        racers.add(racer);
    }

    public List<Racer> getRacers() { return racers; }
    public int getScore() { return score; }
    public void addScore(int s) { score += s; }
    public String getName() { return name; }
    public List<Obstacle> getPassedObstacles() { return passedObstacles; }
    public void addPassedObstacle(Obstacle o) { passedObstacles.add(o); }
} 