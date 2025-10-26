public final class FoodItem extends Product implements ProfitabilityCalculator{
    public static int countFoodItems = 0;
    private int shelfLifeDays;

    public FoodItem(String productName, String brand, String category, String productCode, int shelfLifeDays) {
        super(productName, brand, category, productCode);
        this.shelfLifeDays = shelfLifeDays;
        countFoodItems++;
    }

    @Override
    public String getProductType() {
        return "Food Item";
    }

    @Override
    public Double calculateProfitMargin() {
        return shelfLifeDays * 0.5;
    }
}
