package simulation;

import java.util.*;
import model.*;

public class RaceSimulation {
    private RaceState raceState;
    private Scanner scanner;

    public RaceSimulation(RaceState raceState, Scanner scanner) {
        this.raceState = raceState;
        this.scanner = scanner;
    }

    public void start() {
        int maxRounds = Math.min(8, raceState.getMaxRounds());
        List<Team> teams = raceState.getTeams();
        List<Obstacle> obstacles = raceState.getObstacles();
        Map<Racer, Integer> racerTurns = new HashMap<>();
        for (Team team : teams) {
            for (Racer r : team.getRacers()) {
                racerTurns.put(r, 0);
            }
        }
        int round = 1;
        boolean stop = false;
        while (round <= maxRounds && !stop) {
            System.out.println("=== Lượt " + round + " ===");
            for (Team team : teams) {
                System.out.println("- " + team.getName() + ":");
                Racer racer = selectRacerWithMinTurns(team, racerTurns);
                if (round-1 < obstacles.size()) {
                    Obstacle obs = obstacles.get(round-1);
                    boolean success = racer.navigateObstacle(raceState, obs);
                    racer.incrementObstaclesPassed();
                    racerTurns.put(racer, racerTurns.get(racer) + 1);
                    if (success) {
                        team.addScore(1);
                        team.addPassedObstacle(obs);
                        System.out.println("Tay đua " + racer.getName() + " (" + racer.getVehicleType() + ") vượt '" + obs.getName() + "' -> Thành công!");
                    } else {
                        System.out.println("Tay đua " + racer.getName() + " (" + racer.getVehicleType() + ") vượt '" + obs.getName() + "' -> Thất bại!");
                    }
                }
            }
            printRanking(teams);
            printTeamStatus(teams);
            System.out.println();
            // Kiểm tra điều kiện dừng: 1 đội hết sạch fuel
            for (Team team : teams) {
                boolean allOut = true;
                for (Racer r : team.getRacers()) {
                    if (r.getFuel() > 0) {
                        allOut = false;
                        break;
                    }
                }
                if (allOut) {
                    System.out.println("Đội " + team.getName() + " đã hết nhiên liệu! Trận đấu kết thúc.");
                    stop = true;
                    break;
                }
            }
            round++;
        }
    }

    // Đảm bảo mỗi thành viên thi ít nhất 2 lượt, ưu tiên chọn thành viên chưa đủ 2 lượt
    private Racer selectRacerWithMinTurns(Team team, Map<Racer, Integer> racerTurns) {
        List<Racer> racers = team.getRacers();
        int minTurns = Integer.MAX_VALUE;
        for (Racer r : racers) {
            minTurns = Math.min(minTurns, racerTurns.get(r));
        }
        List<Integer> candidateIdx = new ArrayList<>();
        for (int i = 0; i < racers.size(); i++) {
            if (racerTurns.get(racers.get(i)) < 2) {
                candidateIdx.add(i);
            }
        }
        if (candidateIdx.isEmpty()) {
            // Nếu ai cũng đủ 2 lượt thì cho chọn tự do
            for (int i = 0; i < racers.size(); i++) candidateIdx.add(i);
        }
        System.out.println("Chọn tay đua cho đội " + team.getName() + ":");
        for (int i : candidateIdx) {
            Racer r = racers.get(i);
            System.out.println((i+1) + ". " + r.getName() + " (" + r.getVehicleType() + ", Fuel: " + r.getFuel() + ", Đã thi: " + racerTurns.get(r) + " lượt)");
        }
        int choice = -1;
        while (true) {
            System.out.print("Nhập số thứ tự tay đua: ");
            try {
                choice = Integer.parseInt(scanner.nextLine()) - 1;
                if (candidateIdx.contains(choice) && racers.get(choice).getFuel() > 0) break;
                else System.out.println("Chỉ được chọn trong danh sách trên và tay đua còn fuel!");
            } catch (Exception e) {
                System.out.println("Nhập lại!");
            }
        }
        return racers.get(choice);
    }

    private void printRanking(List<Team> teams) {
        List<Team> sorted = new ArrayList<>(teams);
        sorted.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        System.out.println("\n--- Bảng xếp hạng sau lượt ---");
        for (int i = 0; i < sorted.size(); i++) {
            Team t = sorted.get(i);
            System.out.println((i+1) + ". " + t.getName() + " - Điểm: " + t.getScore());
        }
    }

    private void printTeamStatus(List<Team> teams) {
        System.out.println("\n--- Trạng thái các đội sau lượt ---");
        for (Team team : teams) {
            System.out.println("- " + team.getName() + ": Điểm: " + team.getScore());
            for (Racer racer : team.getRacers()) {
                System.out.println("  + " + racer.getName() + " (" + racer.getVehicleType() + "): Fuel: " + racer.getFuel());
            }
        }
    }
} 