public class Electronics extends Product implements ProfitabilityCalculator {
    public int warrantyYears;
    private double basePrice;
    
    public Electronics(String productName, String brand, String category, String productCode, int warrantyYears, double basePrice) {
        super(productName, brand, category, productCode);
        this.warrantyYears = warrantyYears;
        this.basePrice = basePrice;
    }
    
    @Override
    public String getProductType() {
        return "Electronics";
    }
    
    @Override
    public Double calculateProfitMargin() {
        return basePrice - (warrantyYears * 5);
    }
    
    // Phương thức cập nhật giá cơ bản
    public void updateBasePrice(double newPrice) {
        this.basePrice = newPrice;
    }
    
    // Getter cho basePrice
    public double getBasePrice() {
        return basePrice;
    }
}
