import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Product> productList = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    // Hàm nhập danh sách đối tượng Product
    public static void inputProductList() {
        System.out.println("=== NHẬP DANH SÁCH SẢN PHẨM ===");
        System.out.println("Nhập số lượng sản phẩm muốn thêm: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Đọc bỏ dòng trống
        
        for (int i = 0; i < n; i++) {
            System.out.println("\n--- Sản phẩm thứ " + (i + 1) + " ---");
            System.out.println("Nhập loại đối tượng (elec/cloth/food): ");
            String type = scanner.nextLine().toLowerCase();
            
            Product product = null;
            
            switch (type) {
                case "elec":
                    product = inputElectronics();
                    break;
                case "cloth":
                    product = inputClothing();
                    break;
                case "food":
                    product = inputFoodItem();
                    break;
                default:
                    System.out.println("Loại không hợp lệ! Vui lòng nhập lại.");
                    i--; // Giảm i để nhập lại
                    continue;
            }
            
            if (product != null) {
                productList.add(product);
                System.out.println("Đã thêm sản phẩm thành công!");
            }
        }
    }
    
    // Hàm nhập thông tin Electronics
    private static Electronics inputElectronics() {
        try {
            System.out.println("Nhập tên sản phẩm: ");
            String productName = scanner.nextLine();
            
            System.out.println("Nhập thương hiệu: ");
            String brand = scanner.nextLine();
            
            System.out.println("Nhập danh mục: ");
            String category = scanner.nextLine();
            
            System.out.println("Nhập mã sản phẩm: ");
            String productCode = scanner.nextLine();
            
            System.out.println("Nhập số năm bảo hành: ");
            int warrantyYears = Integer.parseInt(scanner.nextLine());
            
            System.out.println("Nhập giá cơ bản: ");
            double basePrice = Double.parseDouble(scanner.nextLine());
            
            return new Electronics(productName, brand, category, productCode, warrantyYears, basePrice);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng số! Vui lòng nhập lại.");
            return null;
        }
    }
    
    // Hàm nhập thông tin Clothing
    private static Clothing inputClothing() {
        try {
            System.out.println("Nhập tên sản phẩm: ");
            String productName = scanner.nextLine();
            
            System.out.println("Nhập thương hiệu: ");
            String brand = scanner.nextLine();
            
            System.out.println("Nhập danh mục: ");
            String category = scanner.nextLine();
            
            System.out.println("Nhập mã sản phẩm: ");
            String productCode = scanner.nextLine();
            
            System.out.println("Nhập chất liệu: ");
            String material = scanner.nextLine();
            
            System.out.println("Nhập chi phí sản xuất: ");
            double manufacturingCost = Double.parseDouble(scanner.nextLine());
            
            return new Clothing(productName, brand, category, productCode, material, manufacturingCost);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng số! Vui lòng nhập lại.");
            return null;
        }
    }
    
    // Hàm nhập thông tin FoodItem
    private static FoodItem inputFoodItem() {
        try {
            System.out.println("Nhập tên sản phẩm: ");
            String productName = scanner.nextLine();
            
            System.out.println("Nhập thương hiệu: ");
            String brand = scanner.nextLine();
            
            System.out.println("Nhập danh mục: ");
            String category = scanner.nextLine();
            
            System.out.println("Nhập mã sản phẩm: ");
            String productCode = scanner.nextLine();
            
            System.out.println("Nhập số ngày hạn sử dụng: ");
            int shelfLifeDays = Integer.parseInt(scanner.nextLine());
            
            return new FoodItem(productName, brand, category, productCode, shelfLifeDays);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi định dạng số! Vui lòng nhập lại.");
            return null;
        }
    }
    
    // Hàm hiển thị danh sách đối tượng theo từng loại
    public static void displayProductList() {
        System.out.println("\n=== HIỂN THỊ DANH SÁCH SẢN PHẨM ===");
        
        if (productList.isEmpty()) {
            System.out.println("Danh sách sản phẩm trống!");
            return;
        }
        
        // Hiển thị Electronics
        System.out.println("\n--- ELECTRONICS ---");
        boolean hasElectronics = false;
        for (Product product : productList) {
            if (product instanceof Electronics) {
                hasElectronics = true;
                Electronics elec = (Electronics) product;
                System.out.println("Tên: " + elec.productName + 
                                 " | Thương hiệu: " + elec.brand + 
                                 " | Danh mục: " + elec.category + 
                                 " | Mã: " + elec.productCode + 
                                 " | Bảo hành: " + elec.warrantyYears + " năm" +
                                 " | Lợi nhuận: $" + elec.calculateProfitMargin());
            }
        }
        if (!hasElectronics) {
            System.out.println("Không có sản phẩm Electronics");
        }
        
        // Hiển thị Clothing
        System.out.println("\n--- CLOTHING ---");
        boolean hasClothing = false;
        for (Product product : productList) {
            if (product instanceof Clothing) {
                hasClothing = true;
                Clothing cloth = (Clothing) product;
                System.out.println("Tên: " + cloth.productName + 
                                 " | Thương hiệu: " + cloth.brand + 
                                 " | Danh mục: " + cloth.category + 
                                 " | Mã: " + cloth.productCode + 
                                 " | Chất liệu: " + cloth.material +
                                 " | Lợi nhuận: $" + cloth.calculateProfitMargin());
            }
        }
        if (!hasClothing) {
            System.out.println("Không có sản phẩm Clothing");
        }
        
        // Hiển thị FoodItem
        System.out.println("\n--- FOOD ITEMS ---");
        boolean hasFood = false;
        for (Product product : productList) {
            if (product instanceof FoodItem) {
                hasFood = true;
                FoodItem food = (FoodItem) product;
                System.out.println("Tên: " + food.productName + 
                                 " | Thương hiệu: " + food.brand + 
                                 " | Danh mục: " + food.category + 
                                 " | Mã: " + food.productCode +
                                 " | Lợi nhuận: $" + food.calculateProfitMargin());
            }
        }
        if (!hasFood) {
            System.out.println("Không có sản phẩm FoodItem");
        }
    }
    
    public static void main(String[] args) {
        System.out.println("CHƯƠNG TRÌNH QUẢN LÝ SẢN PHẨM");
        System.out.println("================================");
        
        // Gọi hàm nhập danh sách sản phẩm
        inputProductList();
        
        // Gọi hàm hiển thị danh sách sản phẩm
        displayProductList();
        
        // Hiển thị tổng số FoodItem đã tạo
        System.out.println("\n=== THỐNG KÊ ===");
        System.out.println("Tổng số FoodItem đã tạo: " + FoodItem.countFoodItems);
        System.out.println("Tổng số sản phẩm trong danh sách: " + productList.size());
        
        scanner.close();
    }
}
