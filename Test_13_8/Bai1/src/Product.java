public abstract class Product {
    public String productName;
    public String brand;
    public String category;
    protected String productCode;

    public Product(String productName, String brand, String category, String productCode) {
        this.productName = productName;
        this.brand = brand;
        this.category = category;
        this.productCode = productCode;
    }

    public abstract String getProductType();
    public abstract Double calculateProfitMargin();
}
