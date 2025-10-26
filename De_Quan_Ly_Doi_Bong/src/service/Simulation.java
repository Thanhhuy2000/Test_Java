package service;

import model.*;
import exception.*;
import java.util.*;

public class Simulation {
    private MatchState state;
    private Scanner scanner;
    private List<String> log;

    public Simulation() {
        state = new MatchState();
        scanner = new Scanner(System.in);
        log = new ArrayList<>();
    }

    public void setup() throws InvalidPlayerException {
        //Nhap thong tin cho cac team
        for(int i = 1; i <= 2; i++) {
            System.out.print("Ten doi " + i + ": ");
            String teamName = scanner.nextLine();
            System.out.println();
            Team team = new Team(i, teamName);
            int idx = 1;
            // 4 cau thu o 4 vi tri khac nhau
            for (int j = 1; j <= 4; j++) {
                Player player = null;
                String playerName = "";
                int stamina = 0;
                switch(j) {
                    case 1:
                        System.out.println("Vi tri: Goalkeeper");
                        System.out.print("Ten thanh vien " + j + ": ");
                        playerName = scanner.nextLine();
                        System.out.print("Stamina(0-100): ");
                        stamina = Integer.parseInt(scanner.nextLine());
                        System.out.print("Kha nang can pha (0-1): ");
                        double saveChance = Double.parseDouble(scanner.nextLine());
                        player = new Goalkeeper(idx, playerName, stamina, saveChance);
                        idx++;
                        System.out.println();
                        break;
                    case 2:
                        System.out.println("Vi tri: Defender");
                        System.out.print("Ten thanh vien " + j + ": ");
                        playerName = scanner.nextLine();
                        System.out.print("Stamina(0-100): ");
                        stamina = Integer.parseInt(scanner.nextLine());
                        System.out.print("Kha nang tranh bong(0-10): ");
                        int tacklePower = Integer.parseInt(scanner.nextLine());
                        player = new Defender(idx, playerName, stamina, tacklePower);
                        idx++;
                        System.out.println();
                        break;
                    case 3:
                        System.out.println("Vi tri: Midfielder");
                        System.out.print("Ten thanh vien " + j + ": ");
                        playerName = scanner.nextLine();
                        System.out.print("Stamina(0-100): ");
                        stamina = Integer.parseInt(scanner.nextLine());
                        System.out.print("Kha nang chuyen bong(0-1): ");
                        Double passAccuracy = Double.parseDouble(scanner.nextLine());
                        player = new Midfielder(idx, playerName, stamina, passAccuracy);
                        idx++;
                        System.out.println();
                        break;
                    case 4:
                        System.out.println("Vi tri: Forword");
                        System.out.print("Ten thanh vien " + j + ": ");
                        playerName = scanner.nextLine();
                        System.out.print("Stamina(0-100): ");
                        stamina = Integer.parseInt(scanner.nextLine());
                        System.out.print("Kha nang sut bong(0-10): ");
                        int shotPower = Integer.parseInt(scanner.nextLine());
                        player = new Forward(idx, playerName, stamina, shotPower);
                        idx++;
                        System.out.println();
                        break;
                }
                team.addPlayer(player);
            }
            // cau thu thu 5
            System.out.print("Ten thanh vien 5: ");
            String playerName = scanner.nextLine();
            System.out.print("Stamina(0-100): ");
            int stamina = Integer.parseInt(scanner.nextLine());
            System.out.print("Vai tro? (1:Defender, 2:Midfielder, 3: forward): ");
            int choice = Integer.parseInt(scanner.nextLine());
            Player player = null;
            switch(choice) {
                case 1:
                    System.out.print("Kha nang tranh bong(0-10): ");
                    int tacklePower =  Integer.parseInt(scanner.nextLine());
                    player = new Defender(idx, playerName, stamina, tacklePower);
                    idx++;
                    break;
                case 2:
                    System.out.print("Kha nang chuyen bong(0-1): ");
                    Double passAccuracy =  Double.parseDouble(scanner.nextLine());
                    player = new Midfielder(idx, playerName, stamina, passAccuracy);
                    idx++;
                    break;
                case 3:
                    System.out.print("Kha nang sut bong(0-10): ");
                    int shotPower = Integer.parseInt(scanner.nextLine());
                    player = new Forward(idx, playerName, stamina, shotPower);
                    idx++;
                    break;
                default:
                    throw new InvalidPlayerException("Invalid choice");
            }
            team.addPlayer(player);
            state.addTeam(team);
            System.out.println();
        }

        System.out.println();
    }

