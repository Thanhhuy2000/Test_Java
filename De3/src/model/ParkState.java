package model;

import java.util.ArrayList;
import java.util.List;

public class ParkState {
    private List<Team> teams;
    private int currentTurn;
    private List<String> log;

    public ParkState() {
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

    public void nextTunr() {
        this.currentTurn++;
    }

    public void switchTurn() {
        Team tmp = this.teams.get(0);
        this.teams.set(0, this.teams.get(1));
        this.teams.set(1, tmp);
    }

    public boolean checkEnd() {
        for(Team team : this.teams) {
            for(Staff staff : team.getStaffs()) {
                if(staff.canWork()) return false;
            }
        }
        return true;
    }

    public void status() {
        System.out.println("Trang thai:");
        for(Team team : this.teams) {
            System.out.println(" * Doi " + team.getName() + ": Diem: " + team.getSaveScore());
            for(Staff staff : team.getStaffs()) {
                System.out.println(" + " + staff.getName() + " (" + staff.getRole() + "): Stamina: " + staff.getStamina());
            }
        }
    }

    public List<Team> getTeams() { return this.teams; }
    public int getCurrentTurn() { return this.currentTurn; }
    public List<String> getLog() { return this.log; }
}
