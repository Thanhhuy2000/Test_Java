package service;

import exception.InvalidException;
import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Simulation {
    private ExpeditionState state;
    private Scanner scanner;

    public Simulation() {
        this.state = new ExpeditionState();
        this.scanner = new Scanner(System.in);
    }

    public void setup() throws InvalidException {
        // Nhap thong tin cua 2 doi
        Team teamA = new Team("A");
        teamA.addExplorer(new Guide(1, "Minh", 100, 10));
        teamA.addExplorer(new Archaeologist(2, "Lan", 100, 10));
        teamA.addExplorer(new Hunter(3, "Hung", 100, 1));
        state.addTeam(teamA);
        Team teamB = new Team("B");
        teamB.addExplorer(new Guide(4, "Nam", 100, 10));
        teamB.addExplorer(new Archaeologist(5, "Mai", 100, 10));
        teamB.addExplorer(new Hunter(6, "Khoa", 100, 1));
        state.addTeam(teamB);

        // Nhap thu thach
        for(Team team : state.getTeams()){
            team.addChallenge(new Challenge("Hang động", 30, 50));
            team.addChallenge(new Challenge("Đền cổ", 40, 70));
            team.addChallenge(new Challenge("Rừng rậm", 25, 40));
            team.addChallenge(new Challenge("Mỏ khoáng", 50, 90));
            team.addChallenge(new Challenge("Đầm lầy", 20, 30));
        }

        // Nhap thu thach dac biet
        System.out.println("Thu thach dac biet");
        System.out.print("Ten thu thach dac biet: ");
        String name = scanner.nextLine();
        System.out.print("Do kho thu thach(0-10): ");
        int difficulty = Integer.parseInt(scanner.nextLine());
        if(difficulty < 0 || difficulty > 10){
            throw new InvalidException("Invalid difficulty");
        }
        System.out.print("Gia tri thu thach(0-100): ");
        int value = Integer.parseInt(scanner.nextLine());
        if(value < 0 || value > 100){
            throw new InvalidException("Invalid value");
        }
        Challenge challengedb = new Challenge(name, difficulty, value);
        for(Team team : state.getTeams()) {
            team.addChallenge(challengedb);
        }
    }

    public void run() throws InvalidException {
        List<Team> teams = state.getTeams();
        // Chon team tham gia thu thach truoc
        int tmp = 0;
        for(Team team : teams){
            System.out.println(tmp + "." + team.getName());
            tmp++;
        }
        System.out.print("Chon doi tham gia thu thach truoc(ID): ");
        int choice = Integer.parseInt(scanner.nextLine());
        if(choice != 0 && choice != 1) {
            throw new InvalidException("Erron");
        }
        if(choice == 1) {
            state.switchTurn();
        }
        System.out.println();

        // Chay mo phong
        while(!state.checkEnd()){
            Team team = teams.get(0);
            List<Challenge> challenges = team.getChallenges();
            List<Explorer> explorers = team.getExplorers();
            System.out.println("=== Luot " + (state.getCurrentTurn() + 1) + ": Doi " + team.getName() + " ===");

            // Chon thu thach
            tmp = 0;
            for(Challenge challenge : challenges){
                System.out.println(tmp + "." + challenge.getName() + ", difficulty: " + challenge.getDifficulty() + ", traesure value: " + challenge.getTraesureValue());
                tmp++;
            }
            System.out.print("Chon thu thach: ");
            choice = Integer.parseInt(scanner.nextLine());
            if(choice < 0 || choice > challenges.size()) {
                throw new InvalidException("Invalid value");
            }
            Challenge challenge = challenges.get(choice);

            // Chon nha tham hiem
            tmp = 0;
            for(Explorer explorer : explorers){
                System.out.println(tmp + "." + explorer.getName() + " (" +explorer.getRole() + ") stamina: " + explorer.getStamina());
                tmp++;
            }
            System.out.println("Chon nha tham hiem: ");
            choice = Integer.parseInt(scanner.nextLine());
            if(choice < 0 || choice > explorers.size()) {
                throw new InvalidException("Invalid value");
            }
            Explorer explorer = explorers.get(choice);

            if(explorer instanceof Guide) {
                ((Guide)explorer).overcomeChallenge(state, team, challenge);
            }
            else if(explorer instanceof Archaeologist) {
                ((Archaeologist)explorer).overcomeChallenge(state, team, challenge);
            }
            else if(explorer instanceof Hunter) {
                ((Hunter)explorer).overcomeChallenge(state, team, challenge);
            }

            state.nextTurn();
            state.switchTurn();
        }
    }
}