    public void run () throws InvalidPlayerException {
        List<Team> teams = state.getTeams();

        //Chon doi hanh dong truoc
        System.out.println("Danh sach cac doi:");
        int tmp = 0;
        for (Team team : teams) {
            System.out.println(tmp + ": Team " + team.getName());
            tmp++;
        }
        System.out.print("Doi hanh dong truoc? (0 or 1): ");
        tmp = Integer.parseInt(scanner.nextLine());
        Team team1 = null;
        Team team2 = null;
        if(tmp == 0) {
            team1 = teams.get(0);
            team2 = teams.get(1);
        }
        else if(tmp == 1) {
            state.swichTurn();
        }
        else {
            throw new InvalidPlayerException("Invalid choice");
        }

        //Bat dau luot thi dau
        while (!state.checkEndGame()) {
            teams = state.getTeams();
            team1 = teams.get(0);
            team2 = teams.get(1);
            System.out.println("Luot " + state.getTurnCount());
            List<Player> players1 = team1.getPlayers();
            List<Player> players2 = team2.getPlayers();

            // Chon thanh vien thuc hien (ben team hanh dong truoc)
            team1.inforTeam(); // In danh sach thong tin doi
            System.out.print("Chon thanh vien thuc hien (ID): ");
            int idInput = Integer.parseInt(scanner.nextLine()) - 1;
            Player player1 = players1.get(idInput);
            Player player2 = null;

            //Hanh dong
            if(player1 instanceof Goalkeeper) {
                //Chon cau thu doi phuong la Forward
                player2 = players2.get(3);
                double success = ((Goalkeeper)player1).getSaveChance() * 10.0 / ((Forward)player2).getShotPower();
                if (Math.random() < success) {
                    player1.decreaseStamina5();
                    player2.decreaseStamina10();
                    //In thong tin
                    System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                    System.out.println("Cau thu " + player2.getName() + "(Forward) sut bong -> Thu mon " + player1.getName() + "(Goalkeeper) can pha thanh cong!");
                    //Add nhat ky
                    log.add(" * Luot " + state.getTurnCount() + ": " + player1.getName() + " (Doi " + team1.getName() + ") can pha thanh cong.");
                } else {
                    player1.decreaseStamina5();
                    player2.decreaseStamina10();
                    //In thong tin
                    System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                    System.out.println("Cau thu " + player2.getName() + "(Forward) sut bong -> Thu mon " + player1.getName() + "(Goalkeeper) can pha that bai! Doi " + team2.getName() + " ghi 1 ban.");
                    team2.setScore(team2.getScore() + 1);
                    //Add nhat ky
                    log.add(" * Luot " + state.getTurnCount() + ": " + player1.getName() + " (Doi " + team1.getName() + ") can pha that bai.");
                }
            }
            else if(player1 instanceof Defender) {
                //Chon cau thu doi phuong la random tru vi tri thu mon
                int index = 1 + (int)(Math.random() * 4);
                player2 = players2.get(index);
                player1.decreaseStamina10();
                player2.setStamina(Math.max(0, player2.getStamina() - ((Defender)player1).getTacklePower()));
                //In thong tin
                System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                System.out.println("Cau thu " + player1.getName() + "(Defender) tranh bong -> cau thu " + player2.getName() + "(" + player2.getRole() + ") mat stamina.");
                //Add nhat ky
                log.add(" * Luot " + state.getTurnCount() + ": " + player1.getName() + " (Doi " + team1.getName() + ") tranh bong " + player2.getName() + ".");
            }
            else if(player1 instanceof Midfielder) {
                //Chon Forward cung doi de truyen (tang stamina cho Forward)
                player2 = players1.get(3);
                player1.decreaseStamina10();
                player2.setStamina(Math.min(100, player2.getStamina() + (int)(((Midfielder)player1).getPassAccurecy() * 10)));
                //In thong tin
                System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                System.out.println("Cau thu " + player1.getName() + "(Midfielder) chuyen bong -> cau thu " + player2.getName() + "(" + player2.getRole() + ") hoi phuc stamina.");
                //Add nhat ky
                log.add(" * Luot " + state.getTurnCount() + ": " + player1.getName() + " (Doi " + team1.getName() + ") chuyen bong " + player2.getName() + ".");
            }
            else if(player1 instanceof Forward) {
                //Chon cau thu doi phuong la Goalkeeper
                player2 = players2.get(0);
                double success = ((Goalkeeper)player2).getSaveChance() * 10.0 / ((Forward)player1).getShotPower();
                if (Math.random() < success) {
                    player1.decreaseStamina10();
                    player2.decreaseStamina5();
                    //In thong tin
                    System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                    System.out.println("Cau thu " + player1.getName() + "(Forward) sut bong -> Thu mon " + player2.getName() + "(Goalkeeper) can pha thanh cong!");
                    //Add nhat ky
                    log.add(" * Luot " + state.getTurnCount() + ": " + player1.getName() + " (Doi " + team1.getName() + ") khong ghi ban.");
                } else {
                    player1.decreaseStamina10();
                    player2.decreaseStamina5();
                    //In thong tin
                    System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                    System.out.println("Cau thu " + player1.getName() + "(Forward) sut bong -> Thu mon " + player2.getName() + "(Goalkeeper) can pha that bai! Doi " + team1.getName() + " ghi 1 ban.");
                    team1.setScore(team1.getScore() + 1);
                    //Add nhat ky
                    log.add(" * Luot " + state.getTurnCount() + ": " + player1.getName() + " (Doi " + team1.getName() + ") ghi ban.");
                }
            }
            else {
                throw new InvalidPlayerException("Invalid choice");
            }
            team1.displayStatus();
            team2.displayStatus();
            state.turn();
            state.swichTurn();
            System.out.println();
        }
        System.out.println("Nhat ky:");
        for(String s : log) {
            System.out.println(s);
        }
    }
}
