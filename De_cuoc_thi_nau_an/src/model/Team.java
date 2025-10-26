package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Contestant> contestants;
    private List<Dish> dishs;
    private int scoreTeam;

    public Team(String name) {
        this.name = name;
        this.contestants = new ArrayList<Contestant>();
        this.dishs = new ArrayList<Dish>();
        this.scoreTeam = 0;
    }

    public void addContestant(Contestant contestant) {
        this.contestants.add(contestant);
    }

    public boolean getActiveContestants() {
        int num = 0;
        for (Contestant contestant : contestants) {
            if(contestant.canPerform()) { num++; }
        }
        return num > 0;
    }

    public void displayStatus() {
        System.out.println("Trang thai:");
        System.out.println(" * Doi " + this.name + ":");
        for (Dish dish : dishs) {
            System.out.println("  Mon an: [" + dish.getName() + ": Quality: " + dish.getQuality() + ", PrepTime: " + dish.getPrepTime() + ", Presentation: " + dish.getPresentation() + "]");
        }
        for (Contestant contestant : contestants) {
            System.out.println(" + " + contestant.getName() + " (" + contestant.getRole() + "): Energy: " + contestant.getEnergy());
        }
    }

    public void inforTeam() {
        System.out.println("Thanh vien cua doi " + this.name);
        for (Contestant contestant : this.contestants) {
            System.out.println("  + ID: " + contestant.getId() + " Name: " + contestant.getName() + " (" + contestant.getRole() + ")" + ": Enegry: " + contestant.getEnergy());
        }
    }

    public void addDish(Dish dish) {
        this.dishs.add(dish);
    }

    public String getName() { return this.name; }
    public List<Contestant> getContestants() { return this.contestants; }
    public List<Dish> getDishs() { return this.dishs; }
    public int getScoreTeam() { return this.scoreTeam; }
    public void setScoreTeam(int scoreTeam) { this.scoreTeam = scoreTeam; }
}
