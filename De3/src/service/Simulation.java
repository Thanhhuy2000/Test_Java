package service;

import exception.InvalidException;
import model.*;

import java.util.List;
import java.util.Scanner;

public class Simulation {
    private ParkState state;
    private Scanner scanner;

    public Simulation() {
        state = new ParkState();
        scanner = new Scanner(System.in);
    }

    public void setup() throws InvalidException {
        // Nhap thong tin nhan vien
        Team teamA = new Team("A");
        teamA.addStaff(new Scientist(1, "Minh", 100, 10));
        teamA.addStaff(new Ranger(2, "Lan", 100, 10));
        teamA.addStaff(new Medic(3, "Hung", 100, 1));
        Team teamB = new Team("B");
        teamB.addStaff(new Scientist(4, "Nam", 100, 10));
        teamB.addStaff(new Ranger(5, "Mai", 100, 10));
        teamB.addStaff(new Medic(6, "Khoa", 100, 1));
        state.addTeam(teamA);
        state.addTeam(teamB);

        // Nhap thong tin khung long
        teamA.addDinosaur(new Dinosaur("Bao chua", 10, 7));
        teamA.addDinosaur(new Dinosaur("Khung long bay", 20, 7));
        teamA.addDinosaur(new Dinosaur("Bo sat", 30, 6));
        teamB.addDinosaur(new Dinosaur("Ba sung", 10, 6));
        teamB.addDinosaur(new Dinosaur("An co", 20, 6));
        teamB.addDinosaur(new Dinosaur("Khung long duoi nuoc", 30, 7));
    }

    public void run() throws InvalidException {
        List<Team> teams = state.getTeams();

        // Mo phong
        while(!state.checkEnd() && state.getCurrentTurn() <= 16){
            Team team = teams.get(0);
            List<Staff> staffs = team.getStaffs();
            List<Dinosaur> dinosaurs = team.getDinosaurs();
            System.out.println("=== Luot " + (state.getCurrentTurn() + 1) + ": Doi " + team.getName() + " ===");
            System.out.println();

            // Chon khung long
            team.inforDinosaur();
            System.out.print("Chon khung long cham soc: ");
            int choice = scanner.nextInt() - 1;
            Dinosaur dinosaur = dinosaurs.get(choice);
            System.out.println();

            // Chon nhan vien
            team.inforStaff();
            System.out.print("Chon nhan vien (ID): ");
            choice  = scanner.nextInt() - 1;
            if(choice >= 3) choice -= 3;
            Staff staff = staffs.get(choice);
            System.out.println();

            if(staff instanceof Scientist) {
                ((Scientist)staff).handleDinosaur(state, team, dinosaur);
            }
            else if(staff instanceof Ranger) {
                ((Ranger)staff).handleDinosaur(state, team, dinosaur);
            }
            else if(staff instanceof Medic) {
                ((Medic)staff).handleDinosaur(state, team, dinosaur);
            }
            state.status();
            state.switchTurn();
            state.nextTunr();
            System.out.println();
        }
        List<String> logs = state.getLog();
        for(String s : logs) {
            System.out.println(s);
        }
    }
}
