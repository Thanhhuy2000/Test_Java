public class Car extends Vehicle {
    private int seats; // Số chỗ ngồi
    
    public Car(String id, String brand, int yearOfManufacture, int seats) throws VehicleException {
        super(id, brand, yearOfManufacture);
        validateSeats(seats);
        this.seats = seats;
    }
    
    private void validateSeats(int seats) throws VehicleException {
        if (seats < 2 || seats > 15) {
            throw new VehicleException("Số chỗ ngồi ô tô phải từ 2 đến 15");
        }
    }
    
    public int getSeats() {
        return seats;
    }
    
    @Override
    public String displayInfo() {
        return String.format("Ô tô - ID: %s, Hãng: %s, Năm sản xuất: %d, Số chỗ: %d", 
                           id, brand, yearOfManufacture, seats);
    }
    
    @Override
    public String getVehicleType() {
        return "Ô tô";
    }
} 