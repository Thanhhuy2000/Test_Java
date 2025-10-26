package model;

import java.util.ArrayList;
import java.util.List;

public class ExpeditionState {
    private List<Team> teams;
    private int currentTurn;
    private List<String> log;

    public ExpeditionState() {
        this.teams = new ArrayList<>();
        this.currentTurn = 0;
        this.log = new ArrayList<>();
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public void addLog(String log) {
        this.log.add(log);
    }

    public void switchTurn() {
        Team tmp = teams.get(0);
        teams.set(0, teams.get(1));
        teams.set(1, tmp);
    }

    public boolean checkEnd() {
        if(this.currentTurn > 16) return true;
        for (Team team : teams) {
            for(Explorer explorer : team.getExplorers()) {
                if(explorer.canExplorer()) return false;
            }
        }
        return true;
    }

    public void nextTurn() {
        this.currentTurn++;
    }

    public void status() {
        System.out.println("Trang thai");
        for(Team team : teams) {
            System.out.println(" * Doi " + team.getName() + ": Diem: " + team.getScore());
            for(Explorer explorer : team.getExplorers()) {
                System.out.println(" + " + explorer.getName() + " (" + explorer.getRole() + "): Stamina:");
            }
        }
    }

    public List<Team> getTeams() { return this.teams; }
    public int getCurrentTurn() { return this.currentTurn; }
    public List<String> getLog() { return this.log; }
}
