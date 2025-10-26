public class Motorbike extends Vehicle {
    private int power; // Công suất (cc)
    
    public Motorbike(String id, String brand, int yearOfManufacture, int power) throws VehicleException {
        super(id, brand, yearOfManufacture);
        validatePower(power);
        this.power = power;
    }
    
    private void validatePower(int power) throws VehicleException {
        if (power < 50 || power > 2000) {
            throw new VehicleException("Công suất xe máy phải từ 50cc đến 2000cc");
        }
    }
    
    public int getPower() {
        return power;
    }
    
    @Override
    public String displayInfo() {
        return String.format("Xe máy - ID: %s, Hãng: %s, Năm sản xuất: %d, Công suất: %dcc", 
                           id, brand, yearOfManufacture, power);
    }
    
    @Override
    public String getVehicleType() {
        return "Xe máy";
    }
} 