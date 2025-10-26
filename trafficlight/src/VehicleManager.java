import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class VehicleManager {
    private List<Vehicle> vehicles;
    
    public VehicleManager() {
        vehicles = new ArrayList<>();
    }
    
    // Thêm phương tiện
    public void addVehicle(Vehicle vehicle) {
        try {
            if (isIdExists(vehicle.getId())) {
                throw new VehicleException("ID đã tồn tại: " + vehicle.getId());
            }
            vehicles.add(vehicle);
            System.out.println("Đã thêm: " + vehicle.displayInfo());
        } catch (VehicleException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
    
    // Xóa phương tiện theo ID
    public boolean removeVehicle(String id) {
        for (Iterator<Vehicle> iterator = vehicles.iterator(); iterator.hasNext();) {
            Vehicle vehicle = iterator.next();
            if (vehicle.getId().equals(id)) {
                iterator.remove();
                System.out.println("Đã xóa phương tiện có ID: " + id);
                return true;
            }
        }
        System.out.println("Không tìm thấy phương tiện có ID: " + id);
        return false;
    }
    
    // Tìm kiếm theo hãng
    public List<Vehicle> searchByBrand(String brand) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getBrand().toLowerCase().contains(brand.toLowerCase())) {
                result.add(vehicle);
            }
        }
        return result;
    }
    
    // Tìm kiếm theo loại phương tiện
    public List<Vehicle> searchByType(String vehicleType) {
        List<Vehicle> result = new ArrayList<>();
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getVehicleType().equals(vehicleType)) {
                result.add(vehicle);
            }
        }
        return result;
    }
    
    // Sắp xếp theo năm sản xuất (tăng dần)
    public void sortByYear() {
        vehicles.sort(Comparator.comparing(Vehicle::getYearOfManufacture));
        System.out.println("Đã sắp xếp danh sách theo năm sản xuất (tăng dần)");
    }
    
    // Sắp xếp theo năm sản xuất (giảm dần)
    public void sortByYearDescending() {
        vehicles.sort(Comparator.comparing(Vehicle::getYearOfManufacture).reversed());
        System.out.println("Đã sắp xếp danh sách theo năm sản xuất (giảm dần)");
    }
    
    // Hiển thị tất cả phương tiện
    public void displayAllVehicles() {
        if (vehicles.isEmpty()) {
            System.out.println("Danh sách phương tiện trống!");
            return;
        }
        
        System.out.println("\n=== DANH SÁCH PHƯƠNG TIỆN ===");
        for (int i = 0; i < vehicles.size(); i++) {
            System.out.println((i + 1) + ". " + vehicles.get(i).displayInfo());
        }
        System.out.println("Tổng cộng: " + vehicles.size() + " phương tiện");
    }
    
    // Lưu danh sách vào file
    public void saveToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Vehicle vehicle : vehicles) {
                writer.println(vehicle.toString());
            }
            System.out.println("Đã lưu danh sách vào file: " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi khi lưu file: " + e.getMessage());
        }
    }
    
    // Đọc danh sách từ file (đơn giản)
    public void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Đọc: " + line);
            }
            System.out.println("Đã đọc danh sách từ file: " + filename);
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }
    
    // Lấy số lượng phương tiện
    public int getVehicleCount() {
        return vehicles.size();
    }
    
    // Kiểm tra ID đã tồn tại chưa
    public boolean isIdExists(String id) {
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    // Lấy danh sách tất cả phương tiện
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(vehicles);
    }
    
    // Tìm kiếm theo năm sản xuất
    public List<Vehicle> searchByYear(int year) {
        return vehicles.stream()
            .filter(v -> v.getYearOfManufacture() == year)
            .collect(Collectors.toList());
    }
    
    // Tìm kiếm theo khoảng năm sản xuất
    public List<Vehicle> searchByYearRange(int startYear, int endYear) {
        return vehicles.stream()
            .filter(v -> v.getYearOfManufacture() >= startYear && v.getYearOfManufacture() <= endYear)
            .collect(Collectors.toList());
    }
    
    // Sắp xếp theo hãng
    public void sortByBrand() {
        vehicles.sort(Comparator.comparing(Vehicle::getBrand));
        System.out.println("Đã sắp xếp danh sách theo hãng");
    }
    
    // Sắp xếp theo ID
    public void sortById() {
        vehicles.sort(Comparator.comparing(Vehicle::getId));
        System.out.println("Đã sắp xếp danh sách theo ID");
    }
    
    // Lấy phương tiện theo ID
    public Vehicle getVehicleById(String id) {
        return vehicles.stream()
            .filter(v -> v.getId().equals(id))
            .findFirst()
            .orElse(null);
    }
    
    // Cập nhật thông tin phương tiện
    public boolean updateVehicle(String id, String newBrand, int newYear) {
        Vehicle vehicle = getVehicleById(id);
        if (vehicle != null) {
            try {
                // Tạo phương tiện mới với thông tin cập nhật
                Vehicle updatedVehicle = createVehicleWithSameType(vehicle, id, newBrand, newYear);
                if (updatedVehicle != null) {
                    vehicles.remove(vehicle);
                    vehicles.add(updatedVehicle);
                    System.out.println("Đã cập nhật phương tiện có ID: " + id);
                    return true;
                }
            } catch (VehicleException e) {
                System.out.println("Lỗi cập nhật: " + e.getMessage());
            }
        }
        return false;
    }
    
    private Vehicle createVehicleWithSameType(Vehicle original, String id, String brand, int year) throws VehicleException {
        if (original instanceof Motorbike) {
            return new Motorbike(id, brand, year, ((Motorbike) original).getPower());
        } else if (original instanceof Car) {
            return new Car(id, brand, year, ((Car) original).getSeats());
        } else if (original instanceof Truck) {
            return new Truck(id, brand, year, ((Truck) original).getLoadCapacity());
        }
        return null;
    }
} 