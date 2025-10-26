package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Explorer> explorers;
    private List<Challenge> challenges;
    private int score;

    public Team(String name) {
        this.name = name;
        this.explorers = new ArrayList<>();
        this.challenges = new ArrayList<>();
        this.score = 0;
    }

    public void addExplorer(Explorer explorer) {
        this.explorers.add(explorer);
    }

    public void addChallenge(Challenge challenge) {
        this.challenges.add(challenge);
    }

    public String getName() { return name; }
    public List<Explorer> getExplorers() { return explorers; }
    public List<Challenge> getChallenges() { return challenges; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
