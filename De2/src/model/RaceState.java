package model;

import java.util.List;

public class RaceState {
    private int currentRound;
    private int maxRounds;
    private List<Team> teams;
    private List<Obstacle> obstacles;

    public RaceState(List<Team> teams, List<Obstacle> obstacles, int maxRounds) {
        this.teams = teams;
        this.obstacles = obstacles;
        this.maxRounds = maxRounds;
        this.currentRound = 1;
    }

    public int getCurrentRound() { return currentRound; }
    public void nextRound() { currentRound++; }
    public int getMaxRounds() { return maxRounds; }
    public List<Team> getTeams() { return teams; }
    public List<Obstacle> getObstacles() { return obstacles; }
}
