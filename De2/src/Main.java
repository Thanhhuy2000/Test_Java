import java.util.*;
import model.*;
import simulation.RaceSimulation;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Nhập số lượng đội
        System.out.print("Nhập số lượng đội: ");
        int teamCount = Integer.parseInt(scanner.nextLine());
        List<Team> teams = new ArrayList<>();
        for (int t = 0; t < teamCount; t++) {
            System.out.print("Nhập tên đội thứ " + (t+1) + ": ");
            String teamName = scanner.nextLine();
            Team team = new Team(teamName);
            // Nhập số lượng thành viên
            System.out.print("Nhập số lượng thành viên cho đội (gợi ý: 3): ");
            int memberCount = Integer.parseInt(scanner.nextLine());
            for (int m = 0; m < memberCount; m++) {
                System.out.println("--- Thành viên " + (m+1) + " ---");
                System.out.print("Tên: ");
                String name = scanner.nextLine();
                System.out.print("Loại xe (SPORT, OFF_ROAD, CLASSIC): ");
                VehicleType type = VehicleType.valueOf(scanner.nextLine().trim().toUpperCase());
                System.out.print("Fuel (0-100): ");
                int fuel = Integer.parseInt(scanner.nextLine());
                Racer racer = null;
                switch (type) {
                    case SPORT:
                        System.out.print("Speed (0-10): ");
                        int speed = Integer.parseInt(scanner.nextLine());
                        racer = new SportRacer(UUID.randomUUID().toString(), name, fuel, speed);
                        break;
                    case OFF_ROAD:
                        System.out.print("Durability (0-10): ");
                        int durability = Integer.parseInt(scanner.nextLine());
                        racer = new OffRoadRacer(UUID.randomUUID().toString(), name, fuel, durability);
                        break;
                    case CLASSIC:
                        System.out.print("Control (0-10): ");
                        int control = Integer.parseInt(scanner.nextLine());
                        racer = new ClassicRacer(UUID.randomUUID().toString(), name, fuel, control);
                        break;
                }
                team.addRacer(racer);
            }
            teams.add(team);
        }

        // Nhập số lượng chướng ngại vật
        System.out.print("Nhập số lượng chướng ngại vật: ");
        int obsCount = Integer.parseInt(scanner.nextLine());
        List<Obstacle> obstacles = new ArrayList<>();
        for (int i = 0; i < obsCount; i++) {
            System.out.println("--- Chướng ngại vật " + (i+1) + " ---");
            System.out.print("Tên: ");
            String name = scanner.nextLine();
            System.out.print("Độ khó (difficulty): ");
            int diff = Integer.parseInt(scanner.nextLine());
            System.out.print("Sát thương (damage): ");
            int dmg = Integer.parseInt(scanner.nextLine());
            obstacles.add(new Obstacle(name, diff, dmg));
        }

        System.out.print("Nhập số lượt đua tối đa: ");
        int maxRounds = Integer.parseInt(scanner.nextLine());
        RaceState raceState = new RaceState(teams, obstacles, maxRounds);
        RaceSimulation simulation = new RaceSimulation(raceState, scanner);
        simulation.start();

        // In trạng thái cuối và thống kê số lần vượt chướng ngại vật
        System.out.println("\n--- Kết quả cuối cùng ---");
        for (Team team : teams) {
            System.out.println("- " + team.getName() + ": Điểm: " + team.getScore());
            for (Racer racer : team.getRacers()) {
                System.out.println("  + " + racer.getName() + " (" + racer.getVehicleType() + "): Fuel: " + racer.getFuel() + ", Số lần vượt chướng ngại vật: " + racer.getObstaclesPassedCount());
            }
        }
    }
} 