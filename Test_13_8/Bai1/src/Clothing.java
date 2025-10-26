public class Clothing extends Product implements ProfitabilityCalculator{
    public String material;
    private double manufacturingCost;

    public Clothing(String productName, String brand, String category, String productCode, String material, double manufacturingCost) {
        super(productName, brand, category, productCode);
        this.material = material;
        this.manufacturingCost = manufacturingCost;
    }

    @Override
    public String getProductType() {
        return "Clothing";
    }

    @Override
    public Double calculateProfitMargin() {
        return (manufacturingCost * 1.5) - manufacturingCost;
    }
}
