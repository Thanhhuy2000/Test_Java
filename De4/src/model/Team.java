package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Astronaut> astronauts;
    private List<Mission> missions;
    private int score;

    public Team (String name) {
        this.name = name;
        this.astronauts = new ArrayList<Astronaut>();
        this.missions = new ArrayList<Mission>();
        this.score = 0;
    }

    public void addAstronaut(Astronaut a){
        this.astronauts.add(a);
    }

    public void addMission(Mission mission) {
        this.missions.add(mission);
    }

    public void inform() {
        System.out.println("* Team " + this.name);
        for(Astronaut e : astronauts) {
            if(!e.canPerform()) {
                continue;
            }
            System.out.println("ID: " + e.getId() + " Name: " + e.getName() + " (" + e.getRole() + ") " + " Energy: " + e.getEnergy());
        }
    }

    public void status(){
        System.out.println("Trang thai:");
        System.out.println("* Team " + this.name + " Score: " + this.score);
        for(Astronaut e : astronauts) {
            System.out.println("ID: " + e.getId() + " Name: " + e.getName() + " (" + e.getRole() + ") " + " Energy: " + e.getEnergy());
        }
        for(Mission mission : missions) {
            System.out.println(mission.getId() + ". " + mission.getName() + ": , durability: " +  mission.getDurability() + ", timeRemaining: " + mission.getTimeRemaining() + ", sciencePoint: " + mission.getSciencePoint());
        }
    }

    public String getName() { return name; }
    public List<Astronaut> getAstronauts() { return astronauts; }
    public List<Mission> getMissions() { return missions; }
    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }
}
