import java.util.*;
import java.util.stream.Collectors;

public class VehicleStatistics {
    
    public static void displayStatistics(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("Không có dữ liệu để thống kê!");
            return;
        }
        
        System.out.println("\n=== THỐNG KÊ PHƯƠNG TIỆN ===");
        
        // Thống kê theo loại phương tiện
        Map<String, Long> typeStats = vehicles.stream()
            .collect(Collectors.groupingBy(Vehicle::getVehicleType, Collectors.counting()));
        
        System.out.println("Phân bố theo loại phương tiện:");
        typeStats.forEach((type, count) -> 
            System.out.printf("  %s: %d chiếc (%.1f%%)\n", type, count, 
                (double) count / vehicles.size() * 100));
        
        // Thống kê theo hãng
        Map<String, Long> brandStats = vehicles.stream()
            .collect(Collectors.groupingBy(Vehicle::getBrand, Collectors.counting()));
        
        System.out.println("\nTop 5 hãng phổ biến:");
        brandStats.entrySet().stream()
            .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> 
                System.out.printf("  %s: %d chiếc\n", entry.getKey(), entry.getValue()));
        
        // Thống kê theo năm sản xuất
        int oldestYear = vehicles.stream()
            .mapToInt(Vehicle::getYearOfManufacture)
            .min().orElse(0);
        int newestYear = vehicles.stream()
            .mapToInt(Vehicle::getYearOfManufacture)
            .max().orElse(0);
        
        System.out.printf("\nNăm sản xuất: %d - %d\n", oldestYear, newestYear);
        
        // Thống kê chi tiết cho từng loại
        displayDetailedStats(vehicles);
    }
    
    private static void displayDetailedStats(List<Vehicle> vehicles) {
        System.out.println("\n=== THỐNG KÊ CHI TIẾT ===");
        
        // Xe máy
        List<Vehicle> motorbikes = vehicles.stream()
            .filter(v -> v instanceof Motorbike)
            .collect(Collectors.toList());
        
        if (!motorbikes.isEmpty()) {
            System.out.println("Xe máy:");
            IntSummaryStatistics powerStats = motorbikes.stream()
                .mapToInt(v -> ((Motorbike) v).getPower())
                .summaryStatistics();
            System.out.printf("  Công suất trung bình: %.1fcc\n", powerStats.getAverage());
            System.out.printf("  Công suất cao nhất: %dcc\n", powerStats.getMax());
            System.out.printf("  Công suất thấp nhất: %dcc\n", powerStats.getMin());
        }
        
        // Ô tô
        List<Vehicle> cars = vehicles.stream()
            .filter(v -> v instanceof Car)
            .collect(Collectors.toList());
        
        if (!cars.isEmpty()) {
            System.out.println("Ô tô:");
            IntSummaryStatistics seatsStats = cars.stream()
                .mapToInt(v -> ((Car) v).getSeats())
                .summaryStatistics();
            System.out.printf("  Số chỗ trung bình: %.1f\n", seatsStats.getAverage());
            System.out.printf("  Số chỗ cao nhất: %d\n", seatsStats.getMax());
            System.out.printf("  Số chỗ thấp nhất: %d\n", seatsStats.getMin());
        }
        
        // Xe tải
        List<Vehicle> trucks = vehicles.stream()
            .filter(v -> v instanceof Truck)
            .collect(Collectors.toList());
        
        if (!trucks.isEmpty()) {
            System.out.println("Xe tải:");
            DoubleSummaryStatistics loadStats = trucks.stream()
                .mapToDouble(v -> ((Truck) v).getLoadCapacity())
                .summaryStatistics();
            System.out.printf("  Tải trọng trung bình: %.1f tấn\n", loadStats.getAverage());
            System.out.printf("  Tải trọng cao nhất: %.1f tấn\n", loadStats.getMax());
            System.out.printf("  Tải trọng thấp nhất: %.1f tấn\n", loadStats.getMin());
        }
    }
    
    public static List<Vehicle> getVehiclesByYearRange(List<Vehicle> vehicles, int startYear, int endYear) {
        return vehicles.stream()
            .filter(v -> v.getYearOfManufacture() >= startYear && v.getYearOfManufacture() <= endYear)
            .collect(Collectors.toList());
    }
    
    public static double getAverageAge(List<Vehicle> vehicles) {
        int currentYear = java.time.Year.now().getValue();
        return vehicles.stream()
            .mapToInt(v -> currentYear - v.getYearOfManufacture())
            .average()
            .orElse(0.0);
    }
} 