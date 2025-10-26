package service;

import exception.InvalidException;
import model.*;

import javax.naming.InvalidNameException;
import java.util.*;

public class Simulation {
    private SpaceState state;
    private Scanner scanner;
    private Random rand;
    private Mission su_co;

    public Simulation() throws InvalidException {
        state = new SpaceState();
        scanner = new Scanner(System.in);
        rand = new Random();
        su_co = new Mission(0,"SU CO TAU VU TRU", 10);;
    }


    //C1 tu nhap nhu cac phan khac
    //C2 nhap san
    public void setup() throws InvalidException {
        // Tạo đội A
        Team team1 = new Team("A");
        team1.addAstronaut(new Engineer(1, "Minh", 100, 7));
        team1.addAstronaut(new Pilot(2, "Lan", 100, 0.5));
        team1.addAstronaut(new Scientist(3, "Hung", 100, 8));
        state.addTeam(team1); // Thêm team1 vào state

        // Tạo đội B
        Team team2 = new Team("B");
        team2.addAstronaut(new Engineer(4, "Nam", 100, 9));
        team2.addAstronaut(new Pilot(5, "Mai", 100, 0.5));
        team2.addAstronaut(new Scientist(6, "Khoa", 100, 6));
        state.addTeam(team2);

        // Tao danh sach thu thach
        for (Team team : state.getTeams()) {
            List<Mission> missions = team.getMissions();
            missions.add(new Mission(1,"Kiem tra tau", 7));
            missions.add(new Mission(2,"Thu thap du lieu", 10));
            missions.add(new Mission(3,"Quan sat hanh tinh", 5));
        }
    }

    public void run() throws InvalidException {
        List<String> log = state.getLog();
        // Chon team hanh dong truoc (dua team duoc chon len lam phan tu dau tien, ap dung cho bai co 2 phan tu)
        List<Team> teams = state.getTeams();
        Team team = null;
        System.out.println("Cac team:");
        for(int i = 0; i < teams.size(); i++){
            System.out.println(i + ". " + teams.get(i).getName());
        }
        System.out.print("Team thuc hien su menh truoc: ");
        int choiceTeam = scanner.nextInt();
        if(choiceTeam == 1) {
            state.switchTurn();
        }
        if(choiceTeam != 0 && choiceTeam != 1){
            throw new InvalidException("Invalid choice");
        }
        System.out.println();

        // Random su co
        int randomNumber = rand.nextInt(6) + 6;

        // Bat dau mo phong
        while(!state.checkEnd() && state.noEnd()) {
            team = teams.get(0);
            List<Astronaut> astronauts = team.getAstronauts();
            List<Mission> missions = team.getMissions();

            // Su co tau
            if (state.getCurrenTurn() == randomNumber) {
                System.out.println("!!! SU CO TAU VU TRU DANG DIEN RA. TAT CA CAC PHI HANH GIA TRO VE VI TRI SUA TAU!!!");
                team.inform();
                System.out.print("CHON THANH VIEN SUA (ID): ");
                int choiceAstronaut = scanner.nextInt() - 1;
                if(choiceAstronaut >= 3) {
                    choiceAstronaut -= 3;
                }
                Astronaut astro = astronauts.get(choiceAstronaut);
                if(!astro.canPerform()) {
                    throw new InvalidException("Invalid choiceAstronaut");
                }
                if(astro instanceof Pilot) {
                    su_co.setTimeRemaining(su_co.getTimeRemaining() - (int)(((Pilot)astro).getNavigationSkill() * 10));
                    astro.decreaseEnergy();
                }
                else {
                    su_co.setTimeRemaining(su_co.getTimeRemaining() - 2);
                    astro.decreaseEnergy();
                }
                state.addLog("!!! SU CO HONG TAU !!! phi hanh gia " + astro.getName() + "(" + astro.getRole() + ") team " + team.getName() + " tham gia sua");
                if(su_co.getTimeRemaining() <= 0) {
                    state.nextTurn();
                }
                state.switchTurn();
                continue;
            }

            // Luot
            System.out.println("Luot: " + state.getCurrenTurn());

            // Su menh
            System.out.println("Danh sach su menh");
            for(Mission mission : missions) {
                if(mission.isCompleted()) continue;
                System.out.println(mission.getId() + ". " + mission.getName() + ": , durability: " +  mission.getDurability() + ", timeRemaining: " + mission.getTimeRemaining() + ", sciencePoint: " + mission.getSciencePoint());
            }
            System.out.print("Chon su menh: ");
            int choice = scanner.nextInt() - 1;
            Mission mission = missions.get(choice);
            System.out.println();

            // Thanh vien tham gia su menh
            team.inform();
            System.out.print("Chon thanh vien thuc hien su menh (ID): ");
            int choiceAstronaut = scanner.nextInt() - 1;
            if(choiceAstronaut >= 3) {
                choiceAstronaut -= 3;
            }
            Astronaut astro = astronauts.get(choiceAstronaut);
            if(!astro.canPerform()) {
                throw new InvalidException("Invalid choiceAstronaut");
            }

            System.out.println();
            System.out.println("Ket thuc luot " + state.getCurrenTurn());
            if(astro instanceof Engineer) {
                mission.setDurability(mission.getDurability() + ((Engineer)astro).getRepairSkill() * 2);
                mission.setTimeRemaining(mission.getTimeRemaining() - 2);
                astro.decreaseEnergy();
            }
            else if(astro instanceof Pilot) {
                mission.setTimeRemaining(mission.getTimeRemaining() - (int)(((Pilot)astro).getNavigationSkill() * 10));
                astro.decreaseEnergy();
            }
            else if(astro instanceof Scientist) {
                int tmp = mission.getSciencePoint() + ((Scientist)astro).getResearchLever() + (int)(Math.random() * 10);
                mission.setSciencePoint(tmp);
                mission.setTimeRemaining(mission.getTimeRemaining() - 2);
                astro.decreaseEnergy();
            }
            if(mission.isCompleted()){
                team.setScore(team.getScore() + 40);
                mission.setTimeRemaining(0);
            }
            for(Team t : state.getTeams()) {
                t.status();
            }
            System.out.println();
            state.addLog("Turn " + state.getCurrenTurn() + ": phi hanh gia " + astro.getName() + "(" + astro.getRole() + ") team " + team.getName() + " thuc hien su menh");
            state.nextTurn();
            state.switchTurn();
        }
        //Ket qua
        for(Team t : teams){
            for(Mission mission : t.getMissions()){
                t.setScore(t.getScore() + (mission.getDurability() + mission.getSciencePoint()) / 2);
            }
        }
        System.out.println("Ket qua cua 2 doi");
        for(Team t : teams){
            System.out.println("Team " + t.getName() + ": " + t.getScore() + " diem");
        }
        if(teams.get(0).getScore() > teams.get(1).getScore()){
            System.out.println("Doi thang la doi " + teams.get(0).getName() + "voi so diem la " + teams.get(0).getScore() + " diem");
        } else{
            System.out.println("Doi thang la doi " + teams.get(1).getName() + "voi so diem la " + teams.get(1).getScore() + " diem");
        }
        for(String s : log) {
            System.out.println(s);
        }
    }
}
