import javax.swing.*;
import java.util.ArrayList;

public class MainUI {
    public static void main(String[] args) {
        // Tạo danh sách sản phẩm mẫu
        ArrayList<Product> productList = createSampleProducts();
        
        // Khởi chạy giao diện UI
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductManagementUI ui = new ProductManagementUI(productList);
                ui.setVisible(true);
            }
        });
    }
    
    private static ArrayList<Product> createSampleProducts() {
        ArrayList<Product> products = new ArrayList<>();
        
        // Tạo sản phẩm Electronics với mã 8 ký tự
        Electronics laptop = new Electronics("Laptop Dell", "Dell", "Computer", "EL001234", 2, 1500.0);
        Electronics phone = new Electronics("iPhone 15", "Apple", "Mobile", "EL002345", 1, 1200.0);
        Electronics tablet = new Electronics("iPad Pro", "Apple", "Tablet", "EL003456", 1, 800.0);
        
        // Tạo sản phẩm Clothing với mã 8 ký tự
        Clothing shirt = new Clothing("T-shirt Cotton", "Nike", "Apparel", "CL001234", "Cotton", 25.0);
        Clothing jeans = new Clothing("Blue Jeans", "Levi's", "Apparel", "CL002345", "Denim", 40.0);
        Clothing dress = new Clothing("Summer Dress", "Zara", "Apparel", "CL003456", "Polyester", 60.0);
        
        // Tạo sản phẩm FoodItem với mã 8 ký tự
        FoodItem bread = new FoodItem("Fresh Bread", "Local Bakery", "Bakery", "FD001234", 7);
        FoodItem milk = new FoodItem("Fresh Milk", "Dairy Farm", "Dairy", "FD002345", 14);
        FoodItem apple = new FoodItem("Red Apple", "Fruit Market", "Fruits", "FD003456", 30);
        
        // Thêm vào danh sách
        products.add(laptop);
        products.add(phone);
        products.add(tablet);
        products.add(shirt);
        products.add(jeans);
        products.add(dress);
        products.add(bread);
        products.add(milk);
        products.add(apple);
        
        return products;
    }
}
