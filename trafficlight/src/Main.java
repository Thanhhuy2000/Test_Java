import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        VehicleManager manager = new VehicleManager();
        Scanner scanner = new Scanner(System.in);
        
        // Thêm một số dữ liệu mẫu
        addSampleData(manager);
        
        while (true) {
            displayMenu();
            System.out.print("Nhập lựa chọn của bạn: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng mới
            
            switch (choice) {
                case 1:
                    addVehicleMenu(manager, scanner);
                    break;
                case 2:
                    removeVehicleMenu(manager, scanner);
                    break;
                case 3:
                    searchMenu(manager, scanner);
                    break;
                case 4:
                    sortMenu(manager, scanner);
                    break;
                case 5:
                    manager.displayAllVehicles();
                    break;
                case 6:
                    statisticsMenu(manager);
                    break;
                case 7:
                    updateVehicleMenu(manager, scanner);
                    break;
                case 8:
                    fileMenu(manager, scanner);
                    break;
                case 0:
                    System.out.println("Cảm ơn bạn đã sử dụng hệ thống!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
            
            System.out.println("\n" + "=".repeat(50) + "\n");
        }
    }
    
    private static void displayMenu() {
        System.out.println("\n=== HỆ THỐNG QUẢN LÝ PHƯƠNG TIỆN GIAO THÔNG ===");
        System.out.println("1. Thêm phương tiện");
        System.out.println("2. Xóa phương tiện");
        System.out.println("3. Tìm kiếm");
        System.out.println("4. Sắp xếp");
        System.out.println("5. Hiển thị tất cả");
        System.out.println("6. Thống kê");
        System.out.println("7. Cập nhật thông tin");
        System.out.println("8. Lưu/Đọc file");
        System.out.println("0. Thoát");
    }
    
    private static void addSampleData(VehicleManager manager) {
        System.out.println("Đang thêm dữ liệu mẫu...");
        
        try {
            // Thêm xe máy
            manager.addVehicle(new Motorbike("M001", "Honda", 2020, 110));
            manager.addVehicle(new Motorbike("M002", "Yamaha", 2021, 125));
            manager.addVehicle(new Motorbike("M003", "Suzuki", 2019, 100));
            
            // Thêm ô tô
            manager.addVehicle(new Car("C001", "Toyota", 2022, 5));
            manager.addVehicle(new Car("C002", "Honda", 2021, 7));
            manager.addVehicle(new Car("C003", "Ford", 2020, 4));
            
            // Thêm xe tải
            manager.addVehicle(new Truck("T001", "Hyundai", 2021, 2.5));
            manager.addVehicle(new Truck("T002", "Isuzu", 2020, 5.0));
            manager.addVehicle(new Truck("T003", "Dongfeng", 2019, 3.0));
            
            System.out.println("Đã thêm " + manager.getVehicleCount() + " phương tiện mẫu!\n");
        } catch (VehicleException e) {
            System.out.println("Lỗi khi thêm dữ liệu mẫu: " + e.getMessage());
        }
    }
    
    private static void addVehicleMenu(VehicleManager manager, Scanner scanner) {
        System.out.println("\n=== THÊM PHƯƠNG TIỆN ===");
        System.out.println("1. Thêm xe máy");
        System.out.println("2. Thêm ô tô");
        System.out.println("3. Thêm xe tải");
        System.out.print("Chọn loại phương tiện: ");
        
        int type = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới
        
        System.out.print("Nhập ID: ");
        String id = scanner.nextLine();
        
        if (manager.isIdExists(id)) {
            System.out.println("ID đã tồn tại!");
            return;
        }
        
        System.out.print("Nhập hãng: ");
        String brand = scanner.nextLine();
        
        System.out.print("Nhập năm sản xuất: ");
        int year = scanner.nextInt();
        
        try {
            switch (type) {
                case 1:
                    System.out.print("Nhập công suất (cc): ");
                    int power = scanner.nextInt();
                    manager.addVehicle(new Motorbike(id, brand, year, power));
                    break;
                case 2:
                    System.out.print("Nhập số chỗ ngồi: ");
                    int seats = scanner.nextInt();
                    manager.addVehicle(new Car(id, brand, year, seats));
                    break;
                case 3:
                    System.out.print("Nhập tải trọng (tấn): ");
                    double loadCapacity = scanner.nextDouble();
                    manager.addVehicle(new Truck(id, brand, year, loadCapacity));
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } catch (VehicleException e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
    
    private static void removeVehicleMenu(VehicleManager manager, Scanner scanner) {
        System.out.print("\nNhập ID phương tiện cần xóa: ");
        String id = scanner.nextLine();
        manager.removeVehicle(id);
    }
    
    private static void searchMenu(VehicleManager manager, Scanner scanner) {
        System.out.println("\n=== TÌM KIẾM ===");
        System.out.println("1. Tìm theo hãng");
        System.out.println("2. Tìm theo loại phương tiện");
        System.out.println("3. Tìm theo năm sản xuất");
        System.out.println("4. Tìm theo khoảng năm sản xuất");
        System.out.print("Chọn cách tìm kiếm: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới
        
        List<Vehicle> results;
        
        switch (choice) {
            case 1:
                System.out.print("Nhập tên hãng: ");
                String brand = scanner.nextLine();
                results = manager.searchByBrand(brand);
                break;
            case 2:
                System.out.println("1. Xe máy");
                System.out.println("2. Ô tô");
                System.out.println("3. Xe tải");
                System.out.print("Chọn loại phương tiện: ");
                int type = scanner.nextInt();
                
                String vehicleType;
                switch (type) {
                    case 1: vehicleType = "Xe máy"; break;
                    case 2: vehicleType = "Ô tô"; break;
                    case 3: vehicleType = "Xe tải"; break;
                    default: vehicleType = "Xe máy";
                }
                results = manager.searchByType(vehicleType);
                break;
            case 3:
                System.out.print("Nhập năm sản xuất: ");
                int year = scanner.nextInt();
                results = manager.searchByYear(year);
                break;
            case 4:
                System.out.print("Nhập năm bắt đầu: ");
                int startYear = scanner.nextInt();
                System.out.print("Nhập năm kết thúc: ");
                int endYear = scanner.nextInt();
                results = manager.searchByYearRange(startYear, endYear);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
                return;
        }
        
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy kết quả nào!");
        } else {
            System.out.println("\n=== KẾT QUẢ TÌM KIẾM ===");
            for (int i = 0; i < results.size(); i++) {
                System.out.println((i + 1) + ". " + results.get(i).displayInfo());
            }
            System.out.println("Tìm thấy " + results.size() + " kết quả");
        }
    }
    
    private static void sortMenu(VehicleManager manager, Scanner scanner) {
        System.out.println("\n=== SẮP XẾP ===");
        System.out.println("1. Sắp xếp theo năm sản xuất (tăng dần)");
        System.out.println("2. Sắp xếp theo năm sản xuất (giảm dần)");
        System.out.println("3. Sắp xếp theo hãng");
        System.out.println("4. Sắp xếp theo ID");
        System.out.print("Chọn cách sắp xếp: ");
        
        int choice = scanner.nextInt();
        
        switch (choice) {
            case 1:
                manager.sortByYear();
                break;
            case 2:
                manager.sortByYearDescending();
                break;
            case 3:
                manager.sortByBrand();
                break;
            case 4:
                manager.sortById();
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
    
    private static void statisticsMenu(VehicleManager manager) {
        System.out.println("\n=== THỐNG KÊ ===");
        VehicleStatistics.displayStatistics(manager.getAllVehicles());
        
        double averageAge = VehicleStatistics.getAverageAge(manager.getAllVehicles());
        System.out.printf("\nTuổi trung bình của phương tiện: %.1f năm\n", averageAge);
    }
    
    private static void updateVehicleMenu(VehicleManager manager, Scanner scanner) {
        System.out.print("\nNhập ID phương tiện cần cập nhật: ");
        String id = scanner.nextLine();
        
        Vehicle vehicle = manager.getVehicleById(id);
        if (vehicle == null) {
            System.out.println("Không tìm thấy phương tiện có ID: " + id);
            return;
        }
        
        System.out.println("Thông tin hiện tại: " + vehicle.displayInfo());
        System.out.print("Nhập hãng mới (Enter để giữ nguyên): ");
        String newBrand = scanner.nextLine();
        if (newBrand.trim().isEmpty()) {
            newBrand = vehicle.getBrand();
        }
        
        System.out.print("Nhập năm sản xuất mới (0 để giữ nguyên): ");
        int newYear = scanner.nextInt();
        if (newYear == 0) {
            newYear = vehicle.getYearOfManufacture();
        }
        
        if (manager.updateVehicle(id, newBrand, newYear)) {
            System.out.println("Cập nhật thành công!");
        } else {
            System.out.println("Cập nhật thất bại!");
        }
    }
    
    private static void fileMenu(VehicleManager manager, Scanner scanner) {
        System.out.println("\n=== QUẢN LÝ FILE ===");
        System.out.println("1. Lưu danh sách vào file");
        System.out.println("2. Đọc danh sách từ file");
        System.out.print("Chọn thao tác: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng mới
        
        switch (choice) {
            case 1:
                System.out.print("Nhập tên file: ");
                String filename = scanner.nextLine();
                manager.saveToFile(filename);
                break;
            case 2:
                System.out.print("Nhập tên file: ");
                String readFilename = scanner.nextLine();
                manager.loadFromFile(readFilename);
                break;
            default:
                System.out.println("Lựa chọn không hợp lệ!");
        }
    }
}