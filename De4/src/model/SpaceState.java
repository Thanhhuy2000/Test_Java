package model;

import java.util.*;

public class SpaceState {
    private List<Team> teams;
    private int currenTurn;
    private List<String> log;

    public SpaceState() {
        this.teams = new ArrayList<>();
        this.currenTurn = 1;
        this.log = new ArrayList<>();
    }

    public void addTeam(Team team) {
        this.teams.add(team);
    }

    public boolean checkEnd() {
        if(currenTurn > 16) { return true; }
        for(Team team : teams) {
            for(Astronaut e : team.getAstronauts()) {
                if(!e.canPerform()) { return true; }
            }
        }
        return false;
    }

    public boolean noEnd() {
        for(Team team: teams) {
            for(Mission mission : team.getMissions()) {
                if(!mission.isCompleted()) { return false; }
            }
        }
        return true;
    }

    public void addLog(String log) {
        this.log.add(log);
    }

    public void nextTurn() {
        this.currenTurn += 1;
    }

    public void switchTurn() {
        Team tmp = teams.get(0);
        teams.set(0, teams.get(1));
        teams.set(1, tmp);
    }

    public List<Team> getTeams() { return teams; }
    public int getCurrenTurn() { return currenTurn; }
    public List<String> getLog() { return log; }
}
