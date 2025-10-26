package model;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Staff> staffs;
    private List<Dinosaur> dinosaurs;
    private int saveScore;

    public Team(String name) {
        this.name = name;
        this.staffs = new ArrayList<Staff>();
        this.dinosaurs = new ArrayList<Dinosaur>();
        this.saveScore = 0;
    }

    public void addStaff(Staff staff) {
        this.staffs.add(staff);
    }

    public void inforStaff() {
        for(Staff staff : this.staffs) {
            System.out.println(staff.getId() + ". " + staff.getName() + "(" + staff.getRole() + "), stamina: " + staff.getStamina());
        }
    }

    public void addDinosaur(Dinosaur dinosaur) {
        this.dinosaurs.add(dinosaur);
    }

    public void inforDinosaur() {
        int tmp = 1;
        for(Dinosaur dinosaur : this.dinosaurs) {
            if(dinosaur.isStable()) {
                continue;
            }
            System.out.println(tmp + ". " + dinosaur.getName() + ", health: " + dinosaur.getHealth() + ", danger level: " + dinosaur.getDangerLevel());
            tmp++;
        }
    }

    public String getName() { return this.name; }
    public List<Dinosaur> getDinosaurs() { return dinosaurs; }
    public List<Staff> getStaffs() { return staffs; }
    public int getSaveScore() { return saveScore; }
    public void setSaveScore(int score) { this.saveScore = score; }
}
