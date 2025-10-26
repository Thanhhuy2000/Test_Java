package service;

import exception.*;
import model.*;
import java.util.*;

public class Simulation {
    private CompetitionState state;
    private Scanner scanner;
    private List<String> log;

    public Simulation() {
        state = new CompetitionState();
        scanner = new Scanner(System.in);
        log = new ArrayList<>();
    }

    public void setup() throws InvalidContestantException {
        for(int i = 0; i < 2; i++){
            //Nhap tung doi
            System.out.print("Ten doi " + (i + 1) + ": ");
            String teamName = scanner.nextLine();
            Team team = new Team(teamName);
            int idx = 1;
            System.out.println();
            System.out.println("Nhap thong tin cac thanh vien");
            for(int j = 0; j < 3; j++){
                //Nhap tung thanh vien
                Contestant contestant = null;
                String contestantName = "";
                int energy = 0;
                switch(j) {
                    case 0:
                        System.out.println("Role: Head Chef");
                        break;
                    case 1:
                        System.out.println("Role: Sous Chef");
                        break;
                    case 2:
                        System.out.println("Role: Dessert Specialist");
                        break;
                }
                System.out.print("Ten: ");
                contestantName = scanner.nextLine();
                System.out.print("Energy(0-100): ");
                energy = Integer.parseInt(scanner.nextLine());
                switch(j) {
                    case 0:
                        System.out.print("Cooking skil(0-10): ");
                        int cookingSkil = Integer.parseInt(scanner.nextLine());
                        contestant = new HeadChef(idx, contestantName, energy, cookingSkil);
                        break;
                    case 1:
                        System.out.print("Prep speed(0-10): ");
                        int prepSpeed = Integer.parseInt(scanner.nextLine());
                        contestant = new SousChef(idx, contestantName, energy, prepSpeed);
                        break;
                    case 2:
                        System.out.print("Creativity(0-1): ");
                        double creativity = Double.parseDouble(scanner.nextLine());
                        contestant = new DessertSpecialist(idx, contestantName, energy, creativity);
                        break;
                }
                idx++;
                team.addContestant(contestant);
                System.out.println();
            }
            state.addTeam(team);
        }
        // Danh sach cac mon an
        for (Team team : state.getTeams()) {
            List<Dish> dishs = team.getDishs();
            dishs.add(new Dish(1, "Bo bit tet", 100, 20));
            dishs.add(new Dish(2, "Ca hoi ap chao", 120, 24));
            dishs.add(new Dish(3, "Ga ham", 60, 12));
        }

    }
    public void run() throws InvalidContestantException {
        System.out.println("Cuoc thi nau an!");
        List<Team> teams = state.getTeams();

        // Chon doi hanh dong truoc
        System.out.println("Danh sach cac doi:");
        int tmp = 0;
        for (Team team : teams) {
            System.out.println(tmp + ": Doi " + team.getName());
            tmp++;
        }
        System.out.print("Doi hanh dong truoc? (0 or 1): ");
        tmp = Integer.parseInt(scanner.nextLine());
        Team team1 = null;
        Team team2 = null;
        if (tmp == 0) {
            team1 = teams.get(0);
            team2 = teams.get(1);
        }

        else if (tmp == 1) {
            state.switchTurn();
        }
        else if(tmp != 0 && tmp != 1) {
            throw new InvalidContestantException("Invalid choice");
        }

        // Bat dau luot thi
        while(!state.checkEndCompetition()){
            teams = state.getTeams();
            team1 = teams.get(0);
            team2 = teams.get(1);
            List<Dish> dishs = team1.getDishs();
            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
            List<Contestant>  contestants = team1.getContestants();
            // Chon thanh vien thuc hien
            team1.inforTeam();
            System.out.print("Chon thanh vien thuc hien (ID): ");
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            Contestant contestant = contestants.get(idx);

            //Chon mon an thuc hien
            System.out.println("Danh sach mon an: ");
            for(Dish dish : dishs){
                System.out.println(dish.getId() + "." + dish.getName() + ", Prep time: " + dish.getPrepTime());
            }
            System.out.print("Chon mon an thuc hien(ID). Luu y: chi chon mon con preptime: ");
            int choice = Integer.parseInt(scanner.nextLine()) - 1;
            Dish dish = dishs.get(choice);
            if(dish.getPrepTime() == 0 || dish == null) {
                throw new InvalidContestantException("Invalid sos");
            }

            //Hanh dong
            if(contestant instanceof HeadChef){
                // Tang diem chat luong
                int num = ((HeadChef) contestant).getCookingSkil() * 2;
                dish.setQuality(Math.min(100, dish.getQuality() + num));
                // Giam thoi gian can hoan thanh mon
                dish.setPrepTime(Math.max(0, dish.getPrepTime() - 30));
                // Giam nang luong cua dau bep thuc hien
                contestant.decreaseEnergy();
                // In ket qua
                System.out.println("Thi sinh " + contestant.getName() + " (" + contestant.getRole() + ") nau mon " + dish.getName() + " -> Tang chat luong len " + num);
                log.add("* Luot" + state.getTurnCount() + ": " + contestant.getName() + " (Doi " + team1.getName() + ")" + " tang chat luong mon " + dish.getName());
            }
            else if(contestant instanceof SousChef){
                // Giam manh thoi gian can hoan thanh mon
                int num = ((SousChef) contestant).getPrepSpeed() * 7;
                dish.setPrepTime(Math.max(0, dish.getPrepTime() - num));
                // Giam nang luong cua dau bep thuc hien
                contestant.decreaseEnergy();
                // In ket qua
                System.out.println("Thi sinh " + contestant.getName() + " (" + contestant.getRole() + ") chuan bi mon " + dish.getName() + " -> Giam thoi gian di " + num);
                log.add("* Luot" + state.getTurnCount() + ": " + contestant.getName() + " (Doi " + team1.getName() + ")" + " chuan bi mon " + dish.getName());
            }
            else if(contestant instanceof DessertSpecialist){
                // Tang diem trinh bay
                int num = (int)((((DessertSpecialist) contestant).getCreativity() + Math.random()) * 10);
                dish.setPresentation(Math.min(100, dish.getPresentation() + num));
                // Giam thoi gian can hoan thanh mon
                dish.setPrepTime(Math.max(0, dish.getPrepTime() - 10));
                // Giam nang luong cua dau bep thuc hien
                contestant.decreaseEnergy();
                //In ket qua
                System.out.println("Thi sinh " + contestant.getName() + " (" + contestant.getRole() + ") trang tri mon " + dish.getName() + " -> Tang diem trinh bay len " + num);
                log.add("* Luot" + state.getTurnCount() + ": " + contestant.getName() + " (Doi " + team1.getName() + ")" + " trang tri mon " + dish.getName());
            }
            else {
                throw new InvalidContestantException("Invalid choice");
            }

            team1.displayStatus();
            team2.displayStatus();
            state.turn();
            state.switchTurn();
            System.out.println();
        }

        //Tinh tong diem cua 2 doi sau khi cong 2 diem chat luong va diem trinh bay
        for(Team team : teams) {
            int sum = 0;
            for(Dish dish : team.getDishs()) {
                if(dish.getPrepTime() == 0) {
                    sum += dish.getBonus(); // Nếu món ăn đã hết prep time thì món đó được coi là hoàn thành và đội hoàn thành món sẽ được cộng bonus thêm điểm cua mon do
                };
                sum += dish.calculateScore();
            }
            team.setScoreTeam(team.getScoreTeam() + sum);
        }

        // Tong ket
        System.out.println("Diem cua 2 doi:");
        for(Team team : teams) {
            System.out.println("+ Doi " + team.getName() + ": " + team.getScoreTeam() + " diem");
        }
        if(team1.getScoreTeam() > team2.getScoreTeam()) {
            System.out.println("Doi thang cuoc la doi " + team1.getName() + " voi so diem la " + team1.getScoreTeam());
        }
        else if(team2.getScoreTeam() > team1.getScoreTeam()) {
            System.out.println("Doi thang cuoc la doi " + team2.getName() + " voi so diem la " + team2.getScoreTeam());
        }
        else {
            System.out.println("Hai doi hoa nhau voi so diem: " + team1.getScoreTeam());
        }
        // Nhat ky
        System.out.println("Nhat ky:");
        for(String s : log) {
            System.out.println(s);
        }
    }
}
