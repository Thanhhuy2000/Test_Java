public abstract class Vehicle {
    protected String id;
    protected String brand;
    protected int yearOfManufacture;
    
    public Vehicle(String id, String brand, int yearOfManufacture) throws VehicleException {
        validateId(id);
        validateBrand(brand);
        validateYear(yearOfManufacture);
        
        this.id = id;
        this.brand = brand;
        this.yearOfManufacture = yearOfManufacture;
    }
    
    private void validateId(String id) throws VehicleException {
        if (id == null || id.trim().isEmpty()) {
            throw new VehicleException("ID không được để trống");
        }
        if (id.length() < 3) {
            throw new VehicleException("ID phải có ít nhất 3 ký tự");
        }
    }
    
    private void validateBrand(String brand) throws VehicleException {
        if (brand == null || brand.trim().isEmpty()) {
            throw new VehicleException("Hãng không được để trống");
        }
    }
    
    private void validateYear(int year) throws VehicleException {
        int currentYear = java.time.Year.now().getValue();
        if (year < 1900 || year > currentYear + 1) {
            throw new VehicleException("Năm sản xuất phải từ 1900 đến " + (currentYear + 1));
        }
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getBrand() {
        return brand;
    }
    
    public int getYearOfManufacture() {
        return yearOfManufacture;
    }
    
    // Phương thức trừu tượng để hiển thị thông tin
    public abstract String displayInfo();
    
    // Phương thức để lấy loại phương tiện
    public abstract String getVehicleType();
    
    @Override
    public String toString() {
        return displayInfo();
    }
} 