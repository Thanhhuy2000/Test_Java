package model;

import java.util.ArrayList;
import java.util.List;

public class MatchState {
    private List<Team> teams;
    private int currentTurn;
    private int turnCount;
    private List<String> log;// turnCount <= 10

    public MatchState() {
        this.teams = new ArrayList<Team>();
        this.currentTurn = 1;
        this.turnCount = 1;
        this.log = new ArrayList<>();
    }

    public void switchTurn() {
        Team tmp = teams.get(0);
        teams.set(0, teams.get(1));
        teams.set(1, tmp);
    }
    public void addTeam(Team team) {
        this.teams.add(team);
    }
    public void addLog(String log) {
        this.log.add(log);
    }
    public void recordAction(String action) {
        this.log.add(action);
    }
    public boolean checkEndGame() {
        if (turnCount > 25) return true;
        for (Team team : this.teams) {
            if (team.getActivePlayers() == 0) {
                return true;
            }
        }
        return false;
    }
    public void turn () { this.turnCount += 1; }

    public List<Team> getTeams() { return this.teams; }
    public int getCurrentTurn() { return this.currentTurn; }
    public int getTurnCount() { return this.turnCount; }
    public List<String> getLog() { return this.log; }
    public void setTurnCount(int turnCount) { this.turnCount = turnCount; }
}
