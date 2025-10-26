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

    // C1: NHAP THU CONG TU CHON
/*
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
        }

        System.out.println();
    }
*/

    // C2: NHAP TRUC TIEP BAN DAU
    public void setup() throws InvalidPlayerException {
        // Nhap thong tin team 1
        Team teamA = new Team("A");
        List<Player> playersA = Arrays.asList(
                new Goalkeeper(0, "Tuan", 100, 1, 0.5),
                new Defender(1, "Hung", 100, 7, 0.7),
                new Midfielder(2, "Long", 100, 5,1, 7),
                new Forward(3, "Minh", 100, 3, 5)
        );
        for (Player p : playersA) {
            teamA.addPlayer(p);
        }

        // Nhap thong tin team 2
        Team teamB = new Team("B");
        List<Player> playersB = Arrays.asList(
                new Goalkeeper(0, "Nam", 100, 1, 0.5),
                new Defender(1, "Khoa", 100, 7, 0.7),
                new Midfielder(2, "Duy", 100, 5, 1, 7),
                new Forward(3, "Phong", 100, 3, 5)
        );
        for (Player p : playersB) {
            teamB.addPlayer(p);
        }

        state.addTeam(teamA);
        state.addTeam(teamB);
    }

    public void run () throws InvalidPlayerException {
        List<Team> teams = state.getTeams();
        Team team1 = null;
        Team team2 = null;

        //Chon doi hanh dong truoc (cam bong truoc)
        System.out.println("Danh sach cac doi:");
        int tmp = 0;
        for (Team team : teams) {
            System.out.println(tmp + ": Team " + team.getName());
            team.inforTeam();
            tmp++;
        }
        System.out.print("Doi hanh dong truoc? (0 or 1): ");
        tmp = Integer.parseInt(scanner.nextLine());
        if(tmp == 0) {
            team1 = teams.get(0);
            team2 = teams.get(1);
        }
        else if(tmp == 1) {
            state.switchTurn();
        }
        else {
            throw new InvalidPlayerException("Invalid choice");
        }

        //Vi tri cua qua bong hien tai ( Vị trí hiện tại của quả bóng ứng với id cầu thủ đang giữ nó, bắt đầu từ goalkeeper bên cầm bóng trước)
        int point = 0;
        int round = 1;

        //Bat dau luot thi dau
        for(int i = 0; i < 2; i++) {
            log.add("Hiep: " + round);
            while (!state.checkEndGame()) {
                System.out.println("Hiep: " + round);
                teams = state.getTeams();
                team1 = teams.get(0);
                team2 = teams.get(1);
                List<Player> players1 = team1.getPlayers();
                List<Player> players2 = team2.getPlayers();
                Player player1 = null;
                Player player2 = null;

                //Hanh dong
                switch (point) {
                    case 0:
                        player1 = players1.get(0);
                        player2 = players2.get(3);
                        double success = ((Forward) player2).getTacklePower() / (((Goalkeeper) player1).getPassAccurecy() * 10);
                        if (Math.random() < success) {
                            player1.decreaseStamina5();
                            player2.decreaseStamina10();
                            //In thong tin
                            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                            System.out.println("Cau thu " + player1.getName() + "(Goalkeeper) chuyen bong len cho " + players1.get(1).getName() + "(Defender)" + " -> Cau thu " + player2.getName() + "(Forward) can pha that bai.");
                            //Bong duoc chuyen sang cho cau thu defender
                            point = 1;
                            //Add nhat ky
                            log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") chuyen thanh cong.");
                        } else {
                            player1.decreaseStamina5();
                            player2.decreaseStamina10();
                            //In thong tin
                            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                            System.out.println("Cau thu " + player1.getName() + "(Goalkeeper) chuyen bong len cho " + players1.get(1).getName() + "(Defender)" + " -> Cau thu " + player2.getName() + "(Forward) can pha thanh cong lay duoc bong.");
                            //Bong duoc chuyen sang cho cau thu forward doi thu
                            point = 3;
                            //Nếu đối thủ lấy được bóng thì đổi bên tấn công
                            state.switchTurn();
                            //Add nhat ky
                            log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") chuyen that bai.");
                        }
                        break;
                    case 1:
                        player1 = players1.get(1);
                        player2 = players2.get(2);
                        success = ((Midfielder) player2).getTacklePower() / (((Defender) player1).getPassAccurecy() * 10);
                        if (Math.random() < success) {
                            player1.decreaseStamina5();
                            player2.decreaseStamina10();
                            //In thong tin
                            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                            System.out.println("Cau thu " + player1.getName() + "(Defender) chuyen bong len cho " + players1.get(2).getName() + "(Midfielder)" + " -> Cau thu " + player2.getName() + "(Midfielder) can pha that bai.");
                            //Bong duoc chuyen sang cho cau thu midfielder
                            point = 2;
                            //Add nhat ky
                            log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") chuyen thanh cong.");
                        } else {
                            player1.decreaseStamina5();
                            player2.decreaseStamina10();
                            //In thong tin
                            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                            System.out.println("Cau thu " + player1.getName() + "(Defender) chuyen bong len cho " + players1.get(2).getName() + "(Midfielder)" + " -> Cau thu " + player2.getName() + "(Midfielder) can pha thanh cong lay duoc bong.");
                            //Bong duoc chuyen sang cho cau thu midfielder doi thu
                            point = 2;
                            //Nếu đối thủ lấy được bóng thì đổi bên tấn công
                            state.switchTurn();
                            //Add nhat ky
                            log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") chuyen that bai.");
                        }
                        break;
                    case 2:
                        // Nếu forward đội đang cầm bóng mà không thể thi đấu (hết stamina) thì midfielder sẽ sút luôn
                        if (!players1.get(3).canAct()) {
                            player1 = players1.get(2);
                            player2 = players2.get(0);
                            success = ((Midfielder) player1).getShotPower() / (((Goalkeeper) player2).getSaveChance() * 10.0);
                            if (Math.random() < success) {
                                player1.decreaseStamina10();
                                player2.decreaseStamina5();
                                //In thong tin
                                System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                                System.out.println("Cau thu " + player1.getName() + "(Midfielder) sut bong -> Thu mon " + player2.getName() + "(Goalkeeper) can pha thanh cong!");
                                //Add nhat ky
                                log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") khong ghi ban.");
                            } else {
                                player1.decreaseStamina10();
                                player2.decreaseStamina5();
                                //In thong tin
                                System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                                System.out.println("Cau thu " + player1.getName() + "(Midfielder) sut bong -> Thu mon " + player2.getName() + "(Goalkeeper) can pha that bai! Doi " + team1.getName() + " ghi 1 ban.");
                                team1.setScore(team1.getScore() + 1);
                                //Add nhat ky
                                log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") ghi ban.");
                            }
                            //Bong duoc chuyen sang cho cau thu Goalkeeper doi thu du co ghi ban hay khong
                            point = 0;
                            //Đổi bên tấn công
                            state.switchTurn();
                            break;
                        }
                        // Nếu forward đội cầm bóng còn thi đấu được thì chuyền bình thường
                        player1 = players1.get(2);
                        player2 = players2.get(1);
                        success = ((Defender) player2).getTacklePower() / (((Midfielder) player1).getPassAccurecy() * 10);
                        if (Math.random() < success) {
                            player1.decreaseStamina5();
                            player2.decreaseStamina10();
                            //In thong tin
                            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                            System.out.println("Cau thu " + player1.getName() + "(Midfielder) chuyen bong len cho " + players1.get(3).getName() + "(Forward)" + " -> Cau thu " + player2.getName() + "(Defender) can pha that bai.");
                            //Bong duoc chuyen sang cho cau thu forward
                            point = 3;
                            //Add nhat ky
                            log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") chuyen thanh cong.");
                        } else {
                            player1.decreaseStamina5();
                            player2.decreaseStamina10();
                            //In thong tin
                            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                            System.out.println("Cau thu " + player1.getName() + "(Midfielder) chuyen bong len cho " + players1.get(3).getName() + "(Forward)" + " -> Cau thu " + player2.getName() + "(Defender) can pha thanh cong lay duoc bong.");
                            //Bong duoc chuyen sang cho cau thu defender doi thu
                            point = 1;
                            //Nếu đối thủ lấy được bóng thì đổi bên tấn công
                            state.switchTurn();
                            //Add nhat ky
                            log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") chuyen that bai.");
                        }
                        break;
                    case 3:
                        player1 = players1.get(3);
                        player2 = players2.get(0);
                        success = ((Forward) player1).getShotPower() / (((Goalkeeper) player2).getSaveChance() * 10);
                        if (Math.random() < success) {
                            player1.decreaseStamina10();
                            player2.decreaseStamina5();
                            //In thong tin
                            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                            System.out.println("Cau thu " + player1.getName() + "(Forward) sut bong -> Thu mon " + player2.getName() + "(Goalkeeper) can pha thanh cong!");
                            //Add nhat ky
                            log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") khong ghi ban.");
                        } else {
                            player1.decreaseStamina10();
                            player2.decreaseStamina5();
                            //In thong tin
                            System.out.println("=== Luot " + state.getTurnCount() + ": Doi " + team1.getName() + " ===");
                            System.out.println("Cau thu " + player1.getName() + "(Forward) sut bong -> Thu mon " + player2.getName() + "(Goalkeeper) can pha that bai! Doi " + team1.getName() + " ghi 1 ban.");
                            team1.setScore(team1.getScore() + 1);
                            //Add nhat ky
                            log.add(" * Luot " + state.getTurnCount() + ": " + player1.getRole() + " " + player1.getName() + " (Doi " + team1.getName() + ") ghi ban.");
                        }
                        //Bong duoc chuyen sang cho cau thu Goalkeeper doi thu du co ghi ban hay khong
                        point = 0;
                        //Đổi bên tấn công
                        state.switchTurn();
                        break;
                    default:
                        throw new InvalidPlayerException("Erone");
                }

                team1.displayStatus();
                team2.displayStatus();
                state.turn();
                System.out.println();
            }
            // Het hiep doi ben
            state.switchTurn();
            round++;
            state.setTurnCount(1);
        }

        // Doi thang chung cuoc dua vao scode
        if (team1.getScore() > team2.getScore()) {
            System.out.println("Doi thang cung cuoc la doi A voi ty so la: " + team1.getScore() + "-" + team2.getScore());
        } else if (team1.getScore() < team2.getScore()) {
            System.out.println("Doi thang cung cuoc la doi B voi ty so la: " + team2.getScore() + "-" + team1.getScore());
        } else {
            System.out.println("Hai doi hoa nhau voi ty so la: " + team2.getScore() + "-" + team1.getScore());
        }
        System.out.println();

        // Nhat ky
        System.out.println("Nhat ky:");
        for (String s : log) {
            System.out.println(s);
        }
    }
}
