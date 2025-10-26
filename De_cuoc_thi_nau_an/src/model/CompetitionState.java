package model;

import java.util.ArrayList;
import java.util.List;

public class CompetitionState {
    private List<Team> teams;
    private int currentTeam; //Doi dang den luot
    private int turnCount; // So luot da choi
    private List<String> log;

    public CompetitionState() {
        this.teams = new ArrayList<Team>();
        this.currentTeam = 1;
        this.turnCount = 1;
        this.log = new ArrayList<>();
    }

    public void switchTurn() {
        Team tmp = null;
        tmp = teams.get(0);
        teams.set(0, teams.get(1));
        teams.set(1, tmp);
    }

    public void recordAction(String action) {
        this.log.add(action);
    }

    public boolean checkEndCompetition() {
        if (this.turnCount > 16) { return true;}
        for (Team team : this.teams) {
            if(!team.getActiveContestants()) {
                return true;
            }
        }
        return false;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void turn() {
        this.turnCount++;
    }

    public List<Team> getTeams() { return this.teams; }
    public int getCurrentTeam() { return this.currentTeam; }
    public int getTurnCount() { return this.turnCount; }
    public List<String> getLog() { return this.log; }
}
