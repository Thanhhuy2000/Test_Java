public class Truck extends Vehicle {
    private double loadCapacity; // Tải trọng (tấn)
    
    public Truck(String id, String brand, int yearOfManufacture, double loadCapacity) throws VehicleException {
        super(id, brand, yearOfManufacture);
        validateLoadCapacity(loadCapacity);
        this.loadCapacity = loadCapacity;
    }
    
    private void validateLoadCapacity(double loadCapacity) throws VehicleException {
        if (loadCapacity < 0.5 || loadCapacity > 50.0) {
            throw new VehicleException("Tải trọng xe tải phải từ 0.5 đến 50.0 tấn");
        }
    }
    
    public double getLoadCapacity() {
        return loadCapacity;
    }
    
    @Override
    public String displayInfo() {
        return String.format("Xe tải - ID: %s, Hãng: %s, Năm sản xuất: %d, Tải trọng: %.1f tấn", 
                           id, brand, yearOfManufacture, loadCapacity);
    }
    
    @Override
    public String getVehicleType() {
        return "Xe tải";
    }
} 